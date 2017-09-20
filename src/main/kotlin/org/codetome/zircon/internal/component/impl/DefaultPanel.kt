package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Panel
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.internal.component.WrappingStrategy

class DefaultPanel(private val title: String,
                   initialSize: Size,
                   position: Position,
                   componentStyles: ComponentStyles,
                   wrappers: Iterable<WrappingStrategy> = listOf())
    : Panel, DefaultContainer(initialSize = initialSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = wrappers) {

    override fun getTitle() = title

    override fun applyTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getBrightForegroundColor())
                        .backgroundColor(colorTheme.getBrightBackgroundColor())
                        .build())
                .build())
        getComponents().forEach {
            it.applyTheme(colorTheme)
        }
    }
}