package org.codetome.zircon.api.interop

import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.internal.color.DefaultTextColor
import org.codetome.zircon.platform.factory.TextColorFactory
import java.awt.Color

object TextColors {

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
    val TRANSPARENT = TextColorFactory.create(0, 0, 0, 0)

    /**
     * Creates a [TextColor] from a <code>red</code>, <code>green</code>, <code>blue</code> triple.
     */
    @JvmStatic
    fun fromRGB(red: Int, green: Int, blue: Int, alpha: Int = 255): TextColor = TextColorFactory.create(red, green, blue, alpha)

    /**
     * Parses a string into a color. Formats:
     *  * *blue* - Constant value from the [ANSITextColor] enum
     *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultTextColor] color entry by
     *  parsing the tuples as Red, Green and Blue.
     */
    @JvmStatic
    fun fromString(value: String)  = TextColor.fromString(value)

}

/**
 * Extension for easy conversion between [TextColor] and awt [Color].
 */
fun TextColor.toAWTColor(): java.awt.Color = Color(getRed(), getGreen(), getBlue(), getAlpha())
