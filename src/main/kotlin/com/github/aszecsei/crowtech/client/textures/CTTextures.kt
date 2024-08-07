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
                CrowTech.LOGGER.info("Missing mean RGB for material $material.id")
                continue
            }

            val colorRamp = ColorRamp(mtm, meanRgb, material.id)

            if (material.properties.containsKey(MaterialProperty.ORE_PROPERTY)) {
                val oreProp = material.get(MaterialProperty.ORE_PROPERTY)
                if (oreProp.generate) {
                    defer.accept { ->
                        val template = "crowtech:textures/materialsets/ores/${material.id}.png"
                        for (stratum in oreProp.strata) {
                            val from = stratum.baseTexture
                            mtm.getAssetAsTexture(stratum.baseTexture.toString()).use { image ->
                                mtm.getAssetAsTexture(template).use { top ->
                                    TextureHelper.colorize(top, colorRamp)
                                    val oreId =
                                        if (stratum == Strata.STONE) "${material.id}_ore" else "${stratum.id}_${material.id}_ore"
                                    val texturePath = "crowtech:textures/block/${oreId}.png"
                                    mtm.addTexture(texturePath, TextureHelper.blend(image, top), true)
                                }
                            }
                        }
                    }
                }
            }

            if (material.properties.containsKey(MaterialProperty.RAW_PROPERTY)) {
                val rawProp = material.get(MaterialProperty.RAW_PROPERTY)
                if (rawProp.generate) {
                    defer.accept { ->
                        val template = "crowtech:textures/materialsets/raw_ore.png"
                        mtm.getAssetAsTexture(template).use { top ->
                            TextureHelper.colorize(top, colorRamp)
                            val texturePath = "crowtech:textures/item/raw_${material.id}.png"
                            mtm.addTexture(texturePath, top, true)
                        }
                    }
                }
            }

            if (material.properties.containsKey(MaterialProperty.INGOT_PROPERTY)) {
                val ingotProp = material.get(MaterialProperty.INGOT_PROPERTY)
                if (ingotProp.generate) {
                    defer.accept { ->
                        val template = "crowtech:textures/materialsets/ingots/${ingotProp.texture.path}.png"
                        mtm.getAssetAsTexture(template).use { top ->
                            TextureHelper.colorize(top, colorRamp)
                            val texturePath = "crowtech:textures/item/${material.id}_ingot.png"
                            mtm.addTexture(texturePath, top, true)
                        }
                    }
                }
            }

            if (material.properties.containsKey(MaterialProperty.GEM_PROPERTY)) {
                val gemProp = material.get(MaterialProperty.GEM_PROPERTY)
                if (gemProp.generate) {
                    defer.accept { ->
                        val template = "crowtech:textures/materialsets/gems/${gemProp.texture.path}.png"
                        mtm.getAssetAsTexture(template).use { top ->
                            TextureHelper.colorize(top, colorRamp)
                            val texturePath = "crowtech:textures/item/${material.id}.png"
                            mtm.addTexture(texturePath, top, true)
                        }
                    }
                }
            }

            if (material.properties.containsKey(MaterialProperty.NUGGET_PROPERTY)) {
                val nuggetProp = material.get(MaterialProperty.NUGGET_PROPERTY)
                if (nuggetProp.generate) {
                    defer.accept { ->
                        val template = "crowtech:textures/materialsets/nugget.png"
                        mtm.getAssetAsTexture(template).use { top ->
                            TextureHelper.colorize(top, colorRamp)
                            val texturePath = "crowtech:textures/item/${material.id}_nugget.png"
                            mtm.addTexture(texturePath, top, true)
                        }
                    }
                }
            }

            if (material.properties.containsKey(MaterialProperty.DUST_PROPERTY)) {
                val dustProp = material.get(MaterialProperty.DUST_PROPERTY)
                if (dustProp.generate) {
                    defer.accept { ->
                        val template = "crowtech:textures/materialsets/dust.png"
                        mtm.getAssetAsTexture(template).use { top ->
                            TextureHelper.colorize(top, colorRamp)
                            val texturePath = "crowtech:textures/item/${material.id}_dust.png"
                            mtm.addTexture(texturePath, top, true)
                        }
                    }
                }
            }

            if (material.properties.containsKey(MaterialProperty.PLATE_PROPERTY)) {
                val plateProp = material.get(MaterialProperty.PLATE_PROPERTY)
                if (plateProp.generate) {
                    defer.accept { ->
                        val template = "crowtech:textures/materialsets/plate.png"
                        mtm.getAssetAsTexture(template).use { top ->
                            TextureHelper.colorize(top, colorRamp)
                            val texturePath = "crowtech:textures/item/${material.id}_plate.png"
                            mtm.addTexture(texturePath, top, true)
                        }
                    }
                }
            }

            if (material.properties.containsKey(MaterialProperty.ROD_PROPERTY)) {
                val rodProp = material.get(MaterialProperty.ROD_PROPERTY)
                if (rodProp.generate) {
                    defer.accept { ->
                        val template = "crowtech:textures/materialsets/rod.png"
                        mtm.getAssetAsTexture(template).use { top ->
                            TextureHelper.colorize(top, colorRamp)
                            val texturePath = "crowtech:textures/item/${material.id}_rod.png"
                            mtm.addTexture(texturePath, top, true)
                        }
                    }
                }
            }

            if (material.properties.containsKey(MaterialProperty.GEAR_PROPERTY)) {
                val gearProp = material.get(MaterialProperty.GEAR_PROPERTY)
                if (gearProp.generate) {
                    defer.accept { ->
                        val template = "crowtech:textures/materialsets/gear.png"
                        mtm.getAssetAsTexture(template).use { top ->
                            TextureHelper.colorize(top, colorRamp)
                            val texturePath = "crowtech:textures/item/${material.id}_gear.png"
                            mtm.addTexture(texturePath, top, true)
                        }
                    }
                }
            }

            if (material.properties.containsKey(MaterialProperty.WIRE_PROPERTY)) {
                val wireProp = material.get(MaterialProperty.WIRE_PROPERTY)
                if (wireProp.generate) {
                    defer.accept { ->
                        val template = "crowtech:textures/materialsets/wire.png"
                        mtm.getAssetAsTexture(template).use { top ->
                            TextureHelper.colorize(top, colorRamp)
                            val texturePath = "crowtech:textures/item/${material.id}_wire.png"
                            mtm.addTexture(texturePath, top, true)
                        }
                    }
                }
            }

            if (material.properties.containsKey(MaterialProperty.FLUID_PROPERTY)) {
                val path = "crowtech:textures/fluid/"
                val bucket = path + "bucket.png"
                val bucket_content = path + "bucket_content.png"
                val fluidProp = material.get(MaterialProperty.FLUID_PROPERTY)

                // Bucket
                try {
                    val old_bucket_image = mtm.getAssetAsTexture(bucket)
                    val bucket_content_image = mtm.getAssetAsTexture(bucket_content)
                    TextureHelper.colorize(bucket_content_image, colorRamp)
                    val bucket_image = TextureHelper.blend(old_bucket_image, bucket_content_image)
                    old_bucket_image.close()

                    if (fluidProp.isGas) {
                        TextureHelper.flip(bucket_image)
                    }
                    mtm.addTexture("crowtech:textures/item/molten_${material.id}_bucket.png", bucket_image)
                } catch (e: IOException) {
                    e.printStackTrace()
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