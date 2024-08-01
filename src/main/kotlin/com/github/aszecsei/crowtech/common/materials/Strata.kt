package com.github.aszecsei.crowtech.common.materials

import com.github.aszecsei.crowtech.utils.Reference
import net.minecraft.resources.ResourceLocation

class Strata(
    val id: String,
    val baseTexture: ResourceLocation,
    val suffix: String,
    val fillerType: ResourceLocation,
    val englishName: String,
) {
    var harvestTool = HarvestToolType.PICKAXE
    var hardness = 3f
    var resistance = 3f

    fun withHarvestTool(tool: HarvestToolType): Strata {
        harvestTool = tool
        return this
    }
    fun withHardness(hardness: Float): Strata {
        this.hardness = hardness
        return this
    }
    fun withResistance(resistance: Float): Strata {
        this.resistance = resistance
        return this
    }

    companion object {
        val STONE = Strata(
            "stone",
            Reference.vanilla("textures/block/stone.png"),
            "stone",
            Reference.vanilla("stone"),
            "Stone"
        )
        val DEEPSLATE = Strata(
            "deepslate",
            Reference.vanilla("textures/block/deepslate.png"),
            "deepslate",
            Reference.vanilla("deepslate"),
            "Deepslate"
        )
        val NETHERRACK = Strata(
            "netherrack",
            Reference.vanilla("textures/block/netherrack.png"),
            "netherrack",
            Reference.vanilla("netherrack"),
            "Nether"
        )
        val END_STONE = Strata(
            "end_stone",
            Reference.vanilla("textures/block/end_stone.png"),
            "end_stone",
            Reference.vanilla("end_stone"),
            "End"
        )
    }
}