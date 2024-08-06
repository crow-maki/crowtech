package com.github.aszecsei.crowtech.common.materials.properties

import java.util.*
import kotlin.collections.HashMap

class MaterialProperty<T>(val id: String, val defaultValue: T) {
    companion object {
        private val PROPERTY_IDS = HashMap<String, MaterialProperty<*>>()
        val PROPERTIES = Collections.unmodifiableCollection(PROPERTY_IDS.values)

        val ORE_PROPERTY = MaterialProperty<OreProperty>("ore", OreProperty())
        val RAW_PROPERTY = MaterialProperty<RawProperty>("raw_ore", RawProperty())
        val MEAN_RGB = MaterialProperty<Int>("mean_rgb", 0)
        val INGOT_PROPERTY = MaterialProperty<IngotProperty>("ingot", IngotProperty())
        val GEM_PROPERTY = MaterialProperty<GemProperty>("gem", GemProperty())
        val DUST_PROPERTY = MaterialProperty<DustProperty>("dust", DustProperty())
        val NUGGET_PROPERTY = MaterialProperty<NuggetProperty>("nugget", NuggetProperty())

        val PLATE_PROPERTY = MaterialProperty<PlateProperty>("plate", PlateProperty())
        val ROD_PROPERTY = MaterialProperty<RodProperty>("rod", RodProperty())
        val GEAR_PROPERTY = MaterialProperty<GearProperty>("gear", GearProperty())
        val WIRE_PROPERTY = MaterialProperty<WireProperty>("wire", WireProperty())
    }

    init {
        if (PROPERTY_IDS.put(id, this) != null) {
            throw IllegalArgumentException("Duplicate material property id: $id")
        }
    }
}