package org.hexworks.zircon.api.color

/**
 * Default ANSI colors.
 */
enum class ANSITileColor(private val red: Int,
                         private val green: Int,
                         private val blue: Int,
                         private val alpha: Int) : TileColor {

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

    override fun getRed() = red

    override fun getGreen() = green

    override fun getBlue() = blue

    override fun getAlpha() = alpha

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
