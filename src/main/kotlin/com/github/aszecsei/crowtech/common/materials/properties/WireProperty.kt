package com.github.aszecsei.crowtech.common.materials.properties

class WireProperty {
    var generate = true

    fun doNotGenerate(): WireProperty {
        this.generate = false
        return this
    }
}