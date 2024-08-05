package com.github.aszecsei.crowtech.common.materials.properties

class IngotProperty {
    var generate = true
    var texture = IngotTexture.METALLIC

    data class IngotTexture(val path: String) {
        companion object {
            val METALLIC = IngotTexture("metallic")
            val SHINY = IngotTexture("shiny")
        }
    }

    fun doNotGenerate(): IngotProperty {
        this.generate = false
        return this
    }

    fun withTexture(texture: IngotTexture): IngotProperty {
        this.texture = texture
        return this
    }
}