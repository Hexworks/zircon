package org.codetome.zircon.api.color

import org.codetome.zircon.internal.multiplatform.factory.TextColorFactory
import org.codetome.zircon.internal.multiplatform.factory.TextColorFactory.defaultAlpha

/**
 * Default ANSI colors.
 */
enum class ANSITextColor(private val red: Int,
                         private val green: Int,
                         private val blue: Int,
                         private val alpha: Int) : TextColor {

    BLACK(0, 0, 0, defaultAlpha()),
    RED(170, 0, 0, defaultAlpha()),
    GREEN(0, 170, 0, defaultAlpha()),
    YELLOW(170, 85, 0, defaultAlpha()),
    BLUE(0, 0, 170, defaultAlpha()),
    MAGENTA(170, 0, 170, defaultAlpha()),
    CYAN(0, 170, 170, defaultAlpha()),
    WHITE(170, 170, 170, defaultAlpha()),
    DEFAULT(0, 0, 0, defaultAlpha());

    override fun generateCacheKey() = name

    override fun getRed() = red

    override fun getGreen() = green

    override fun getBlue() = blue

    override fun getAlpha() = alpha

    override fun darkenByPercent(percentage: Double): TextColor {
        return TextColorFactory.fromRGB(red, green, blue, alpha).darkenByPercent(percentage)
    }

    override fun tint(): TextColor {
        return TextColorFactory.fromRGB(red, green, blue, alpha).tint()
    }

    override fun shade(): TextColor {
        return TextColorFactory.fromRGB(red, green, blue, alpha).shade()
    }

    override fun invert(): TextColor {
        return TextColorFactory.fromRGB(red, green, blue, alpha).invert()
    }
}
