package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.color.TileColor

interface ColorTheme {

    fun getBrightForegroundColor(): TileColor

    fun getDarkForegroundColor(): TileColor

    fun getBrightBackgroundColor(): TileColor

    fun getDarkBackgroundColor(): TileColor

    fun getAccentColor(): TileColor
}
