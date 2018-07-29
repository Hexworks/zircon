package org.codetome.zircon.api.color

/**
 * Default ANSI colors.
 */
enum class ANSITextColor(private val red: Int,
                         private val green: Int,
                         private val blue: Int,
                         private val alpha: Int) : TextColor {

    BLACK(0, 0, 0, TextColor.defaultAlpha()),
    RED(170, 0, 0, TextColor.defaultAlpha()),
    GREEN(0, 170, 0, TextColor.defaultAlpha()),
    YELLOW(170, 85, 0, TextColor.defaultAlpha()),
    BLUE(0, 0, 170, TextColor.defaultAlpha()),
    MAGENTA(170, 0, 170, TextColor.defaultAlpha()),
    CYAN(0, 170, 170, TextColor.defaultAlpha()),
    WHITE(170, 170, 170, TextColor.defaultAlpha()),
    DEFAULT(0, 0, 0, TextColor.defaultAlpha());

    override fun getRed() = red

    override fun getGreen() = green

    override fun getBlue() = blue

    override fun getAlpha() = alpha

    override fun darkenByPercent(percentage: Double): TextColor {
        return TextColor.create(red, green, blue, alpha).darkenByPercent(percentage)
    }

    override fun tint(): TextColor {
        return TextColor.create(red, green, blue, alpha).tint()
    }

    override fun shade(): TextColor {
        return TextColor.create(red, green, blue, alpha).shade()
    }

    override fun invert(): TextColor {
        return TextColor.create(red, green, blue, alpha).invert()
    }

    companion object {

        internal val COLOR_NAMES = values().map { it.name }
    }
}
