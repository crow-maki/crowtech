package com.github.aszecsei.crowtech.common.registries

import com.github.aszecsei.crowtech.CrowTech
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ItemRegistry {
    val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrowTech.ID)

    val ADVANCEMENT_ICON by ITEMS.registerObject("advancement_icon") { Item(Item.Properties()) }

    fun shouldSkipCreativeModeTab(item: ItemLike): Boolean {
        if (item == ADVANCEMENT_ICON) return true

        return false
    }
}