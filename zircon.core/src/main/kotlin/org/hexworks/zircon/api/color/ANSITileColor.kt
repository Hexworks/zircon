package org.hexworks.zircon.api.color

/**
 * Default ANSI colors.
 */
enum class ANSITileColor(final override val red: Int,
                         final override val green: Int,
                         final override val blue: Int,
                         final override val alpha: Int) : TileColor {

    BLACK(0, 0, 0, TileColor.defaultAlpha()),
    RED(170, 0, 0, TileColor.defaultAlpha()),
    GREEN(0, 170, 0, TileColor.defaultAlpha()),
    YELLOW(170, 85, 0, TileColor.defaultAlpha()),
    BLUE(0, 0, 170, TileColor.defaultAlpha()),
    MAGENTA(170, 0, 170, TileColor.defaultAlpha()),
    CYAN(0, 170, 170, TileColor.defaultAlpha()),
    WHITE(170, 170, 170, TileColor.defaultAlpha()),
    DEFAULT(0, 0, 0, TileColor.defaultAlpha());

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
