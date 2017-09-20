package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Label
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.factory.TextColorFactory
import org.codetome.zircon.api.input.Input
import java.util.*

class DefaultLabel (private val text: String,
                    initialSize: Size,
                    position: Position,
                    componentStyles: ComponentStyles) : Label, DefaultComponent(
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

    override fun giveFocus(input: Optional<Input>): Boolean {
        return false
    }

    override fun takeFocus(input: Optional<Input>) {

    }

    override fun applyTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkForegroundColor())
                        .backgroundColor(TextColorFactory.TRANSPARENT)
                        .build())
                .build())
    }
}