package org.hexworks.zircon.api.color

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.internal.color.DefaultTextColor

interface TileColor : Cacheable {

    fun isOpaque() = alpha == 255

    val alpha: Int

    val red: Int

    val green: Int

    val blue: Int

    /**
     * Returns a new [TileColor] which is tinted by the default factor (.7).
     */
    fun tint(): TileColor = tint(defaultFactor())

    /**
     * Returns a new [TileColor] which is tinted by `factor`.
     */
    fun tint(factor: Double): TileColor

    /**
     * Returns a new [TileColor] which is shaded by the default factor (.7).
     */
    fun shade(): TileColor = shade(defaultFactor())

    /**
     * Returns a new [TileColor] which is shaded by `factor`.
     */
    fun shade(factor: Double): TileColor

    /**
     * Returns a new [TileColor] which is the inversion of this one.
     */
    fun invert(): TileColor

    /**
     * Returns a new [TileColor] which is darkened by the given `percentage`.
     * The number must be between `0` and `1`.
     */
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

        fun defaultFactor() = DEFAULT_FACTOR

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
        private const val DEFAULT_FACTOR = 0.7
    }
}
