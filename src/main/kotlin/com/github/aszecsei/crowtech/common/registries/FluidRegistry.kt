package com.github.aszecsei.crowtech.common.registries

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.client.textures.TextureHelper
import com.github.aszecsei.crowtech.common.fluids.CTFluidType
import com.github.aszecsei.crowtech.common.materials.properties.MaterialProperty
import com.github.aszecsei.crowtech.utils.Reference
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import org.joml.Vector3f
import java.util.*

object FluidRegistry {
    val FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CrowTech.ID)
    val FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, CrowTech.ID)
    val FLUID_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrowTech.ID)
    val FLUID_BUCKETS = DeferredRegister.create(ForgeRegistries.ITEMS, CrowTech.ID)

    val FLOWING_FLUIDS = TreeMap<String, RegistryObject<ForgeFlowingFluid.Flowing>>()
    val SOURCE_FLUIDS = TreeMap<String, RegistryObject<ForgeFlowingFluid.Source>>()
    val LIQUID_BLOCKS = TreeMap<String, RegistryObject<LiquidBlock>>()
    val BUCKETS = TreeMap<String, RegistryObject<BucketItem>>()

    val FLOWING = Reference.ct("block/flowing")
    val STILL = Reference.ct("block/still")

    init {
        MaterialRegistry.MATERIALS.forEach { (id, material) ->
            if (material.properties.containsKey(MaterialProperty.FLUID_PROPERTY)) {
                val fluidType = FLUID_TYPES.register("molten_$id") {
                    val materialColor = material.get(MaterialProperty.MEAN_RGB)
                    val r = TextureHelper.getR(materialColor)
                    val g = TextureHelper.getG(materialColor)
                    val b = TextureHelper.getB(materialColor)
                    val fog = Vector3f(b / 255f, g / 255f, r / 255f)
                    CTFluidType(
                        FLOWING,
                        STILL,
                        TextureHelper.fromArgb(255, r, g, b),
                        fog,
                        material.get(MaterialProperty.FLUID_PROPERTY)
                    )
                }

                lateinit var properties: ForgeFlowingFluid.Properties
                val flowing = lazy {
                    FLUIDS.register("molten_${id}") { ForgeFlowingFluid.Flowing(properties) }
                }
                val source = lazy {
                    FLUIDS.register("flowing_molten_${id}") { ForgeFlowingFluid.Source(properties) }
                }
                val block = lazy {
                    FLUID_BLOCKS.register("molten_${id}_block") {
                        LiquidBlock(source.value, BlockBehaviour.Properties.copy(Blocks.LAVA))
                    }
                }
                val bucket = lazy {
                    FLUID_BUCKETS.register("molten_${id}_bucket") {
                        BucketItem(source.value, Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
                    }
                }
                properties = ForgeFlowingFluid.Properties(fluidType, source.value, flowing.value)
                    .bucket(bucket.value)
                    .block(block.value)

                FLOWING_FLUIDS[material.id] = flowing.value
                SOURCE_FLUIDS[material.id] = source.value
                LIQUID_BLOCKS[material.id] = block.value
                BUCKETS[material.id] = bucket.value
            }
        }
    }
}