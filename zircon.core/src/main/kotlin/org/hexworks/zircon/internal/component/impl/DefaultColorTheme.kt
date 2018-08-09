package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme

data class DefaultColorTheme(private val brightForegroundColor: TileColor,
                             private val brightBackgroundColor: TileColor,
                             private val darkForegroundColor: TileColor,
                             private val darkBackgroundColor: TileColor,
                             private val accentColor: TileColor) : ColorTheme {

    override fun getBrightForegroundColor() = brightForegroundColor

    override fun getDarkForegroundColor() = darkForegroundColor

    override fun getBrightBackgroundColor() = brightBackgroundColor

    override fun getDarkBackgroundColor() = darkBackgroundColor

    override fun getAccentColor() = accentColor
}
