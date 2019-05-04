package org.hexworks.zircon.internal.color

import org.hexworks.zircon.api.color.TileColor
import kotlin.math.max
import kotlin.math.min

internal data class DefaultTileColor(override val red: Int,
                            override val green: Int,
                            override val blue: Int,
                            override val alpha: Int = TileColor.defaultAlpha()) : TileColor {

    private val cacheKey = "TextColor(r=$red,g=$green,b=$blue,a=$alpha)"

    override fun generateCacheKey() = cacheKey

    override fun tint(factor: Double): TileColor {
        require(factor in 0.0..1.0) {
            "The given percentage ($factor) is not between the required range (0 - 1)."
        }
        var r = red
        var g = green
        var b = blue
        val alpha = alpha

        val i = (1.0 / (1.0 - factor)).toInt()
        if (r == 0 && g == 0 && b == 0) {
            return DefaultTileColor(i, i, i, alpha)
        }
        if (r in 1..(i - 1)) r = i
        if (g in 1..(i - 1)) g = i
        if (b in 1..(i - 1)) b = i

        return DefaultTileColor(min((r / factor).toInt(), 255),
                min((g / factor).toInt(), 255),
                min((b / factor).toInt(), 255),
                alpha)
    }

    override fun shade(factor: Double): TileColor {
        require(factor in 0.0..1.0) {
            "The given percentage ($factor) is not between the required range (0 - 1)."
        }
        return TileColor.create(max((red * factor).toInt(), 0),
                max((green * factor).toInt(), 0),
                max((blue * factor).toInt(), 0),
                alpha)
    }

    override fun invert(): TileColor {
        return DefaultTileColor(255 - red, 255 - green, 255 - blue, alpha)
    }

    override fun darkenByPercent(percentage: Double): TileColor {
        require(percentage in 0.0..1.0) {
            "The given percentage ($percentage) is not between the required range (0 - 1)."
        }
        return TileColor.create(
                red = (red * (1f - percentage)).toInt(),
                green = (green * (1f - percentage)).toInt(),
                blue = (blue * (1f - percentage)).toInt(),
                alpha = alpha)
    }

    override fun lightenByPercent(percentage: Double): TileColor {
        require(percentage in 0.0..1.0) {
            "The given percentage ($percentage) is not between the required range (0 - 1)."
        }
        return TileColor.create(
                red = (red * (1f + percentage)).toInt(),
                green = (green * (1f + percentage)).toInt(),
                blue = (blue * (1f + percentage)).toInt(),
                alpha = alpha)
    }
}
