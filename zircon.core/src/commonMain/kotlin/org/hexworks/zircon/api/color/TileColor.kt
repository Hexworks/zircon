@file:Suppress("MemberVisibilityCanBePrivate")

package org.hexworks.zircon.api.color

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.color.DefaultTileColor

/**
 * A [TileColor] represents the colors of a [Tile]. [TileColor]s support transparency
 * through [alpha]. You can choose to create your own colors using the various factory
 * functions in [TileColor.Companion] or use [ANSITileColor] instead that comes with
 * the default ANSI colors.
 */
interface TileColor : Cacheable {

    val alpha: Int
    val red: Int
    val green: Int
    val blue: Int

    val isOpaque: Boolean
        get() = alpha == 255

    /**
     * Returns a new [TileColor] which is desaturated by the [DEFAULT_FACTOR] (.7).
     */
    fun desaturate(): TileColor = desaturate(defaultFactor())

    /**
     * Returns a new [TileColor] which is desaturated by [factor].
     */
    fun desaturate(factor: Double): TileColor

    /**
     * Returns a new [TileColor] which is tinted by the [DEFAULT_FACTOR] (.7).
     */
    fun tint(): TileColor = tint(defaultFactor())

    /**
     * Returns a new [TileColor] which is tinted by [factor].
     */
    fun tint(factor: Double): TileColor

    /**
     * Returns a new [TileColor] which is shaded by the [DEFAULT_FACTOR] (.7).
     */
    fun shade(): TileColor = shade(defaultFactor())

    /**
     * Returns a new [TileColor] which is shaded by [factor].
     */
    fun shade(factor: Double): TileColor

    /**
     * Returns a new [TileColor] which is toned by the [DEFAULT_FACTOR] (.7).
     */
    fun tone(): TileColor = tone(defaultFactor())

    /**
     * Returns a new [TileColor] which is toned by [factor].
     */
    fun tone(factor: Double): TileColor

    /**
     * Returns a new [TileColor] which is the inversion of this one.
     */
    fun invert(): TileColor

    /**
     * Returns a new [TileColor] which is darkened by the given [percentage].
     * The number must be between `0` and `1`.
     */
    fun darkenByPercent(percentage: Double): TileColor

    /**
     * Returns a new [TileColor] which is lightened by the given [percentage].
     * The number must be between `0` and `1`.
     */
    fun lightenByPercent(percentage: Double): TileColor

    /**
     * Creates a copy of this [TileColor] with the given [alpha].
     */
    fun withAlpha(alpha: Int): TileColor

    /**
     * Creates a copy of this [TileColor] with the given [red].
     */
    fun withRed(red: Int): TileColor

    /**
     * Creates a copy of this [TileColor] with the given [green].
     */
    fun withGreen(green: Int): TileColor

    /**
     * Creates a copy of this [TileColor] with the given [blue].
     */
    fun withBlue(blue: Int): TileColor

    /**
     * Creates a new [ColorInterpolator] with the receiver color as low color and the other color as high color.
     */
    fun interpolateTo(other: TileColor): ColorInterpolator

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
         *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultTileColor] color entry by
         *  parsing the tuples as Red, Green and Blue.
         */
        @Suppress("DEPRECATION")
        fun fromString(value: String): TileColor {
            value.trim { it <= ' ' }.let { cleanValue ->
                try {
                    val uc = cleanValue.toUpperCase()
                    return if (ANSITileColor.values()
                            .map { it.name }
                            .contains(uc)
                    ) {
                        ANSITileColor.valueOf(uc)
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
            return DefaultTileColor(red, green, blue, alpha)
        }

        private val TRANSPARENT = create(0, 0, 0, 0)

        const val DEFAULT_ALPHA = 255
        const val DEFAULT_FACTOR = 0.7
    }
}
