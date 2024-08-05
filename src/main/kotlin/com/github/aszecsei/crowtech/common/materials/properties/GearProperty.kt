package com.github.aszecsei.crowtech.common.materials.properties

class GearProperty {
    var generate = true

    fun doNotGenerate(): GearProperty {
        this.generate = false
        return this
    }
}