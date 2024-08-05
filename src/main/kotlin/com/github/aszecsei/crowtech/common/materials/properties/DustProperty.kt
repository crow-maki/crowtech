package com.github.aszecsei.crowtech.common.materials.properties

class DustProperty {
    var generate = true

    fun doNotGenerate(): DustProperty {
        this.generate = false
        return this
    }
}