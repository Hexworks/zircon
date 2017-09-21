package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.CheckBox
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.factory.TextColorFactory
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.*
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.*

class DefaultCheckBox(private val text: String,
                      wrappers: Deque<WrappingStrategy>,
                      width: Int,
                      position: Position,
                      componentStyles: ComponentStyles)
    : CheckBox, DefaultComponent(initialSize = Size.of(width, 1),
        position = position,
        componentStyles = componentStyles,
        wrappers = wrappers) {

    private val maxTextLength = width - BUTTON_WIDTH - 1
    private val clearedText = if (text.length > maxTextLength) {
        text.substring(0, maxTextLength - 3).plus("...")
    } else {
        text
    }
    private var checkBoxState = UNCHECKED
    private var checked = false

    init {
        redrawContent()

        EventBus.subscribe<MouseAction>(EventType.MousePressed(getId()), {
            getDrawSurface().applyColorsFromStyle(getComponentStyles().activate())
            checkBoxState = PRESSED
            redrawContent()
            EventBus.emit(EventType.ComponentChange)
        })
        EventBus.subscribe<MouseAction>(EventType.MouseReleased(getId()), {
            getDrawSurface().applyColorsFromStyle(getComponentStyles().mouseOver())
            checkBoxState = if (checked) UNCHECKED else CHECKED
            checked = checked.not()
            redrawContent()
            EventBus.emit(EventType.ComponentChange)
        })
    }

    private fun redrawContent() {
        getDrawSurface().putText("${STATES["$checkBoxState$checked"]!!} $clearedText")
    }

    override fun isChecked() = checkBoxState == CHECKED

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
                        .foregroundColor(colorTheme.getBrightBackgroundColor())
                        .backgroundColor(colorTheme.getAccentColor())
                        .build())
                .activeStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getBrightBackgroundColor())
                        .backgroundColor(colorTheme.getAccentColor())
                        .build())
                .build())
    }

    private enum class CheckBoxState {
        PRESSED,
        CHECKED,
        UNCHECKED
    }

    companion object {

        private val PRESS_TO_CHECK_BUTTON = "[+]"
        private val PRESS_TO_UNCHECK_BUTTON = "[-]"
        private val CHECKED_BUTTON = "[*]"
        private val UNCHECKED_BUTTON = "[ ]"

        private val BUTTON_WIDTH = PRESS_TO_CHECK_BUTTON.length

        private val STATES = mapOf(
                Pair("${PRESSED}false", PRESS_TO_CHECK_BUTTON),
                Pair("${PRESSED}true", PRESS_TO_UNCHECK_BUTTON),
                Pair("${CHECKED}true", CHECKED_BUTTON),
                Pair("${UNCHECKED}false", UNCHECKED_BUTTON))

    }
}