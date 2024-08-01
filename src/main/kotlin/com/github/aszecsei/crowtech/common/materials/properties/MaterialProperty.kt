package com.github.aszecsei.crowtech.common.materials.properties

import java.util.*
import kotlin.collections.HashMap

class MaterialProperty<T>(val id: String, val defaultValue: T) {
    companion object {
        private val PROPERTY_IDS = HashMap<String, MaterialProperty<*>>()
        val PROPERTIES = Collections.unmodifiableCollection(PROPERTY_IDS.values)

        val ORE_PROPERTY = MaterialProperty<OreProperty>("ore", OreProperty())
        val MEAN_RGB = MaterialProperty<Int>("mean_rgb", 0)
    }

    init {
        if (PROPERTY_IDS.put(id, this) != null) {
            throw IllegalArgumentException("Duplicate material property id: $id")
        }
    }
}