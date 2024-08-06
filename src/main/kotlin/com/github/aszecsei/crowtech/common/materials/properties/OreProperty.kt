package com.github.aszecsei.crowtech.common.materials.properties

import com.github.aszecsei.crowtech.common.materials.Strata
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.Tags

class OreProperty() {
    var min = 1
    var max = 1
    var uniformCount = false
    var strata = mutableSetOf(Strata.STONE, Strata.DEEPSLATE)
    var generate = true
    var toolTierRequirement: ToolTierRequirement = ToolTierRequirement.STONE

    enum class ToolTierRequirement(val tag: TagKey<Block>?) {
        NONE(null),
        STONE(BlockTags.NEEDS_STONE_TOOL),
        IRON(BlockTags.NEEDS_IRON_TOOL),
        DIAMOND(BlockTags.NEEDS_DIAMOND_TOOL),
        NETHERITE(Tags.Blocks.NEEDS_NETHERITE_TOOL)
    }

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
    fun needs(requirement: ToolTierRequirement): OreProperty {
        toolTierRequirement = requirement
        return this
    }
}