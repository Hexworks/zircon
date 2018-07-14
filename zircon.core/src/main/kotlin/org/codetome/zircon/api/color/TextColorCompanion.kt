package org.codetome.zircon.api.color

import org.codetome.zircon.platform.factory.TextColorFactory

interface TextColorCompanion {

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
    fun transparent() = TextColorFactory.create(0, 0, 0, 0)

    /**
     * Creates a [TextColor] from a <code>red</code>, <code>green</code>, <code>blue</code> triple.
     */
    fun fromRGB(red: Int, green: Int, blue: Int, alpha: Int = 255): TextColor = TextColorFactory.create(red, green, blue, alpha)

    /**
     * Parses a string into a color. Formats:
     *  * *blue* - Constant value from the [ANSITextColor] enum
     *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultTextColor] color entry by
     *  parsing the tuples as Red, Green and Blue.
     */
    fun fromString(value: String): TextColor

    fun defaultAlpha() = 255

    fun rgbColorPattern() = "#[0-9a-fA-F]{6}"
}
