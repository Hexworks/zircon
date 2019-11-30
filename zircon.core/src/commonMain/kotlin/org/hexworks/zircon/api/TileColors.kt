package org.hexworks.zircon.api

import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.internal.color.DefaultTileColor
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

object TileColors {

    /**
     * The default foreground color is `WHITE`.
     */
    @JvmStatic
    @Deprecated("Use TileColor.defaultForegroundColor instead", replaceWith = ReplaceWith(
            "TileColor.defaultForegroundColor", "org.hexworks.zircon.api.color.TileColor"))
    fun defaultForegroundColor() = TileColor.defaultForegroundColor()

    /**
     * The default background color is `BLACK`.
     */
    @JvmStatic
    @Deprecated("Use TileColor.defaultBackgroundColor instead", replaceWith = ReplaceWith(
            "TileColor.defaultBackgroundColor", "org.hexworks.zircon.api.color.TileColor"))
    fun defaultBackgroundColor() = TileColor.defaultBackgroundColor()

    /**
     * Shorthand for a [TileColor] which is fully transparent.
     */
    @JvmStatic
    @Deprecated("Use TileColor.transparent instead", replaceWith = ReplaceWith(
            "TileColor.transparent", "org.hexworks.zircon.api.color.TileColor"))
    fun transparent() = TileColor.transparent()

    @JvmStatic
    @Deprecated("Use TileColor.defaultAlpha instead", replaceWith = ReplaceWith(
            "TileColor.defaultAlpha", "org.hexworks.zircon.api.color.TileColor"))
    fun defaultAlpha() = TileColor.defaultAlpha()

    /**
     * Parses a string into a color. Formats:
     *  * *blue* - Constant value from the [ANSITileColor] enum
     *  * *#1a1a1a* - Hash character followed by three hex-decimal tuples; creates a [DefaultTileColor] color entry by
     *  parsing the tuples as Red, Green and Blue.
     */
    @JvmStatic
    @Deprecated("Use TileColor.fromString instead", replaceWith = ReplaceWith(
            "TileColor.fromString", "org.hexworks.zircon.api.color.TileColor"))
    fun fromString(value: String): TileColor = TileColor.fromString(value)

    /**
     * Creates a new [TileColor].
     */
    @JvmStatic
    @JvmOverloads
    @Deprecated("Use TileColor.create instead", replaceWith = ReplaceWith(
            "TileColor.create", "org.hexworks.zircon.api.color.TileColor"))
    fun create(red: Int, green: Int, blue: Int, alpha: Int = 255): TileColor =
            TileColor.create(red, green, blue, alpha)

}
