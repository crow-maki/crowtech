package com.github.aszecsei.crowtech.common.materials

import com.github.aszecsei.crowtech.common.materials.properties.MaterialProperty
import com.github.aszecsei.crowtech.common.registries.MaterialRegistry
import java.util.*

class Material(val id: String, val name: String) {
    val properties = IdentityHashMap<MaterialProperty<*>, Any>()

    fun <T> set(prop: MaterialProperty<T>, value: T): Material {
        properties[prop] = value
        return this
    }
    inline fun <reified T> get(prop: MaterialProperty<T>): T {
        if (!properties.containsKey(prop)) {
            throw IllegalArgumentException("Material $id does not contain prop ${prop.id}")
        }
        return (properties[prop] as T)
    }

    fun register(): Material {
        MaterialRegistry.MATERIALS[id] = this
        return this
    }
}