package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.Panel
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.component.WrappingStrategy

class DefaultPanel(private val title: String,
                   initialSize: Size,
                   position: Position,
                   initialFont: Font,
                   componentStyleSet: ComponentStyleSet,
                   wrappers: Iterable<WrappingStrategy> = listOf())
    : Panel, DefaultContainer(initialSize = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = wrappers,
        initialFont = initialFont) {

    override fun getTitle() = title

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getBrightForegroundColor())
                        .backgroundColor(colorTheme.getBrightBackgroundColor())
                        .build())
                .build())
        getComponents().forEach {
            it.applyColorTheme(colorTheme)
        }
    }
}
