package com.github.aszecsei.crowtech.datagen.client

import com.github.aszecsei.crowtech.client.textures.CTTextures
import com.github.aszecsei.crowtech.common.resources.FastPathPackResources
import com.google.common.hash.Hashing
import com.google.gson.JsonElement
import com.mojang.blaze3d.platform.NativeImage
import net.minecraft.Util
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.PathPackResources
import net.minecraft.server.packs.VanillaPackResourcesBuilder
import net.minecraft.server.packs.resources.MultiPackResourceManager
import net.minecraft.server.packs.resources.ResourceProvider
import net.minecraftforge.common.data.ExistingFileHelper
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class TexturesProvider(val packOutput: PackOutput, val fileHelper: ExistingFileHelper): DataProvider {
    override fun run(cache: CachedOutput): CompletableFuture<*> {
        val packs = ArrayList<PackResources>()
        packs.add(VanillaPackResourcesBuilder().exposeNamespace("minecraft").pushJarResources().build())
        val nonGeneratedResources = packOutput.outputFolder.resolve("../../main/resources")
        packs.add(FastPathPackResources("nonGen", nonGeneratedResources, true))

        val jsonSaveFutures = ArrayList<CompletableFuture<*>>()
        val fallbackProvider = MultiPackResourceManager(PackType.CLIENT_RESOURCES, packs)
        return generateTextures(cache, fallbackProvider, jsonSaveFutures::add)
            .whenComplete { _, _ -> fallbackProvider.close() }
            .thenRunAsync({ CompletableFuture.allOf(*jsonSaveFutures.map { it }.toTypedArray()) }, Util.backgroundExecutor())
    }

    private fun generateTextures(cache: CachedOutput, fallbackResourceProvider: ResourceProvider, futureList: Consumer<CompletableFuture<*>>): CompletableFuture<*> {
        val generatedResources = packOutput.outputFolder
        val generatedPack = listOf(FastPathPackResources("gen", generatedResources, true))

        val outputPack = MultiPackResourceManager(PackType.CLIENT_RESOURCES, generatedPack)

        return CTTextures.offerTextures(
            { image, textureId -> writeTexture(cache, image, textureId) },
            { json, path -> futureList.accept(customJsonSave(cache, json, path)) },
            { resourceLocation ->
                val generated = outputPack.getResource(resourceLocation)
                if (generated.isPresent) generated else fallbackResourceProvider.getResource(resourceLocation)
            },
            fileHelper
        ).whenComplete { _, _ -> outputPack.close() }
    }

    private fun writeTexture(cache: CachedOutput, image: NativeImage, textureId: String) {
        try {
            val path = packOutput.outputFolder.resolve("assets").resolve(textureId.replace(':', '/'))
            cache.writeIfNeeded(path, image.asByteArray(), Hashing.sha1().hashBytes(image.asByteArray()))
        } catch (e: IOException) {
            throw RuntimeException("Failed to write texture $textureId", e)
        }
    }

    private fun customJsonSave(cache: CachedOutput, jsonElement: JsonElement, path: String): CompletableFuture<*> {
        val pathFormatted = packOutput.outputFolder.resolve("assets").resolve(path.replace(':', '/'))
        return DataProvider.saveStable(cache, jsonElement, pathFormatted)
    }

    override fun getName(): String {
        return "Textures"
    }
}