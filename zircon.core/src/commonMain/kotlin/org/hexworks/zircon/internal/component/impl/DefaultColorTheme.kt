package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme

data class DefaultColorTheme(
        override val name: String,
        override val primaryForegroundColor: TileColor,
        override val secondaryForegroundColor: TileColor,
        override val primaryBackgroundColor: TileColor,
        override val secondaryBackgroundColor: TileColor,
        override val accentColor: TileColor
) : ColorTheme {

    override fun toString() = name
}
