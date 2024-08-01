package com.github.aszecsei.crowtech.common.config

import ca.weblite.objc.Client
import com.github.aszecsei.crowtech.CrowTech
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.config.ModConfig

object CTConfig {
    private val CLIENT_BUILDER = ForgeConfigSpec.Builder()
    private val COMMON_BUILDER = ForgeConfigSpec.Builder()
    private val SERVER_BUILDER = ForgeConfigSpec.Builder()

    val CLIENT_CONFIG = ClientConfig()
    val SERVER_CONFIG = ServerConfig()
    val COMMON_CONFIG = CommonConfig()

    fun register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build(), "${CrowTech.ID}-client.toml")
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build(), "${CrowTech.ID}-common.toml")
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build(), "${CrowTech.ID}-server.toml")
    }

    class ClientConfig {
        init {
            CLIENT_BUILDER.comment("CrowTech client-side settings").push("client")

            CLIENT_BUILDER.pop()
        }
    }

    class ServerConfig {
        init {
            SERVER_BUILDER.comment("CrowTech server-side settings").push("server")

            SERVER_BUILDER.pop()
        }
    }

    class CommonConfig {
        init {
            COMMON_BUILDER.comment("CrowTech common settings").push("common")

            COMMON_BUILDER.pop()
        }
    }
}