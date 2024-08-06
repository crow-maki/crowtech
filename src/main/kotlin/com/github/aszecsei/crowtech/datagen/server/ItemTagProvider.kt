package com.github.aszecsei.crowtech.datagen.server

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.common.registries.ItemRegistry
import com.github.aszecsei.crowtech.common.registries.ItemTagRegistry
import com.github.aszecsei.crowtech.utils.Reference
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.data.tags.TagsProvider
import net.minecraft.tags.ItemTags
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ItemTagProvider(output: PackOutput,
                      lookupProvider: CompletableFuture<HolderLookup.Provider>,
                      blockTags: CompletableFuture<TagsProvider.TagLookup<Block>>,
                      existingFileHelper: ExistingFileHelper?): ItemTagsProvider(output, lookupProvider, blockTags, CrowTech.ID, existingFileHelper) {
    override fun addTags(provider: HolderLookup.Provider) {
        ItemRegistry.RAW_ORES.forEach { (id, item) ->
            this.tag(ItemTagRegistry.RAW_ORES).add(item.get())
            this.tag(ItemTags.create(Reference.raw(id))).add(item.get())
        }
        ItemRegistry.INGOTS.forEach { (id, item) ->
            this.tag(ItemTagRegistry.INGOTS).add(item.get())
            this.tag(ItemTags.create(Reference.ingot(id))).add(item.get())
        }
        ItemRegistry.GEMS.forEach { (id, item) ->
            this.tag(ItemTagRegistry.GEMS).add(item.get())
            this.tag(ItemTags.create(Reference.gem(id))).add(item.get())
        }
        ItemRegistry.NUGGETS.forEach { (id, item) ->
            this.tag(ItemTagRegistry.NUGGETS).add(item.get())
            this.tag(ItemTags.create(Reference.nugget(id))).add(item.get())
        }
        ItemRegistry.DUSTS.forEach { (id, item) ->
            this.tag(ItemTagRegistry.DUSTS).add(item.get())
            this.tag(ItemTags.create(Reference.dust(id))).add(item.get())
        }

        ItemRegistry.GEARS.forEach { (id, item) ->
            this.tag(ItemTagRegistry.GEARS).add(item.get())
            this.tag(ItemTags.create(Reference.gear(id))).add(item.get())
        }
        ItemRegistry.PLATES.forEach { (id, item) ->
            this.tag(ItemTagRegistry.PLATES).add(item.get())
            this.tag(ItemTags.create(Reference.plate(id))).add(item.get())
        }
        ItemRegistry.RODS.forEach { (id, item) ->
            this.tag(ItemTagRegistry.RODS).add(item.get())
            this.tag(ItemTags.create(Reference.rod(id))).add(item.get())
        }
        ItemRegistry.WIRES.forEach { (id, item) ->
            this.tag(ItemTagRegistry.WIRES).add(item.get())
            this.tag(ItemTags.create(Reference.wire(id))).add(item.get())
        }
    }
}