package org.codetome.zircon.api.component

import org.codetome.zircon.api.color.TextColor

interface Theme {

    fun getBrightForegroundColor(): TextColor

    fun getDarkForegroundColor(): TextColor

    fun getBrightBackgroundColor(): TextColor

    fun getDarkBackgroundColor(): TextColor

    fun getAccentColor(): TextColor
}