package com.github.aszecsei.crowtech.common.materials.properties

class RawProperty {
    var generate = true

    fun doNotGenerate(): RawProperty {
        this.generate = false
        return this
    }
}