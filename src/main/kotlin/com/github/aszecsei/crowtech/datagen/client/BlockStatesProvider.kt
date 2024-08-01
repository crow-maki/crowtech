package com.github.aszecsei.crowtech.datagen.client

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.common.registries.BlockRegistry
import net.minecraft.data.DataGenerator
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.registries.RegistryObject

class BlockStatesProvider(generator: DataGenerator, fileHelper: ExistingFileHelper): BlockStateProvider(generator.packOutput, CrowTech.ID, fileHelper) {
    override fun registerStatesAndModels() {
        val entries = BlockRegistry.BLOCKS.entries.stream().map { entry -> entry.get() }.toList()
        entries.forEach { block -> simpleBlockWithItem(block, cubeAll(block)) }
    }
}