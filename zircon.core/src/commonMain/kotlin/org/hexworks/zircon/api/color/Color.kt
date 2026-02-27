@file:Suppress("MemberVisibilityCanBePrivate")

package org.hexworks.zircon.api.color

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.color.ANSIColor.BLACK
import org.hexworks.zircon.api.color.ANSIColor.WHITE
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

    val component1: Int
        get() = red
    val component2: Int
        get() = green
    val component3: Int
        get() = blue
    val component4: Int
        get() = alpha

    companion object {

        /**
         * The default foreground color is `WHITE`.
         */
        val DEFAULT_FOREGROUND_COLOR = DefaultAnsiPalette[WHITE]

        /**
         * The default background color is `BLACK`.
         */
        val DEFAULT_BACKGROUND_COLOR = DefaultAnsiPalette[BLACK]

        val TRANSPARENT = create(0, 0, 0, 0)
        const val DEFAULT_ALPHA = 255
        const val DEFAULT_FACTOR = 0.7

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

    }
}
