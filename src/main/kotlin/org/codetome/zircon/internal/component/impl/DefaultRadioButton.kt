package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.RadioButton
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.*
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.awt.image.BufferedImage
import java.util.*

class DefaultRadioButton(private val text: String,
                         wrappers: Deque<WrappingStrategy>,
                         width: Int,
                         initialFont: Font<BufferedImage>,
                         position: Position,
                         componentStyles: ComponentStyles)
    : RadioButton, DefaultComponent(initialSize = Size.of(width, 1),
        position = position,
        componentStyles = componentStyles,
        wrappers = wrappers,
        initialFont = initialFont) {

    private val maxTextLength = width - BUTTON_WIDTH - 1
    private val clearedText = if (text.length > maxTextLength) {
        text.substring(0, maxTextLength - 3).plus("...")
    } else {
        text
    }

    private var state = NOT_SELECTED

    init {
        redrawContent()
    }

    private fun redrawContent() {
        getDrawSurface().putText("${STATES[state]} $clearedText")
    }

    override fun isSelected() = state == SELECTED

    fun select() {
        if (state != SELECTED) {
            getDrawSurface().applyColorsFromStyle(getComponentStyles().mouseOver())
            state = SELECTED
            redrawContent()
            EventBus.emit(EventType.ComponentChange)
        }
    }

    fun removeSelection() =
            if (state != NOT_SELECTED) {
                getDrawSurface().applyColorsFromStyle(getComponentStyles().reset())
                state = NOT_SELECTED
                redrawContent()
                EventBus.emit(EventType.ComponentChange)
                true
            } else {
                false
            }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Optional<Input>): Boolean {
        getDrawSurface().applyColorsFromStyle(getComponentStyles().giveFocus())
        EventBus.emit(EventType.ComponentChange)
        return true
    }

    override fun takeFocus(input: Optional<Input>) {
        getDrawSurface().applyColorsFromStyle(getComponentStyles().reset())
        EventBus.emit(EventType.ComponentChange)
    }

    override fun getText() = text

    override fun applyTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getAccentColor())
                        .backgroundColor(TextColorFactory.TRANSPARENT)
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

    enum class RadioButtonState {
        PRESSED,
        SELECTED,
        NOT_SELECTED
    }

    companion object {

        private val PRESSED_BUTTON = "<o>" // TODO: not used now
        private val SELECTED_BUTTON = "<O>"
        private val NOT_SELECTED_BUTTON = "< >"

        private val BUTTON_WIDTH = NOT_SELECTED_BUTTON.length

        private val STATES = mapOf(
                Pair(PRESSED, PRESSED_BUTTON),
                Pair(SELECTED, SELECTED_BUTTON),
                Pair(NOT_SELECTED, NOT_SELECTED_BUTTON))

    }
}