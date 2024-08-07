package com.github.aszecsei.crowtech.common.fluids

import com.github.aszecsei.crowtech.common.materials.properties.FluidProperty
import com.mojang.blaze3d.shaders.FogShape
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Camera
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.FogRenderer
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.material.FluidState
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
import net.minecraftforge.common.SoundActions
import net.minecraftforge.fluids.FluidType
import org.joml.Vector3f
import java.util.function.Consumer

class CTFluidType(val still: ResourceLocation,
                  val flowing: ResourceLocation,
                  val tint: Int,
                  val fog: Vector3f,
                  fluidProp: FluidProperty
): FluidType(
    Properties.create()
    .density(1000).viscosity(1000).temperature(fluidProp.temp).lightLevel(10)
    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)) {
    override fun canConvertToSource(state: FluidState?, reader: LevelReader?, pos: BlockPos?): Boolean {
        return false
    }

    override fun initializeClient(consumer: Consumer<IClientFluidTypeExtensions>?) {
        consumer?.accept(object : IClientFluidTypeExtensions {
            override fun getStillTexture(): ResourceLocation {
                return still
            }

            override fun getFlowingTexture(): ResourceLocation {
                return flowing
            }

            override fun getTintColor(): Int {
                return tint
            }

            override fun modifyFogColor(
                camera: Camera?,
                partialTick: Float,
                level: ClientLevel?,
                renderDistance: Int,
                darkenWorldAmount: Float,
                fluidFogColor: Vector3f?
            ): Vector3f {
                return fog
            }

            override fun modifyFogRender(
                camera: Camera?,
                mode: FogRenderer.FogMode?,
                renderDistance: Float,
                partialTick: Float,
                nearDistance: Float,
                farDistance: Float,
                shape: FogShape?
            ) {
                RenderSystem.setShaderFogStart(1f)
                RenderSystem.setShaderFogEnd(3f)
            }
        })
    }
}