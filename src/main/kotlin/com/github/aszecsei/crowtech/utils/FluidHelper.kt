package com.github.aszecsei.crowtech.utils

import kotlin.math.min

object FluidHelper {
    fun getColorMinLuminance(color: Int): Int {
        var r = (color and 0xFF)
        var g = (color and 0xFF00) shr 8
        var b = (color and 0xFF0000) shr 16
        val lum = (0.2126 * r + 0.7152 * g + 0.0722 * b) / 255.0
        if (lum < 0.3) {
            if (lum == 0.0) {
                return 0x4C4C4C
            } else {
                r = min((r * 0.3 / lum).toInt().toDouble(), 255.0).toInt()
                g = min((g * 0.3 / lum).toInt().toDouble(), 255.0).toInt()
                b = min((b * 0.3 / lum).toInt().toDouble(), 255.0).toInt()
                return r + (g shl 8) + (b shl 16)
            }
        } else {
            return color
        }
    }
}