package com.github.aszecsei.crowtech.datagen.server

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.common.registries.ItemRegistry
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.FrameType
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.advancements.critereon.PlayerTrigger
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.nbt.IntTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

class AdvancementsProvider(val generator: DataGenerator) : DataProvider {
    val advancements: MutableMap<ResourceLocation, Advancement> = HashMap()

    companion object {
        private fun text(name: String, type: String): MutableComponent {
            return Component.translatable("advancements." + CrowTech.ID + "." + name + "." + type)
        }

        fun title(name: String): MutableComponent {
            return text(name, "title")
        }

        fun description(name: String): MutableComponent {
            return text(name, "description")
        }

        private fun getPath(path: Path, advancement: Advancement): Path {
            val id = advancement.id
            return path.resolve("data/" + id.namespace + "/advancements/" + id.path + ".json")
        }
    }

    override fun run(p0: CachedOutput): CompletableFuture<*> {
        // val futures = ArrayList<CompletableFuture<*>>()
        val folder = generator.packOutput.outputFolder
        this.start()
        val futures: List<CompletableFuture<*>> = advancements.values.map {
            val path = getPath(folder, it)
            return DataProvider.saveStable(p0, it.deconstruct().serializeToJson(), path)
        }
        return CompletableFuture.allOf(*futures.toTypedArray())
    }

    private fun start() {
        val root = this.add(
            Advancement.Builder.advancement()
                .addCriterion(
                    "crowmod_present",
                    PlayerTrigger.TriggerInstance(CriteriaTriggers.TICK.id, ContextAwarePredicate.ANY)
                )
                .display(
                    this.icon(0),
                    title("root"),
                    description("root"),
                    ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.TASK,
                    false,
                    false,
                    false
                )
                .build(ResourceLocation(CrowTech.ID, "crowmod/root"))
        )
    }

    private fun icon(data: Int): ItemStack {
        val icon = ItemRegistry.ADVANCEMENT_ICON.defaultInstance
        icon.addTagElement("CustomModelData", IntTag.valueOf(data))
        return icon
    }

    private fun add(advancement: Advancement): Advancement {
        if (this.advancements.containsKey(advancement.id)) {
            throw IllegalStateException("Duplicate advancement " + advancement.id)
        }
        advancements[advancement.id] = advancement
        return advancement
    }

    override fun getName(): String {
        return "Advancements: " + CrowTech.ID
    }
}