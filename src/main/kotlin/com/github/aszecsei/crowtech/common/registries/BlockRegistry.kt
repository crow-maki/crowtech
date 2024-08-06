package com.github.aszecsei.crowtech.common.registries

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.common.blocks.OreBlock
import com.github.aszecsei.crowtech.common.materials.Strata
import com.github.aszecsei.crowtech.common.materials.properties.MaterialProperty
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import thedarkcolour.kotlinforforge.forge.registerObject
import java.util.*

object BlockRegistry {
    val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CrowTech.ID)
    val BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrowTech.ID)

    val ORES = TreeMap<String, RegistryObject<OreBlock>>()

    init {
        MaterialRegistry.MATERIALS.forEach { (id, material) ->
            if (material.properties.containsKey(MaterialProperty.ORE_PROPERTY)) {
                val oreProp = material.get(MaterialProperty.ORE_PROPERTY)
                if (oreProp.generate) {
                    for (stratum in oreProp.strata) {
                        val oreId = if (stratum == Strata.STONE) "${id}_ore" else "${stratum.id}_${id}_ore"
                        CrowTech.LOGGER.info("Registering $oreId")
                        val block = BLOCKS.register(oreId) {
                            OreBlock(
                                material.get(MaterialProperty.ORE_PROPERTY),
                                stratum
                            )
                        }
                        BLOCK_ITEMS.register(oreId) { BlockItem(block.get(), Item.Properties()) }
                        ORES["${id}:${stratum.id}"] = block
                    }
                }
            }
        }
    }
}