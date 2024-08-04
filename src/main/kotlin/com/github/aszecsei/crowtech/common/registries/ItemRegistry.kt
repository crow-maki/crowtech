package com.github.aszecsei.crowtech.common.registries

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.common.materials.properties.MaterialProperty
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import thedarkcolour.kotlinforforge.forge.registerObject
import java.util.*

object ItemRegistry {
    val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrowTech.ID)

    val ADVANCEMENT_ICON by ITEMS.registerObject("advancement_icon") { Item(Item.Properties()) }

    val INGOTS = TreeMap<String, RegistryObject<Item>>()
    val NUGGETS = TreeMap<String, RegistryObject<Item>>()
    val DUSTS = TreeMap<String, RegistryObject<Item>>()

    val PLATES = TreeMap<String, RegistryObject<Item>>()
    val RODS = TreeMap<String, RegistryObject<Item>>()
    val GEARS = TreeMap<String, RegistryObject<Item>>()
    val WIRES = TreeMap<String, RegistryObject<Item>>()

    init {
        MaterialRegistry.MATERIALS.forEach { (id, material) ->
            if (material.properties.containsKey(MaterialProperty.INGOT_PROPERTY)) {
                val ingotProp = material.get(MaterialProperty.INGOT_PROPERTY)
                INGOTS[id] = ITEMS.register("${id}_ingot") { Item(Item.Properties()) }
            }
            if (material.properties.containsKey(MaterialProperty.NUGGET_PROPERTY)) {
                val nuggetProp = material.get(MaterialProperty.NUGGET_PROPERTY)
                NUGGETS[id] = ITEMS.register("${id}_nugget") { Item(Item.Properties()) }
            }
            if (material.properties.containsKey(MaterialProperty.DUST_PROPERTY)) {
                val dustProp = material.get(MaterialProperty.DUST_PROPERTY)
                DUSTS[id] = ITEMS.register("${id}_dust") { Item(Item.Properties()) }
            }

            if (material.properties.containsKey(MaterialProperty.PLATE_PROPERTY)) {
                val plateProp = material.get(MaterialProperty.PLATE_PROPERTY)
                PLATES[id] = ITEMS.register("${id}_plate") { Item(Item.Properties()) }
            }
            if (material.properties.containsKey(MaterialProperty.ROD_PROPERTY)) {
                val rodProp = material.get(MaterialProperty.ROD_PROPERTY)
                RODS[id] = ITEMS.register("${id}_rod") { Item(Item.Properties()) }
            }
            if (material.properties.containsKey(MaterialProperty.GEAR_PROPERTY)) {
                val gearProp = material.get(MaterialProperty.GEAR_PROPERTY)
                GEARS[id] = ITEMS.register("${id}_gear") { Item(Item.Properties()) }
            }
            if (material.properties.containsKey(MaterialProperty.WIRE_PROPERTY)) {
                val wireProp = material.get(MaterialProperty.WIRE_PROPERTY)
                WIRES[id] = ITEMS.register("${id}_wire") { Item(Item.Properties()) }
            }
        }
    }

    fun shouldSkipCreativeModeTab(item: ItemLike): Boolean {
        if (item == ADVANCEMENT_ICON) return true

        return false
    }
}