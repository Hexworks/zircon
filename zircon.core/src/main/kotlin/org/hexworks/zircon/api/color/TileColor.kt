package org.hexworks.zircon.api.color

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.internal.color.DefaultTextColor

interface TileColor : Cacheable {

    fun isOpaque() = getAlpha() == 255

    fun getAlpha(): Int

    fun getRed(): Int

    fun getGreen(): Int

    fun getBlue(): Int

    fun tint(): TileColor

    fun shade(): TileColor

    fun invert(): TileColor

    fun darkenByPercent(percentage: Double): TileColor

    companion object {

        /**
         * The default foreground color is `WHITE`.
         */
        fun defaultForegroundColor() = ANSITileColor.WHITE

        /**
         * The default background color is `BLACK`.
         */
        fun defaultBackgroundColor() = ANSITileColor.BLACK

        /**
         * Shorthand for a [TileColor] which is fully transparent.
         */
        fun transparent() = TRANSPARENT

        fun defaultAlpha() = DEFAULT_ALPHA

        /**
         * Parses a string into a color. Formats:
         *  * *blue* - Constant value from the [ANSITileColor] enum
         *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultTextColor] color entry by
         *  parsing the tuples as Red, Green and Blue.
         */
        fun fromString(value: String): TileColor {
            value.trim { it <= ' ' }.let { cleanValue ->
                try {
                    return if (ANSITileColor.values().map { it.name }.contains(cleanValue.toUpperCase())) {
                        ANSITileColor.valueOf(cleanValue.toUpperCase())
                    } else {
                        val r = cleanValue.substring(1, 3).toInt(16)
                        val g = cleanValue.substring(3, 5).toInt(16)
                        val b = cleanValue.substring(5, 7).toInt(16)
                        create(r, g, b)
                    }
                } catch (e: Exception) {
                    throw IllegalArgumentException("Unknown color definition '$cleanValue'", e)
                }
            }
        }

        /**
         * Creates a new [TileColor].
         */
        fun create(red: Int, green: Int, blue: Int, alpha: Int = 255): TileColor {
            return DefaultTextColor(red, green, blue, alpha)
        }

        private val TRANSPARENT = TileColor.create(0, 0, 0, 0)
        private const val DEFAULT_ALPHA = 255
    }
}
