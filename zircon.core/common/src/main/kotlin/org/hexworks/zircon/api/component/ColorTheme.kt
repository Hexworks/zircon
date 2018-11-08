package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.color.TileColor

interface ColorTheme {
    val primaryForegroundColor: TileColor
    val secondaryForegroundColor: TileColor
    val primaryBackgroundColor: TileColor
    val secondaryBackgroundColor: TileColor
    val accentColor: TileColor
}
