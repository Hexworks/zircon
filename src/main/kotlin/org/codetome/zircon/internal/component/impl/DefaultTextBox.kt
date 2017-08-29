package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Scrollable
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.TextBox
import org.codetome.zircon.api.component.Theme
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.*

class DefaultTextBox @JvmOverloads constructor(private var text: String,
                                               initialSize: Size,
                                               visibleSize: Size,
                                               position: Position,
                                               componentStyles: ComponentStyles,
                                               scrollable: Scrollable = DefaultScrollable(initialSize, visibleSize))
    : TextBox, Scrollable by scrollable, DefaultComponent(
        initialSize = visibleSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = listOf()) {

    init {
        text.forEach {
            getDrawSurface().setCharacterAt(
                    position = getCursorPosition().withRelative(getVisibleOffset()),
                    character = it)
            advanceCursor()
        }
    }

    override fun getText() = text

    override fun setText(text: String) {
        this.text = text
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Optional<Input>): Boolean {
        getDrawSurface().applyStyle(getComponentStyles().giveFocus())
        EventBus.emit(EventType.RequestCursorAt, getCursorPosition())
        EventBus.emit(EventType.ComponentChange)
        return true
    }

    override fun takeFocus(input: Optional<Input>) {
        getDrawSurface().applyStyle(getComponentStyles().reset())
        EventBus.emit(EventType.HideCursor)
        EventBus.emit(EventType.ComponentChange)
    }

    override fun applyTheme(theme: Theme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getDarkBackgroundColor())
                        .backgroundColor(theme.getDarkForegroundColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getBrightBackgroundColor())
                        .backgroundColor(theme.getBrightForegroundColor())
                        .build())
                .build())
    }
}