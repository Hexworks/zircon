package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.component.Button
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.modifier.BorderBuilder
import org.codetome.zircon.api.modifier.BorderType
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.component.impl.wrapping.BorderWrappingStrategy
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.util.Maybe
import java.util.*

class DefaultButton(private val text: String,
                    initialFont: Font,
                    wrappers: Deque<WrappingStrategy>,
                    initialSize: Size,
                    position: Position,
                    componentStyleSet: ComponentStyleSet)
    : Button, DefaultComponent(initialSize = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = wrappers,
        initialFont = initialFont) {

    init {
        getDrawSurface().putText(text, getWrapperOffset())

        EventBus.listenTo<Event.MousePressed>(getId()) {
            getDrawSurface().applyStyle(getComponentStyles().activate())
            EventBus.broadcast(Event.ComponentChange)
        }
        EventBus.listenTo<Event.MouseReleased>(getId()) {
            getDrawSurface().applyStyle(getComponentStyles().mouseOver())
            EventBus.broadcast(Event.ComponentChange)
        }
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        getDrawSurface().applyStyle(getComponentStyles().giveFocus())
        EventBus.broadcast(Event.ComponentChange)
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
        getDrawSurface().applyStyle(getComponentStyles().reset())
        EventBus.broadcast(Event.ComponentChange)
    }

    override fun getText() = text

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getAccentColor())
                        .backgroundColor(TextColorFactory.transparent())
                        .build())
                .mouseOverStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getBrightBackgroundColor())
                        .backgroundColor(colorTheme.getAccentColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkBackgroundColor())
                        .backgroundColor(colorTheme.getAccentColor())
                        .build())
                .activeStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkForegroundColor())
                        .backgroundColor(colorTheme.getAccentColor())
                        .build())
                .build())
    }

    companion object {
        // TODO: fix this later
        val BOX_HIGHLIGHT = BorderWrappingStrategy(BorderBuilder.newBuilder()
                .borderType(BorderType.DOTTED)
                .build())
    }
}
