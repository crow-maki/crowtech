package com.github.aszecsei.crowtech.common.materials.properties

class RodProperty {
    var generate = true

    fun doNotGenerate(): RodProperty {
        this.generate = false
        return this
    }
}