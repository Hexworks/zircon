package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme

data class DefaultColorTheme(private val primaryForegroundColor: TileColor,
                             private val secondaryForegroundColor: TileColor,
                             private val primaryBackgroundColor: TileColor,
                             private val secondaryBackgroundColor: TileColor,
                             private val accentColor: TileColor) : ColorTheme {

    override fun primaryForegroundColor() = primaryForegroundColor

    override fun secondaryForegroundColor() = secondaryForegroundColor

    override fun primaryBackgroundColor() = primaryBackgroundColor

    override fun secondaryBackgroundColor() = secondaryBackgroundColor

    // TODO: secondary accent color
    override fun accentColor() = accentColor
}
