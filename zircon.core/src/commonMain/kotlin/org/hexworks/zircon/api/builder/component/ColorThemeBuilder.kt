package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.internal.component.impl.DefaultColorTheme
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class ColorThemeBuilder: Builder<ColorTheme> {

    var name: String = "anonymous"
    var primaryForegroundColor: TileColor = TileColor.defaultForegroundColor()
    var secondaryForegroundColor: TileColor = TileColor.defaultForegroundColor()
    var primaryBackgroundColor: TileColor = TileColor.defaultBackgroundColor()
    var secondaryBackgroundColor: TileColor = TileColor.defaultBackgroundColor()
    var accentColor: TileColor = TileColor.defaultForegroundColor()

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


