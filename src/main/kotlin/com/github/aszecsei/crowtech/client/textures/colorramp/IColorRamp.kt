package com.github.aszecsei.crowtech.client.textures.colorramp

import com.github.aszecsei.crowtech.client.textures.TextureHelper.fromArgb
import com.github.aszecsei.crowtech.client.textures.TextureHelper.getRrgb
import com.github.aszecsei.crowtech.client.textures.TextureHelper.getGrgb
import com.github.aszecsei.crowtech.client.textures.TextureHelper.getBrgb
import com.mojang.blaze3d.platform.NativeImage

interface IColorRamp {
    fun getRGB(luminance: Double): Int
    fun getMeanRGB(): Int

    fun bakeAsImage(): NativeImage {
        val dimension = 256;
        val image = NativeImage(dimension, dimension, true)
        for (i in 0..<dimension) {
            for (j in 0..<dimension) {
                val luminance = i / 255.0
                val rgb = getRGB(luminance)
                val r = getRrgb(rgb)
                val g = getGrgb(rgb)
                val b = getBrgb(rgb)
                image.setPixelRGBA(i, j, fromArgb(255, r, g, b))
            }
        }
        return image
    }
}