package com.github.aszecsei.crowtech.common.materials.properties

import com.github.aszecsei.crowtech.common.materials.properties.IngotProperty.IngotTexture

class GemProperty {
    var generate = true
    var texture = GemTexture.RUBY

    data class GemTexture(val path: String) {
        companion object {
            val DIAMOND = GemTexture("diamond")
            val EMERALD = GemTexture("emerald")
            val LAPIS = GemTexture("lapis")
            val QUARTZ = GemTexture("quartz")
            val RUBY = GemTexture("ruby")
        }
    }

    fun doNotGenerate(): GemProperty {
        this.generate = false
        return this
    }
    fun withTexture(texture: GemTexture): GemProperty {
        this.texture = texture
        return this
    }
}