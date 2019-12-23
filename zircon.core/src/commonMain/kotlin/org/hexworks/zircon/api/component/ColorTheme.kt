package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.color.TileColor

interface ColorTheme {
    val isDefault: Boolean
        get() = this == ColorThemes.default()
    val primaryForegroundColor: TileColor
    val secondaryForegroundColor: TileColor
    val primaryBackgroundColor: TileColor
    val secondaryBackgroundColor: TileColor
    val accentColor: TileColor
}
