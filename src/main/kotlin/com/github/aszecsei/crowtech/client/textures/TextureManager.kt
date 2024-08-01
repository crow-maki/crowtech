package com.github.aszecsei.crowtech.client.textures

import com.github.aszecsei.crowtech.common.textures.MCMetaInfo
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.mojang.blaze3d.platform.NativeImage
import net.minecraft.Util
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceProvider
import net.minecraftforge.common.data.ExistingFileHelper
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.function.BiConsumer
import java.util.stream.Collectors

class TextureManager(private val rm: ResourceProvider, private val textureWriter: BiConsumer<NativeImage, String>, private val mcMetaWriter: BiConsumer<JsonElement, String>) {
    private val endRunnables = ConcurrentLinkedQueue<IORunnable>()
    private val generatedTextures = ConcurrentHashMap.newKeySet<String>()

    private val GSON = Gson()

    fun hasAsset(asset: String): Boolean {
        return rm.getResource(ResourceLocation(asset)).isPresent
    }

    @Throws(IOException::class)
    fun getAssetAsTexture(textureId: String): NativeImage {
        val resource = rm.getResource(ResourceLocation(textureId))
        if (!resource.isPresent) {
            throw IOException("Texture not found: $textureId")
        }
        resource.get().open().use { stream ->
            return NativeImage.read(stream)
        }
    }

    fun addTexture(textureId: String, image: NativeImage) {
        addTexture(textureId, image, false)
    }

    fun addTexture(textureId: String, image: NativeImage, closeImage: Boolean) {
        if (!textureId.contains(":textures/")) {
            throw IllegalArgumentException("Invalid texture location: $textureId")
        }

        val overrideId = textureId.replace(":textures/", ":datagen_texture_overrides/")
        val overrideResource = rm.getResource(ResourceLocation.tryParse(overrideId)!!)

        if (overrideResource.isPresent) {
            overrideResource.get().open().use { stream ->
                val overrideImage = NativeImage.read(stream)
                textureWriter.accept(overrideImage, textureId)
            }
        } else {
            // Write generated texture
            textureWriter.accept(image, textureId)
        }

        generatedTextures.add(textureId.replace(":textures/", ":"))

        if (closeImage) {
            image.close()
        }
    }

    fun addMcMeta(path: String, info: MCMetaInfo) {
        mcMetaWriter.accept(GSON.toJsonTree(info), path)
    }

    fun runAtEnd(runnable: IORunnable) {
        endRunnables.add(runnable)
    }

    fun doEndWork(): CompletableFuture<*> {
        val ret = CompletableFuture.allOf(
            *endRunnables.stream().map { r -> CompletableFuture.runAsync(r::safeRun, Util.backgroundExecutor()) }.toArray() { x -> arrayOfNulls<CompletableFuture<*>>(x) }
        )
        endRunnables.clear()
        return ret
    }

    fun markTexturesAsGenerated(helper: ExistingFileHelper) {
        for (texture in generatedTextures) {
            helper.trackGenerated(ResourceLocation.tryParse(texture)!!, PackType.CLIENT_RESOURCES, "", "textures")
        }
    }
}