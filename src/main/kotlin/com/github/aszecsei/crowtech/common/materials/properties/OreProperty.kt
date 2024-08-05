package com.github.aszecsei.crowtech.common.materials.properties

import com.github.aszecsei.crowtech.common.materials.Strata

class OreProperty() {
    var min = 1
    var max = 1
    var uniformCount = false
    var strata = mutableSetOf(Strata.STONE, Strata.DEEPSLATE)
    var generate = true

    fun withMin(min: Int): OreProperty {
        this.min = min
        return this
    }
    fun withMax(max: Int): OreProperty {
        this.max = max
        return this
    }
    fun withUniformCount(uniformCount: Boolean): OreProperty {
        this.uniformCount = uniformCount
        return this
    }
    fun withStrata(vararg strata: Strata): OreProperty {
        this.strata.clear()
        this.strata.addAll(strata)
        return this
    }
    fun doNotGenerate(): OreProperty {
        this.generate = false
        return this
    }
}