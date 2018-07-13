package org.codetome.zircon.api.component

import org.codetome.zircon.api.color.TextColor

interface ColorTheme {

    fun getBrightForegroundColor(): TextColor

    fun getDarkForegroundColor(): TextColor

    fun getBrightBackgroundColor(): TextColor

    fun getDarkBackgroundColor(): TextColor

    fun getAccentColor(): TextColor
}
