package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.*
import org.codetome.zircon.api.factory.TextColorFactory

class DefaultHeader (private val text: String,
                    initialSize: Size,
                    position: Position,
                    componentStyles: ComponentStyles) : Header, DefaultComponent(
        initialSize = initialSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = listOf()) {


    init {
        getDrawSurface().putText(text, Position.DEFAULT_POSITION)
    }

    override fun getText() = text

    override fun acceptsFocus(): Boolean {
        return false
    }

    override fun giveFocus(): Boolean {
        return false
    }

    override fun takeFocus() {

    }

    override fun applyTheme(theme: Theme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getBrightForegroundColor())
                        .backgroundColor(TextColorFactory.TRANSPARENT)
                        .build())
                .build())
    }
}