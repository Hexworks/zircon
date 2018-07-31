package org.codetome.zircon.api.color

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.internal.color.DefaultTextColor

interface TextColor : Cacheable {

    fun isOpaque() = getAlpha() == 255

    fun getAlpha(): Int

    fun getRed(): Int

    fun getGreen(): Int

    fun getBlue(): Int

    fun tint(): TextColor

    fun shade(): TextColor

    fun invert(): TextColor

    fun darkenByPercent(percentage: Double): TextColor

    override fun generateCacheKey(): String {
        return "a:${getAlpha()}r:${getRed()}g:${getGreen()}b:${getBlue()}"
    }

    companion object {

        /**
         * The default foreground color is `WHITE`.
         */
        fun defaultForegroundColor() = ANSITextColor.WHITE

        /**
         * The default background color is `BLACK`.
         */
        fun defaultBackgroundColor() = ANSITextColor.BLACK

        /**
         * Shorthand for a [TextColor] which is fully transparent.
         */
        fun transparent() = TRANSPARENT

        fun defaultAlpha() = 255

        /**
         * Parses a string into a color. Formats:
         *  * *blue* - Constant value from the [ANSITextColor] enum
         *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultTextColor] color entry by
         *  parsing the tuples as Red, Green and Blue.
         */
        fun fromString(value: String): TextColor {
            value.trim { it <= ' ' }.let { cleanValue ->
                try {
                    return if (ANSITextColor.COLOR_NAMES.contains(cleanValue.toUpperCase())) {
                        ANSITextColor.valueOf(cleanValue.toUpperCase())
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
         * Creates a new [TextColor].
         */
        fun create(red: Int, green: Int, blue: Int, alpha: Int = 255): TextColor {
            return DefaultTextColor(red, green, blue, alpha)
        }

        private val TRANSPARENT = TextColor.create(0, 0, 0, 0)
    }
}
