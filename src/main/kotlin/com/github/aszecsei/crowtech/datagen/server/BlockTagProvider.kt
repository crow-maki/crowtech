package com.github.aszecsei.crowtech.datagen.server

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.common.registries.BlockRegistry
import com.github.aszecsei.crowtech.common.registries.BlockTagRegistry
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.tags.BlockTags
import net.minecraftforge.common.data.BlockTagsProvider
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class BlockTagProvider(output: PackOutput,
                       lookupProvider: CompletableFuture<HolderLookup.Provider>,
                       existingFileHelper: ExistingFileHelper?
) : BlockTagsProvider(output, lookupProvider, CrowTech.ID, existingFileHelper) {
    fun addOres(provider: HolderLookup.Provider) {
        BlockRegistry.ORES.forEach { (_, block) ->
            val oreBlock = block.get()
            this.tag(BlockTagRegistry.ORES)
                .add(oreBlock)
            if (oreBlock.properties.toolTierRequirement.tag != null) {
                this.tag(oreBlock.properties.toolTierRequirement.tag!!).add(oreBlock)
            }
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(oreBlock)
            if (BlockTagRegistry.STRATA_TAGS.containsKey(oreBlock.strata.id)) {
                this.tag(BlockTagRegistry.STRATA_TAGS[oreBlock.strata.id]!!).add(oreBlock)
            }
        }
    }

    override fun addTags(provider: HolderLookup.Provider) {
        addOres(provider)
    }
}