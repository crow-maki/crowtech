package com.github.aszecsei.crowtech.common.registries

import com.github.aszecsei.crowtech.utils.Reference
import net.minecraft.tags.ItemTags

object ItemTagRegistry {
    val INGOTS = ItemTags.create(Reference.forge("ingots"))
    val NUGGETS = ItemTags.create(Reference.forge("nuggets"))
    val STORAGE_BLOCKS = ItemTags.create(Reference.forge("storage_blocks"))
    val DUSTS = ItemTags.create(Reference.forge("dusts"))
    val GEARS = ItemTags.create(Reference.forge("gears"))
    val PLATES = ItemTags.create(Reference.forge("plates"))
    val RODS = ItemTags.create(Reference.forge("rods"))
    val RAW_ORES = ItemTags.create(Reference.forge("raw_materials"))
    val ORES = ItemTags.create(Reference.forge("ores"))
    val GEMS = ItemTags.create(Reference.forge("gems"))
}