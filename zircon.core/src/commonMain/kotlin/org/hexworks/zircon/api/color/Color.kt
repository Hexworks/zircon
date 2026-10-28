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
public interface Color : Cacheable {

    public val red: Int
    public val green: Int
    public val blue: Int

    /**
     * A transparent color draws nothing, so whatever is below shows through. There is no
     * partial transparency: a color is either fully opaque or [TRANSPARENT].
     */
    public val isTransparent: Boolean

    public val component1: Int
        get() = red
    public val component2: Int
        get() = green
    public val component3: Int
        get() = blue

    public companion object {

        // Getters, not stored values: DefaultAnsiPalette's initializer calls Color.create,
        // so storing these here would create a class-initialization cycle.

        /**
         * The default foreground color is `WHITE`.
         */
        public val DEFAULT_FOREGROUND_COLOR: Color get() = DefaultAnsiPalette[WHITE]

        /**
         * The default background color is `BLACK`.
         */
        public val DEFAULT_BACKGROUND_COLOR: Color get() = DefaultAnsiPalette[BLACK]

        public val TRANSPARENT: Color = DefaultColor(0, 0, 0, isTransparent = true)
        public const val DEFAULT_FACTOR: Double = 0.7

        /**
         * Parses a string into a color. Formats:
         *  * *blue* - Constant value from the [ANSIColor] enum
         *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultColor] color entry by
         *  parsing the tuples as Red, Green and Blue.
         */
        @Suppress("DEPRECATION")
        public fun fromString(value: String): Color {
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
        public fun create(red: Int, green: Int, blue: Int): Color {
            return DefaultColor(red, green, blue)
        }

    }
}
