package com.github.aszecsei.crowtech.datagen

import com.github.aszecsei.crowtech.CrowTech
import com.github.aszecsei.crowtech.datagen.client.BlockStatesProvider
import com.github.aszecsei.crowtech.datagen.client.ItemModelsProvider
import com.github.aszecsei.crowtech.datagen.client.TexturesProvider
import com.github.aszecsei.crowtech.datagen.server.AdvancementsProvider
import com.github.aszecsei.crowtech.datagen.server.LanguagesProvider
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.core.Core

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CrowTech.ID)
object DataGenerators {
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val packOutput = generator.packOutput
        val fileHelper = event.existingFileHelper

        generator.addProvider(event.includeServer(), AdvancementsProvider(generator))
        generator.addProvider(event.includeServer(), LanguagesProvider(packOutput))

        generator.addProvider(event.includeClient(), TexturesProvider(packOutput, fileHelper))
        generator.addProvider(event.includeClient(), BlockStatesProvider(generator, fileHelper))
        generator.addProvider(event.includeClient(), ItemModelsProvider(generator, fileHelper))
    }
}