package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.component.ColorTheme

data class DefaultColorTheme(private val brightForegroundColor: TextColor,
                             private val brightBackgroundColor: TextColor,
                             private val darkForegroundColor: TextColor,
                             private val darkBackgroundColor: TextColor,
                             private val accentColor: TextColor) : ColorTheme {

    override fun getBrightForegroundColor() = brightForegroundColor

    override fun getDarkForegroundColor() = darkForegroundColor

    override fun getBrightBackgroundColor() = brightBackgroundColor

    override fun getDarkBackgroundColor() = darkBackgroundColor

    override fun getAccentColor() = accentColor
}
