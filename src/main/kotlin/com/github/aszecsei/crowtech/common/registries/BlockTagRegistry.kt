package com.github.aszecsei.crowtech.common.registries

import com.github.aszecsei.crowtech.common.materials.Strata
import com.github.aszecsei.crowtech.utils.Reference
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.Tags

object BlockTagRegistry {
    val ORES = BlockTags.create(Reference.forge("ores"))
    val ORES_IN_GROUND_ENDSTONE = BlockTags.create(Reference.forge("ores_in_ground_endstone"))

    val STRATA_TAGS = mapOf(
        Strata.STONE.id to Tags.Blocks.ORES_IN_GROUND_STONE,
        Strata.DEEPSLATE.id to Tags.Blocks.ORES_IN_GROUND_DEEPSLATE,
        Strata.NETHERRACK.id to Tags.Blocks.ORES_IN_GROUND_NETHERRACK,
        Strata.END_STONE.id to ORES_IN_GROUND_ENDSTONE
    )
}