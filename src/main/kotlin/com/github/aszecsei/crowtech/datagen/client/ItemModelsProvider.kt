package com.github.aszecsei.crowtech.datagen.client

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.common.registries.BlockRegistry
import com.github.aszecsei.crowtech.common.registries.FluidRegistry
import com.github.aszecsei.crowtech.common.registries.ItemRegistry
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.world.item.*
import net.minecraftforge.client.model.generators.ItemModelBuilder
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class ItemModelsProvider(generator: DataGenerator, fileHelper: ExistingFileHelper) :
    ItemModelProvider(generator.packOutput, CrowTech.ID, fileHelper) {
    private fun res(name: String): ResourceLocation {
        return ResourceLocation(CrowTech.ID, "$ITEM_FOLDER/$name")
    }

    private fun textureExists(texture: ResourceLocation): Boolean {
        return existingFileHelper.exists(texture, PackType.CLIENT_RESOURCES, ".png", "textures")
    }

    override fun registerModels() {
        val generated = ResourceLocation("item/generated")

        BlockRegistry.BLOCK_ITEMS.entries.stream().filter { item -> item.get() !is BlockItem }
            .forEach { item ->
                val name = item.id.path
                if (!name.contains("bucket")) {
                    withExistingParent(name, generated).texture("layer0", res(name))
                }
            }

        ItemRegistry.ITEMS.entries.stream().filter { item -> item.get() != ItemRegistry.ADVANCEMENT_ICON }
            .forEach { item ->
                val name = item.id.path
                withExistingParent(name, generated).texture("layer0", res(name))
            }

        FluidRegistry.FLUID_BUCKETS.entries.stream().filter { item -> item.get() != ItemRegistry.ADVANCEMENT_ICON }
            .forEach { item ->
                val name = item.id.path
                withExistingParent(name, generated).texture("layer0", res(name))
            }

        registerAdvancementItem()
    }

    private fun registerAdvancementItem() {
        val textures: Array<String> = arrayOf(
            "crow_icon"
        )
        val icons: MutableList<ItemModelBuilder> = mutableListOf()

        for (texture in textures) {
            icons.add(
                withExistingParent("item/advancement/$texture", mcLoc("item/generated")).texture(
                    "layer0",
                    modLoc("item/advancement/$texture")
                )
            )
        }
        val builder = withExistingParent("item/advancement_icon", mcLoc("item/generated"))
        for (i in 0..<icons.size) {
            builder.override().predicate(mcLoc("custom_model_data"), i.toFloat()).model(icons[i]).end()
        }
    }
}