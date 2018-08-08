package org.codetome.zircon.internal.color

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.util.Math

data class DefaultTextColor(private val red: Int,
                            private val green: Int,
                            private val blue: Int,
                            private val alpha: Int = TextColor.defaultAlpha()) : TextColor {

    private val cacheKey = "TextColor(r=$red,g=$green,b=$blue,a=$alpha)"

    override fun generateCacheKey() = cacheKey

    override fun getRed() = red

    override fun getGreen() = green

    override fun getBlue() = blue

    override fun getAlpha() = alpha

    override fun tint(): TextColor {
        var r = getRed()
        var g = getGreen()
        var b = getBlue()
        val alpha = getAlpha()

        val i = (1.0 / (1.0 - FACTOR)).toInt()
        if (r == 0 && g == 0 && b == 0) {
            return DefaultTextColor(i, i, i, alpha)
        }
        if (r in 1..(i - 1)) r = i
        if (g in 1..(i - 1)) g = i
        if (b in 1..(i - 1)) b = i

        return DefaultTextColor(Math.min((r / FACTOR).toInt(), 255),
                Math.min((g / FACTOR).toInt(), 255),
                Math.min((b / FACTOR).toInt(), 255),
                alpha)
    }

    override fun shade(): TextColor {
        return DefaultTextColor(Math.max((getRed() * FACTOR).toInt(), 0),
                Math.max((getGreen() * FACTOR).toInt(), 0),
                Math.max((getBlue() * FACTOR).toInt(), 0),
                getAlpha())
    }

    override fun invert(): TextColor {
        return DefaultTextColor(255 - red, 255 - green, 255 - blue, alpha)
    }

    override fun darkenByPercent(percentage: Double): TextColor {
        return TextColor.create(
                red = (red * (1f - percentage)).toInt(),
                green = (green * (1f - percentage)).toInt(),
                blue = (blue * (1f - percentage)).toInt(),
                alpha = alpha)
    }

    companion object {
        private const val FACTOR = 0.7
    }
}
