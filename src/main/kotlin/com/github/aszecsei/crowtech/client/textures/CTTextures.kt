package com.github.aszecsei.crowtech.client.textures

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.client.textures.colorramp.ColorRamp
import com.github.aszecsei.crowtech.common.materials.Strata
import com.github.aszecsei.crowtech.common.materials.properties.MaterialProperty
import com.github.aszecsei.crowtech.common.registries.MaterialRegistry
import com.google.gson.JsonElement
import com.mojang.blaze3d.platform.NativeImage
import net.minecraft.Util
import net.minecraft.server.packs.resources.ResourceProvider
import net.minecraftforge.common.data.ExistingFileHelper
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer
import java.util.function.Consumer

object CTTextures {
    fun offerTextures(textureWriter: BiConsumer<NativeImage, String>, mcMetaWriter: BiConsumer<JsonElement, String>, manager: ResourceProvider, fileHelper: ExistingFileHelper): CompletableFuture<*> {
        val mtm = TextureManager(manager, textureWriter, mcMetaWriter)

        val futures = ArrayList<CompletableFuture<*>>()
        val defer = Consumer<IORunnable> { r -> futures.add(CompletableFuture.runAsync(r::safeRun, Util.backgroundExecutor())) }

        for (material in MaterialRegistry.MATERIALS.values) {
            val meanRgb = material.get(MaterialProperty.MEAN_RGB)
            if (meanRgb == 0) {
                CrowTech.LOGGER.error("Missing mean RGB for material $material.id")
                continue
            }

            val colorRamp = ColorRamp(mtm, meanRgb, material.id)

            if (material.properties.containsKey(MaterialProperty.ORE_PROPERTY)) {
                val oreProp = material.get(MaterialProperty.ORE_PROPERTY)
                defer.accept { ->
                    val template = "crowtech:textures/materialsets/ores/${material.id}.png"
                    for (stratum in oreProp.strata) {
                        val from = stratum.baseTexture
                        mtm.getAssetAsTexture(stratum.baseTexture.toString()).use { image ->
                            mtm.getAssetAsTexture(template).use { top ->
                                TextureHelper.colorize(top, colorRamp)
                                val oreId = if (stratum == Strata.STONE) "${material.id}_ore" else "${stratum.id}_${material.id}_ore"
                                val texturePath = "crowtech:textures/block/${oreId}.png"
                                mtm.addTexture(texturePath, TextureHelper.blend(image, top), true)
                            }
                        }
                    }
                }
            }
        }

        return CompletableFuture.allOf(*futures.toTypedArray())
            .thenComposeAsync({ _ -> mtm.doEndWork() }, Util.backgroundExecutor())
            .thenRun { mtm.markTexturesAsGenerated(fileHelper) }
            .thenRun { CrowTech.LOGGER.info("Generated textures successfully!") }
    }

//    private fun registerFluidTextures(tm: TextureManager, fluid: FluidDefinition) {
//        val path = "crowtech:textures/fluid/"
//        val bucket = path + "bucket.png"
//        val bucket_content = path + "bucket_content.png"
//
//        val fluidColorRamp = ColorRamp(fluid.color)
//
//        // Bucket
//        try {
//            val old_bucket_image = tm.getAssetAsTexture(bucket)
//            val bucket_content_image = tm.getAssetAsTexture(bucket_content)
//            TextureHelper.colorize(bucket_content_image, fluidColorRamp)
//            val bucket_image = TextureHelper.blend(old_bucket_image, bucket_content_image)
//            old_bucket_image.close()
//
//            if (fluid.isGas) {
//                TextureHelper.flip(bucket_image)
//            }
//            tm.addTexture("crowtech:textures/item/${fluid.id.path}_bucket.png", bucket_image)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        val pathFluid = path + "template/${fluid.fluidTexture.path}"
//
//        try {
//            val fluidAnim = tm.getAssetAsTexture(pathFluid)
//            TextureHelper.colorize(fluidAnim, fluidColorRamp)
//            TextureHelper.setAlpha(fluidAnim, fluid.opacity)
//            tm.addTexture("crowtech:textures/fluid/${fluid.id.path}_still.png", fluidAnim, true)
//            tm.addMcMeta("crowtech:textures/fluid/${fluid.id.path}_still.png.mcmeta", fluid.fluidTexture.mcMetaInfo)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
}