package com.github.aszecsei.crowtech.common.registries

import com.github.aszecsei.crowtech.CrowTech
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTabs
import net.minecraftforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.forge.registerObject

object CreativeModeTabRegistry {
    val CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrowTech.ID)

    val CROWTECH = CREATIVE_MODE_TABS.register("crowtech") {
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.${CrowTech.ID}"))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems {
                _, output -> ItemRegistry.ITEMS.entries.forEach { i ->
                if (!ItemRegistry.shouldSkipCreativeModeTab(i.get())) {
                    output.accept(i.get())
                }
            }
            }.build()
    }
}