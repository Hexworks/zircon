package org.codetome.zircon.api.color

import org.codetome.zircon.internal.color.DefaultTextColor
import java.util.regex.Pattern

/**
 * Use this factory to create [TextColor]s.
 */
actual object TextColorFactory : TextColorCompanion {

    actual fun create(red: Int, green: Int, blue: Int, alpha: Int): TextColor {
        return DefaultTextColor(red, green, blue, alpha)
    }

    override fun fromString(value: String): TextColor {
        value.trim { it <= ' ' }.let { cleanValue ->
            return if (Pattern.compile(rgbColorPattern()).matcher(cleanValue).matches()) {
                val r = Integer.parseInt(cleanValue.substring(1, 3), 16)
                val g = Integer.parseInt(cleanValue.substring(3, 5), 16)
                val b = Integer.parseInt(cleanValue.substring(5, 7), 16)
                DefaultTextColor(r, g, b)
            } else {
                try {
                    ANSITextColor.valueOf(cleanValue.toUpperCase())
                } catch (e: Exception) {
                    throw IllegalArgumentException("Unknown color definition '$cleanValue'", e)
                }
            }
        }
    }
}
