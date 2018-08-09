package org.hexworks.zircon.api

import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.internal.color.DefaultTextColor
import java.awt.Color

object TileColors {

    /**
     * The default foreground color is `WHITE`.
     */
    @JvmStatic
    fun defaultForegroundColor() = TileColor.defaultForegroundColor()

    /**
     * The default background color is `BLACK`.
     */
    @JvmStatic
    fun defaultBackgroundColor() = TileColor.defaultBackgroundColor()

    /**
     * Shorthand for a [TileColor] which is fully transparent.
     */
    @JvmStatic
    fun transparent() = TileColor.transparent()

    @JvmStatic
    fun defaultAlpha() = TileColor.defaultAlpha()

    /**
     * Parses a string into a color. Formats:
     *  * *blue* - Constant value from the [ANSITileColor] enum
     *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultTextColor] color entry by
     *  parsing the tuples as Red, Green and Blue.
     */
    @JvmStatic
    fun fromString(value: String): TileColor = TileColor.fromString(value)

    /**
     * Creates a new [TileColor].
     */
    @JvmStatic
    @JvmOverloads
    fun create(red: Int, green: Int, blue: Int, alpha: Int = 255): TileColor =
            TileColor.create(red, green, blue, alpha)

}

/**
 * Extension for easy conversion between [TileColor] and awt [Color].
 */
fun TileColor.toAWTColor(): java.awt.Color = Color(getRed(), getGreen(), getBlue(), getAlpha())
