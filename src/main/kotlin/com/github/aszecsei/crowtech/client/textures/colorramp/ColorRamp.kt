package com.github.aszecsei.crowtech.client.textures.colorramp

import com.github.aszecsei.crowtech.client.textures.TextureHelper
import com.github.aszecsei.crowtech.client.textures.TextureHelper.getB
import com.github.aszecsei.crowtech.client.textures.TextureHelper.getG
import com.github.aszecsei.crowtech.client.textures.TextureManager
import com.github.aszecsei.crowtech.client.textures.TextureHelper.getR

class ColorRamp(private val meanRGB: Int): IColorRamp {
    private val colors = Array<Int>(256) { 0 }

    init {
        fillUniform()
    }

    constructor(mtm: TextureManager, meanRGB: Int, name: String) : this(meanRGB) {
        val gradientMapPath = "crowtech:textures/gradient_maps/$name.png"

        if (mtm.hasAsset(gradientMapPath)) {
            mtm.getAssetAsTexture(gradientMapPath).use { gradientMap ->
                for (i in 0 until 256) {
                    val color = gradientMap.getPixelRGBA(i, 0)
                    val r = getR(color)
                    val g = getG(color)
                    val b = getB(color)
                    colors[i] = (r shl 16) or (g shl 8) or b
                }
            }
        } else {
            fillUniform()
        }
    }

    private fun fillUniform() {
        val meanR = TextureHelper.getRrgb(meanRGB)
        val meanG = TextureHelper.getGrgb(meanRGB)
        val meanB = TextureHelper.getBrgb(meanRGB)

        for (i in 0 until 256) {
            colors[i] = TextureHelper.toRGB(meanR * i / 255, meanG * i / 255, meanB * i / 255)
        }
    }

    override fun getRGB(luminance: Double): Int {
        val i = (luminance * 255).toInt()
        return colors[i]
    }

    override fun getMeanRGB(): Int {
        return meanRGB
    }
}