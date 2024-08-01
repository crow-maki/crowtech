package com.github.aszecsei.crowtech.common.blocks

import com.github.aszecsei.crowtech.common.materials.Strata
import com.github.aszecsei.crowtech.common.materials.properties.OreProperty
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType

class OreBlock(val properties: OreProperty, strata: Strata): Block(
    Properties.of().sound(if (strata == Strata.DEEPSLATE) SoundType.DEEPSLATE else if (strata == Strata.NETHERRACK) SoundType.NETHER_ORE else SoundType.STONE)
        .strength(strata.hardness, strata.resistance).requiresCorrectToolForDrops()
) {
}