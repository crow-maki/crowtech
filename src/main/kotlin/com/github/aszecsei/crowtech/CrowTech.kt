package com.github.aszecsei.crowtech

import com.github.aszecsei.crowtech.common.config.CTConfig
import com.github.aszecsei.crowtech.common.registries.*
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist

/**
 * Main mod class. Should be an `object` declaration annotated with `@Mod`.
 * The modid should be declared in this object and should match the modId entry
 * in mods.toml.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(CrowTech.ID)
object CrowTech {
    const val ID = "crowtech"

    fun rl(path: String): ResourceLocation {
        return ResourceLocation(ID, path)
    }

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(ID)

    init {
        LOGGER.log(Level.INFO, "Hello world!")

        // Load config values
        CTConfig.register()

        // Registration
        CreativeModeTabRegistry.CREATIVE_MODE_TABS.register(MOD_BUS)
        ItemRegistry.ITEMS.register(MOD_BUS)
        BlockRegistry.BLOCKS.register(MOD_BUS)
        BlockRegistry.BLOCK_ITEMS.register(MOD_BUS)
        FluidRegistry.FLUIDS.register(MOD_BUS)
        FluidRegistry.FLUID_TYPES.register(MOD_BUS)
        FluidRegistry.FLUID_BLOCKS.register(MOD_BUS)
        FluidRegistry.FLUID_BUCKETS.register(MOD_BUS)

        val obj = runForDist(
            clientTarget = {
                MOD_BUS.addListener(CrowTech::onClientSetup)
                Minecraft.getInstance()
            },
            serverTarget = {
                MOD_BUS.addListener(CrowTech::onServerSetup)
                "test"
            })

        println(obj)
    }

    /**
     * This is used for initializing client specific
     * things such as renderers and keymaps
     * Fired on the mod specific event bus.
     */
    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...")
    }

    /**
     * Fired on the global Forge bus.
     */
    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
    }
}