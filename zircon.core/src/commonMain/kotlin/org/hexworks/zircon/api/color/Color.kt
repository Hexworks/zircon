@file:Suppress("MemberVisibilityCanBePrivate")

package org.hexworks.zircon.api.color

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.color.Color.Companion.DEFAULT_FACTOR
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.color.DefaultColor

/**
 * A [Color] represents the colors of a [Tile]. You can choose to create your own
 * colors using the various factory functions in [Color.Companion] or use a color palette
 * that we support out of the box
 * @see ANSIColor
 * @see Palette
 */
interface Color : Cacheable {

    val red: Int
    val green: Int
    val blue: Int
    val alpha: Int

    val isOpaque: Boolean
        get() = alpha == 255

    /**
     * Returns a new [Color] which is desaturated by the [DEFAULT_FACTOR] (.7).
     */
    fun desaturate(): Color = desaturate(defaultFactor())

    /**
     * Returns a new [Color] which is desaturated by [factor].
     */
    fun desaturate(factor: Double): Color

    /**
     * Returns a new [Color] which is tinted by the [DEFAULT_FACTOR] (.7).
     */
    fun tint(): Color = tint(defaultFactor())

    /**
     * Returns a new [Color] which is tinted by [factor].
     */
    fun tint(factor: Double): Color

    /**
     * Returns a new [Color] which is shaded by the [DEFAULT_FACTOR] (.7).
     */
    fun shade(): Color = shade(defaultFactor())

    /**
     * Returns a new [Color] which is shaded by [factor].
     */
    fun shade(factor: Double): Color

    /**
     * Returns a new [Color] which is toned by the [DEFAULT_FACTOR] (.7).
     */
    fun tone(): Color = tone(defaultFactor())

    /**
     * Returns a new [Color] which is toned by [factor].
     */
    fun tone(factor: Double): Color

    /**
     * Returns a new [Color] which is the inversion of this one.
     */
    fun invert(): Color

    /**
     * Returns a new [Color] which is darkened by the given [percentage].
     * The number must be between `0` and `1`.
     */
    fun darkenByPercent(percentage: Double): Color

    /**
     * Returns a new [Color] which is lightened by the given [percentage].
     * The number must be between `0` and `1`.
     */
    fun lightenByPercent(percentage: Double): Color

    /**
     * Creates a copy of this [Color] with the given [alpha].
     */
    fun withAlpha(alpha: Int): Color

    /**
     * Creates a copy of this [Color] with the given [red].
     */
    fun withRed(red: Int): Color

    /**
     * Creates a copy of this [Color] with the given [green].
     */
    fun withGreen(green: Int): Color

    /**
     * Creates a copy of this [Color] with the given [blue].
     */
    fun withBlue(blue: Int): Color

    /**
     * Creates a new [ColorInterpolator] with the receiver color as low color and the other color as high color.
     */
    fun interpolateTo(other: Color): ColorInterpolator

    companion object {

        /**
         * The default foreground color is `WHITE`.
         */
        fun defaultForegroundColor() = DefaultAnsiPalette[ANSIColor.WHITE]

        /**
         * The default background color is `BLACK`.
         */
        fun defaultBackgroundColor() = DefaultAnsiPalette[ANSIColor.BLACK]

        /**
         * Shorthand for a [Color] which is fully transparent.
         */
        fun transparent() = TRANSPARENT

        fun defaultAlpha() = DEFAULT_ALPHA

        fun defaultFactor() = DEFAULT_FACTOR

        /**
         * Parses a string into a color. Formats:
         *  * *blue* - Constant value from the [ANSIColor] enum
         *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultColor] color entry by
         *  parsing the tuples as Red, Green and Blue.
         */
        @Suppress("DEPRECATION")
        fun fromString(value: String): Color {
            value.trim { it <= ' ' }.let { cleanValue ->
                try {
                    val r = cleanValue.substring(1, 3).toInt(16)
                    val g = cleanValue.substring(3, 5).toInt(16)
                    val b = cleanValue.substring(5, 7).toInt(16)
                    return create(r, g, b)
                } catch (e: Exception) {
                    throw IllegalArgumentException("Unknown color definition '$cleanValue'", e)
                }
            }
        }

        /**
         * Creates a new [Color].
         */
        fun create(red: Int, green: Int, blue: Int, alpha: Int = 255): Color {
            return DefaultColor(red, green, blue, alpha)
        }

        private val TRANSPARENT = create(0, 0, 0, 0)

        const val DEFAULT_ALPHA = 255
        const val DEFAULT_FACTOR = 0.7
    }
}
