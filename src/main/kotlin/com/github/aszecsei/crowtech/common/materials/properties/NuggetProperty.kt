package com.github.aszecsei.crowtech.common.materials.properties

class NuggetProperty {
    var generate = true

    fun doNotGenerate(): NuggetProperty {
        this.generate = false
        return this
    }
}