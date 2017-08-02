package org.codetome.zircon

import java.awt.Color
import java.util.regex.Pattern

object TextColorFactory {

    val DEFAULT_FOREGROUND_COLOR = ANSITextColor.WHITE
    val DEFAULT_BACKGROUND_COLOR = ANSITextColor.BLACK

    private val RGB_COLOR = Pattern.compile("#[0-9a-fA-F]{6}")

    @JvmStatic
    fun fromAWTColor(color: Color) = TextColorImpl(color.red, color.green, color.blue)

    /**
     * Creates a [TextColor] from a <code>red</code>, <code>green</code>, <code>blue</code> triple.
     */
    @JvmStatic
    fun fromRGB(red: Int, green: Int, blue: Int) = TextColorImpl(red, green, blue)

    /**
     * Parses a string into a color. Formats:
     *  * *blue* - Constant value from the [ANSI] enum
     *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [TextColorImpl] color entry by
     *  parsing the tuples as Red, Green and Blue.
     */
    @JvmStatic
    fun fromString(value: String): TextColor {
        value.trim { it <= ' ' }.let { cleanValue ->
            return if (RGB_COLOR.matcher(cleanValue).matches()) {
                val r = Integer.parseInt(cleanValue.substring(1, 3), 16)
                val g = Integer.parseInt(cleanValue.substring(3, 5), 16)
                val b = Integer.parseInt(cleanValue.substring(5, 7), 16)
                TextColorImpl(r, g, b)
            } else {
                try {
                    ANSITextColor.valueOf(cleanValue.toUpperCase())
                } catch (e: Exception) {
                    throw IllegalArgumentException("Unknown color definition \"" + cleanValue + "\"", e)
                }
            }
        }
    }

    data class TextColorImpl(private val red: Int,
                             private val green: Int,
                             private val blue: Int) : TextColor {

        private val color: Color = Color(red, green, blue)

        override fun toColor() = color

        override fun getRed() = red

        override fun getGreen() = green

        override fun getBlue() = blue

    }
}