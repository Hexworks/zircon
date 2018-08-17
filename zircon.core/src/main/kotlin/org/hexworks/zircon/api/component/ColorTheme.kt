package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.color.TileColor

interface ColorTheme {

    fun primaryForegroundColor(): TileColor

    fun secondaryForegroundColor(): TileColor

    fun primaryBackgroundColor(): TileColor

    fun secondaryBackgroundColor(): TileColor

    fun accentColor(): TileColor
}
