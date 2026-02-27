package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultColorTheme

@ZirconDsl
class ColorThemeBuilder : Builder<ColorTheme> {

    var name: String = "anonymous"
    var primaryForegroundColor: Color = Color.DEFAULT_FOREGROUND_COLOR
    var secondaryForegroundColor: Color = Color.DEFAULT_FOREGROUND_COLOR
    var primaryBackgroundColor: Color = Color.DEFAULT_BACKGROUND_COLOR
    var secondaryBackgroundColor: Color = Color.DEFAULT_BACKGROUND_COLOR
    var accentColor: Color = Color.DEFAULT_FOREGROUND_COLOR

    override fun build(): ColorTheme = DefaultColorTheme(
        name = name,
        primaryForegroundColor = primaryForegroundColor,
        primaryBackgroundColor = primaryBackgroundColor,
        secondaryForegroundColor = secondaryForegroundColor,
        secondaryBackgroundColor = secondaryBackgroundColor,
        accentColor = accentColor
    )
}

/**
 * Creates a new [ColorTheme] using the component builder DSL and returns it.
 */
fun colorTheme(init: ColorThemeBuilder.() -> Unit): ColorTheme =
    ColorThemeBuilder().apply(init).build()


