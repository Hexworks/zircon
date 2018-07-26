package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.component.CheckBox
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.*
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.util.ThreadSafeQueue

class DefaultCheckBox(private val text: String,
                      wrappers: ThreadSafeQueue<WrappingStrategy>,
                      width: Int,
                      initialFont: Font,
                      position: Position,
                      componentStyleSet: ComponentStyleSet)
    : CheckBox, DefaultComponent(initialSize = Size.create(width, 1),
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = wrappers,
        initialFont = initialFont) {

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

        // TODO: re-enable in next release and fix the bug when the mouse is moved
        // TODO: after it is pressed and released on another component
        // TODO: the pressed state persists
//        EventBus.subscribe<MouseAction>(EventType.MousePressed(getId()), {
//            getDrawSurface().applyStyle(getComponentStyles().activate())
//            checkBoxState = PRESSED
//            redrawContent()
//            EventBus.broadcast(EventType.ComponentChange)
//        })
        EventBus.listenTo<Event.MouseReleased>(getId()) {
            getDrawSurface().applyStyle(getComponentStyles().mouseOver())
            checkBoxState = if (checked) UNCHECKED else CHECKED
            checked = checked.not()
            redrawContent()
            EventBus.broadcast(Event.ComponentChange)
        }
    }

    private fun redrawContent() {
        getDrawSurface().putText("${STATES["$checkBoxState$checked"]!!} $clearedText")
    }

    override fun isChecked() = checkBoxState == CHECKED

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
                        .backgroundColor(TextColor.transparent())
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
