package org.hexworks.zircon.api.color

import org.hexworks.zircon.internal.color.DefaultColorInterpolator
import org.hexworks.zircon.internal.color.DefaultTileColor

/**
 * Default ANSI colors.
 */
enum class ANSITileColor(
    override val red: Int,
    override val green: Int,
    override val blue: Int,
    override val alpha: Int
) : TileColor {

    RED(128, 0, 0, TileColor.defaultAlpha()),
    GREEN(0, 128, 0, TileColor.defaultAlpha()),
    YELLOW(128, 128, 0, TileColor.defaultAlpha()),
    BLUE(0, 0, 128, TileColor.defaultAlpha()),
    MAGENTA(128, 0, 128, TileColor.defaultAlpha()),
    CYAN(0, 128, 128, TileColor.defaultAlpha()),
    WHITE(192, 192, 192, TileColor.defaultAlpha()),
    BLACK(0, 0, 0, TileColor.defaultAlpha()),
    GRAY(128, 128, 128, TileColor.defaultAlpha()),
    BRIGHT_RED(255, 0, 0, TileColor.defaultAlpha()),
    BRIGHT_GREEN(0, 255, 0, TileColor.defaultAlpha()),
    BRIGHT_YELLOW(255, 255, 0, TileColor.defaultAlpha()),
    BRIGHT_BLUE(0, 0, 255, TileColor.defaultAlpha()),
    BRIGHT_MAGENTA(255, 0, 255, TileColor.defaultAlpha()),
    BRIGHT_CYAN(0, 255, 255, TileColor.defaultAlpha()),
    BRIGHT_WHITE(255, 255, 255, TileColor.defaultAlpha());

    override val cacheKey = "TextColor(r=$red,g=$green,b=$blue,a=$alpha)"

    override fun desaturate(factor: Double): TileColor {
        return TileColor.create(red, green, blue, alpha).desaturate(factor)
    }

    override fun tint(factor: Double): TileColor {
        return TileColor.create(red, green, blue, alpha).tint(factor)
    }

    override fun shade(factor: Double): TileColor {
        return TileColor.create(red, green, blue, alpha).shade(factor)
    }

    override fun tone(factor: Double): TileColor {
        return TileColor.create(red, green, blue, alpha).tone(factor)
    }

    override fun invert(): TileColor {
        return TileColor.create(red, green, blue, alpha).invert()
    }

    override fun darkenByPercent(percentage: Double): TileColor {
        return TileColor.create(red, green, blue, alpha).darkenByPercent(percentage)
    }

    override fun lightenByPercent(percentage: Double): TileColor {
        return TileColor.create(red, green, blue, alpha).lightenByPercent(percentage)
    }

    override fun withAlpha(alpha: Int): TileColor = DefaultTileColor(red, green, blue, alpha)

    override fun withRed(red: Int): TileColor = DefaultTileColor(red, green, blue, alpha)

    override fun withGreen(green: Int): TileColor = DefaultTileColor(red, green, blue, alpha)

    override fun withBlue(blue: Int): TileColor = DefaultTileColor(red, green, blue, alpha)

    override fun interpolateTo(other: TileColor): ColorInterpolator = DefaultColorInterpolator(this, other)

    companion object
}
