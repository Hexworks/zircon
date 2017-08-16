package org.codetome.zircon.api

import org.codetome.zircon.color.ANSITextColor
import org.codetome.zircon.color.DefaultTextColor
import org.codetome.zircon.color.TextColor
import java.awt.Color
import java.util.regex.Pattern

object TextColorFactory {

    /**
     * The default foreground color is `WHITE`.
     */
    @JvmField
    val DEFAULT_FOREGROUND_COLOR = ANSITextColor.WHITE

    /**
     * The default background color is `BLACK`.
     */
    @JvmField
    val DEFAULT_BACKGROUND_COLOR = ANSITextColor.BLACK

    /**
     * Shorthand for a [TextColor] which is fully transparent.
     */
    @JvmField
    val TRANSPARENT = DefaultTextColor(0, 0, 0, 0)

    val DEFAULT_ALPHA = 255

    private val RGB_COLOR_PATTERN = Pattern.compile("#[0-9a-fA-F]{6}")

    /**
     * Creates a [TextColor] from an awt [Color].
     */
    @JvmStatic
    fun fromAWTColor(color: Color) = DefaultTextColor(color.red, color.green, color.blue, color.alpha)

    /**
     * Creates a [TextColor] from a <code>red</code>, <code>green</code>, <code>blue</code> triple.
     */
    @JvmStatic
    @JvmOverloads
    fun fromRGB(red: Int, green: Int, blue: Int, alpha: Int = 255) = DefaultTextColor(red, green, blue, alpha)

    /**
     * Parses a string into a color. Formats:
     *  * *blue* - Constant value from the [ANSI] enum
     *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultTextColor] color entry by
     *  parsing the tuples as Red, Green and Blue.
     */
    @JvmStatic
    fun fromString(value: String): TextColor {
        value.trim { it <= ' ' }.let { cleanValue ->
            return if (RGB_COLOR_PATTERN.matcher(cleanValue).matches()) {
                val r = Integer.parseInt(cleanValue.substring(1, 3), 16)
                val g = Integer.parseInt(cleanValue.substring(3, 5), 16)
                val b = Integer.parseInt(cleanValue.substring(5, 7), 16)
                DefaultTextColor(r, g, b)
            } else {
                try {
                    ANSITextColor.valueOf(cleanValue.toUpperCase())
                } catch (e: Exception) {
                    throw IllegalArgumentException("Unknown color definition \"" + cleanValue + "\"", e)
                }
            }
        }
    }
}