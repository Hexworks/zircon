package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.Header
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.util.Maybe
import java.util.*

class DefaultHeader(private val text: String,
                    initialSize: Size,
                    initialFont: Font,
                    position: Position,
                    componentStyleSet: ComponentStyleSet) : Header, DefaultComponent(
        initialSize = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = listOf(),
        initialFont = initialFont) {


    init {
        getDrawSurface().putText(text, Position.defaultPosition())
    }

    override fun getText() = text

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getBrightForegroundColor())
                        .backgroundColor(TextColorFactory.transparent())
                        .build())
                .build())
    }
}
