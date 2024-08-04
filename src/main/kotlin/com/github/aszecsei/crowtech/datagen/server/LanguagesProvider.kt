package com.github.aszecsei.crowtech.datagen.server

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.common.materials.properties.MaterialProperty
import com.github.aszecsei.crowtech.common.registries.ItemRegistry
import com.github.aszecsei.crowtech.common.registries.MaterialRegistry
import net.minecraft.data.PackOutput
import net.minecraft.network.chat.contents.TranslatableContents
import net.minecraftforge.common.data.LanguageProvider

class LanguagesProvider(packOutput: PackOutput): LanguageProvider(packOutput, CrowTech.ID, "en_us") {

    private fun addItemMessages() {

    }

    private fun addItemTooltips() {

    }

    private fun addItems() {
        this.add("itemGroup." + CrowTech.ID, "CrowTech")

        ItemRegistry.INGOTS.forEach { (id, item) ->
            val mat = MaterialRegistry.MATERIALS[id]!!
            this.addItem(item, "${mat.name} Ingot")
        }
        ItemRegistry.NUGGETS.forEach { (id, item) ->
            val mat = MaterialRegistry.MATERIALS[id]!!
            this.addItem(item, "${mat.name} Nugget")
        }
        ItemRegistry.DUSTS.forEach { (id, item) ->
            val mat = MaterialRegistry.MATERIALS[id]!!
            this.addItem(item, "${mat.name} Dust")
        }

        ItemRegistry.PLATES.forEach { (id, item) ->
            val mat = MaterialRegistry.MATERIALS[id]!!
            this.addItem(item, "${mat.name} Plate")
        }
        ItemRegistry.RODS.forEach { (id, item) ->
            val mat = MaterialRegistry.MATERIALS[id]!!
            this.addItem(item, "${mat.name} Rod")
        }
        ItemRegistry.GEARS.forEach { (id, item) ->
            val mat = MaterialRegistry.MATERIALS[id]!!
            this.addItem(item, "${mat.name} Gear")
        }
        ItemRegistry.WIRES.forEach { (id, item) ->
            val mat = MaterialRegistry.MATERIALS[id]!!
            this.addItem(item, "${mat.name} Wire")
        }
    }

    private fun addBlocks() {

    }

    private fun addEntities() {

    }

    private fun addEffects() {

    }

    private fun addMiscTranslations() {

    }

    private fun addGuiTranslations() {

    }

    private fun addMessages() {

    }

    private fun addAdvancements() {
        this.advancementTitle("root", "CrowTech")
        this.advancementDescription("root", "Begin your journey with CrowTech.")
    }

    private fun addKeybinds() {

    }

    private fun addJeiTranslations() {

    }

    private fun advancementTitle(name: String, s: String) {
        this.add((AdvancementsProvider.title(name).contents as TranslatableContents).key, s)
    }

    private fun advancementDescription(name: String, s: String) {
        this.add((AdvancementsProvider.description(name).contents as TranslatableContents).key, s)
    }

    override fun addTranslations() {
        addAdvancements()
        addItems()
        addItemMessages()
        addItemTooltips()
        addBlocks()
        addEntities()
        addMiscTranslations()
        addGuiTranslations()
        addKeybinds()
        addJeiTranslations()
        addMessages()
    }
}