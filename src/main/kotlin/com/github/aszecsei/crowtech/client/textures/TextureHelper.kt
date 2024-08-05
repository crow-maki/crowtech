package com.github.aszecsei.crowtech.client.textures

import com.github.aszecsei.crowtech.client.textures.colorramp.IColorRamp
import com.mojang.blaze3d.platform.NativeImage
import java.awt.Color
import kotlin.math.max

object TextureHelper {
    fun colorize(image: NativeImage, colorRamp: IColorRamp) {
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val color = image.getPixelRGBA(i, j)
                val l = getLuminance(color)
                val rgb = colorRamp.getRGB(l)
                val r = getRrgb(rgb)
                val g = getGrgb(rgb)
                val b = getBrgb(rgb)
                image.setPixelRGBA(i, j, fromArgb(getA(color), r, g, b))
            }
        }
    }
    fun setAlpha(image: NativeImage, alpha: Int) {
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val color = image.getPixelRGBA(i, j)
                val r = getR(color)
                val g = getG(color)
                val b = getB(color)
                image.setPixelRGBA(i, j, fromArgb(alpha, r, g, b))
            }
        }
    }
    fun increaseBrightness(image: NativeImage, minBrightness: Float) {
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val color = image.getPixelRGBA(i, j)
                val l = getLuminance(color)
                var r = getR(color)
                var g = getG(color)
                var b = getB(color)

                val rgb = increaseBrightness(toRGB(r, g, b), minBrightness)
                r = getRrgb(rgb)
                g = getGrgb(rgb)
                b = getBrgb(rgb)

                image.setPixelRGBA(i, j, fromArgb(getA(color), r, g, b))
            }
        }
    }

    fun getLuminance(color: Int): Double {
        return (0.2126 * getR(color) + 0.7152 * getG(color) + 0.0722 * getB(color)) / 255;
    }

    fun lerp(a: Int, b: Int, fact: Double): Int {
        return (fact * b + (1 - fact) * a).toInt()
    }

    fun mixRGB(rgb1: Int, rgb2: Int, fact: Double): Int {
        val r1 = getRrgb(rgb1)
        val r2 = getRrgb(rgb2)
        val g1 = getGrgb(rgb1)
        val g2 = getGrgb(rgb2)
        val b1 = getBrgb(rgb1)
        val b2 = getBrgb(rgb2)
        return toRGB(lerp(r1, r2, fact), lerp(g1, g2, fact), lerp(b1, b2, fact))
    }

    fun setHue(rgb: Int, hue: Float): Int {
        val hsbval = FloatArray(3)
        Color.RGBtoHSB(getRrgb(rgb), getGrgb(rgb), getBrgb(rgb), hsbval)
        return 0xFFFFFF and Color.HSBtoRGB(hue, hsbval[1], hsbval[2])
    }
    fun setSaturation(rgb: Int, saturation: Float): Int {
        val hsbval = FloatArray(3)
        Color.RGBtoHSB(getRrgb(rgb), getGrgb(rgb), getBrgb(rgb), hsbval)
        return 0xFFFFFF and Color.HSBtoRGB(hsbval[0], saturation, hsbval[2])
    }
    fun increaseBrightness(rgb: Int, minBrightness: Float): Int {
        val hsbval = FloatArray(3)
        Color.RGBtoHSB(getRrgb(rgb), getGrgb(rgb), getBrgb(rgb), hsbval)
        return 0xFFFFFF and Color.HSBtoRGB(hsbval[0], hsbval[1], minBrightness + (1 - minBrightness) * hsbval[2])
    }

    fun toRGB(r: Int, g: Int, b: Int): Int {
        return (r shl 16) + (g shl 8) + b
    }

    fun getRrgb(rgb: Int): Int {
        return (rgb shr 16) and 0xFF
    }

    fun getGrgb(rgb: Int): Int {
        return (rgb shr 8) and 0xFF
    }

    fun getBrgb(rgb: Int): Int {
        return rgb and 0xFF
    }

    fun getA(color: Int): Int {
        return (color shr 24) and 0xFF
    }
    fun getR(color: Int): Int {
        return color and 0xFF
    }
    fun getG(color: Int): Int {
        return (color shr 8) and 0xFF
    }
    fun getB(color: Int): Int {
        return (color shr 16) and 0xFF
    }

    // Double values from 0 to 255
    fun fromArgb(a: Int, r: Double, G: Double, B: Double): Int {
        return fromArgb(a, r.toInt(), G.toInt(), B.toInt())
    }
    fun fromArgb(a: Int, r: Int, g: Int, b: Int): Int {
        return (a shl 24) or (b shl 16) or (g shl 8) or r
    }

    fun adjustDimensions(images: MutableList<NativeImage>) {
        var maxWidth = 0
        var maxHeight = 0

        for (image in images) {
            maxWidth = max(maxWidth, image.width)
            maxHeight = max(maxHeight, image.height)
        }

        for (i in 0 until images.size) {
            val image = images[i]

            if (maxWidth % image.width != 0 || maxHeight % image.height != 0) {
                throw IllegalArgumentException("Mismatched dimensions, can't adjust. Max: ($maxWidth, $maxHeight). Current image: (${image.width}, ${image.height}).")
            }

            val wFactor = maxWidth / image.width
            val hFactor = maxHeight / image.height

            if (wFactor == 1 && hFactor == 1) {
                continue
            }

            val newImage = NativeImage(maxWidth, maxHeight, false)

            for (x in 0 until maxWidth) {
                for (y in 0 until maxHeight) {
                    newImage.setPixelRGBA(x, y, image.getPixelRGBA(x / wFactor, y / hFactor))
                }
            }

            images[i] = newImage
        }
    }

    fun blend(originalSource: NativeImage, originalTop: NativeImage): NativeImage {
        val images = mutableListOf(originalSource, originalTop)
        adjustDimensions(images)
        val source = images[0]
        val top = images[1]

        val output = NativeImage(source.width, source.height, false)

        for (x in 0 until source.width) {
            for (y in 0 until source.height) {
                val sourceColor = source.getPixelRGBA(x, y)
                val topColor = top.getPixelRGBA(x, y)
                val alphaSource = getA(sourceColor) / 255.0
                val alphaTop = getA(topColor) / 255.0
                val alphaOut = alphaTop + (1 - alphaTop) * alphaSource
                val mergeAlpha = { sourceValue: Int, topValue: Int ->
                    ((topValue * alphaTop + sourceValue * alphaSource * (1 - alphaTop)) / alphaOut).toInt()
                }
                output.setPixelRGBA(x, y, fromArgb((alphaOut * 255).toInt(),
                    mergeAlpha(getR(sourceColor), getR(topColor)),
                    mergeAlpha(getG(sourceColor), getG(topColor)),
                    mergeAlpha(getB(sourceColor), getB(topColor))
                ))
            }
        }

        return output
    }

    fun flip(image: NativeImage) {
        val width = image.width
        val height = image.height
        val flipped = Array(width) { IntArray(height) }

        for (x in 0 until width) {
            for (y in 0 until height) {
                flipped[x][height - y - 1] = image.getPixelRGBA(x, y)
            }
        }
        for (x in 0 until width) {
            for (y in 0 until height) {
                image.setPixelRGBA(x, y, flipped[x][y])
            }
        }
    }
}