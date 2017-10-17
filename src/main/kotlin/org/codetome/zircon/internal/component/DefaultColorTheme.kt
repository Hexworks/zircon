package org.codetome.zircon.internal.component

import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.graphics.StyleSet

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

    // TODO: test these

    override fun toBrightStyle() = StyleSetBuilder.newBuilder()
            .backgroundColor(brightBackgroundColor)
            .foregroundColor(brightForegroundColor)
            .build()

    override fun toDarkStyle() = StyleSetBuilder.newBuilder()
            .backgroundColor(darkBackgroundColor)
            .foregroundColor(darkForegroundColor)
            .build()
}