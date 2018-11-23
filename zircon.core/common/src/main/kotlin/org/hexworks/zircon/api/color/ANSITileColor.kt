package org.hexworks.zircon.api.color

/**
 * Default ANSI colors.
 */
@Suppress("RedundantModalityModifier")
enum class ANSITileColor(final override val red: Int,
                         final override val green: Int,
                         final override val blue: Int,
                         final override val alpha: Int) : TileColor {

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

    private val cacheKey = "TextColor(r=$red,g=$green,b=$blue,a=$alpha)"

    override fun generateCacheKey() = cacheKey

    override fun tint(factor: Double): TileColor {
        return TileColor.create(red, green, blue, alpha).tint(factor)
    }

    override fun shade(factor: Double): TileColor {
        return TileColor.create(red, green, blue, alpha).shade(factor)
    }

    override fun invert(): TileColor {
        return TileColor.create(red, green, blue, alpha).invert()
    }

    override fun darkenByPercent(percentage: Double): TileColor {
        return TileColor.create(red, green, blue, alpha).darkenByPercent(percentage)
    }
}
