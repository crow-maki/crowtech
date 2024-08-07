package com.github.aszecsei.crowtech.common.materials.properties

class FluidProperty {
    var temp = 300
    var isGas = false

    fun temperature(temp: Int): FluidProperty {
        this.temp = temp
        return this
    }
    fun gas(): FluidProperty {
        this.isGas = true
        return this
    }
}