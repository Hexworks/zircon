package org.codetome.zircon

import java.awt.Color
import java.util.regex.Pattern

interface TextColor {

    fun toColor(): java.awt.Color

    fun getRed(): Int

    fun getGreen(): Int

    fun getBlue(): Int

    enum class ANSI(red: Int, green: Int, blue: Int) : TextColor {
        BLACK(0, 0, 0),
        RED(170, 0, 0),
        GREEN(0, 170, 0),
        YELLOW(170, 85, 0),
        BLUE(0, 0, 170),
        MAGENTA(170, 0, 170),
        CYAN(0, 170, 170),
        WHITE(170, 170, 170),
        DEFAULT(0, 0, 0);

        private val color: java.awt.Color = java.awt.Color(red, green, blue)

        override fun toColor() = color

        override fun getRed() = color.red

        override fun getGreen() = color.green

        override fun getBlue() = color.blue
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

    companion object {
        val DEFAULT_FOREGROUND_COLOR = ANSI.WHITE
        val DEFAULT_BACKGROUND_COLOR = ANSI.BLACK

        private val RGB_COLOR = Pattern.compile("#[0-9a-fA-F]{6}")

        fun fromAWTColor(color: Color) = TextColorImpl(color.red, color.green, color.blue)

        /**
         * Creates a [TextColor] from a <code>red</code>, <code>green</code>, <code>blue</code> triple.
         */
        fun createFrom(red: Int, green: Int, blue: Int) = TextColorImpl(red, green, blue)

        /**
         * Parses a string into a color. Formats:
         *  * *blue* - Constant value from the [ANSI] enum
         *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [TextColorImpl] color entry by
         *  parsing the tuples as Red, Green and Blue.
         */
        fun fromString(value: String): TextColor {
            value.trim { it <= ' ' }.let { cleanValue ->
                return if (RGB_COLOR.matcher(cleanValue).matches()) {
                    val r = Integer.parseInt(cleanValue.substring(1, 3), 16)
                    val g = Integer.parseInt(cleanValue.substring(3, 5), 16)
                    val b = Integer.parseInt(cleanValue.substring(5, 7), 16)
                    TextColor.TextColorImpl(r, g, b)
                } else {
                    try {
                        TextColor.ANSI.valueOf(cleanValue.toUpperCase())
                    } catch (e: Exception) {
                        throw IllegalArgumentException("Unknown color definition \"" + cleanValue + "\"", e)
                    }
                }
            }
        }
    }
}