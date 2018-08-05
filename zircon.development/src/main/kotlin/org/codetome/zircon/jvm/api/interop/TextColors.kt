package org.codetome.zircon.jvm.api.interop

import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.internal.color.DefaultTextColor
import java.awt.Color

object TextColors {

    /**
     * The default foreground color is `WHITE`.
     */
    @JvmStatic
    fun defaultForegroundColor() = TextColor.defaultForegroundColor()

    /**
     * The default background color is `BLACK`.
     */
    @JvmStatic
    fun defaultBackgroundColor() = TextColor.defaultBackgroundColor()

    /**
     * Shorthand for a [TextColor] which is fully transparent.
     */
    @JvmStatic
    fun transparent() = TextColor.transparent()

    @JvmStatic
    fun defaultAlpha() = TextColor.defaultAlpha()

    /**
     * Parses a string into a color. Formats:
     *  * *blue* - Constant value from the [ANSITextColor] enum
     *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultTextColor] color entry by
     *  parsing the tuples as Red, Green and Blue.
     */
    @JvmStatic
    fun fromString(value: String): TextColor = TextColor.fromString(value)

    @JvmStatic
    @JvmOverloads
    fun create(red: Int, green: Int, blue: Int, alpha: Int = 255): TextColor =
            TextColor.create(red, green, blue, alpha)


}

/**
 * Extension for easy conversion between [TextColor] and awt [Color].
 */
fun TextColor.toAWTColor(): java.awt.Color = Color(getRed(), getGreen(), getBlue(), getAlpha())
