package com.github.aszecsei.crowtech.common.materials.properties

class PlateProperty {
    var generate = true

    fun doNotGenerate(): PlateProperty {
        this.generate = false
        return this
    }
}