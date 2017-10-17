package org.codetome.zircon.api.component

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet

interface ColorTheme {

    fun getBrightForegroundColor(): TextColor

    fun getDarkForegroundColor(): TextColor

    fun getBrightBackgroundColor(): TextColor

    fun getDarkBackgroundColor(): TextColor

    fun getAccentColor(): TextColor

    fun toBrightStyle(): StyleSet

    fun toDarkStyle(): StyleSet
}