package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.WrappingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.*
import org.hexworks.zircon.internal.event.InternalEvent
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.internal.util.ThreadSafeQueue

class DefaultCheckBox(private val text: String,
                      wrappers: ThreadSafeQueue<WrappingStrategy>,
                      width: Int,
                      initialTileset: TilesetResource,
                      position: Position,
                      componentStyleSet: ComponentStyleSet)
    : CheckBox, DefaultComponent(size = Size.create(width, 1),
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = wrappers,
        tileset = initialTileset) {

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
//        EventBus.subscribe<MouseAction>(EventType.MousePressed(id), {
//            getDrawSurface().applyStyle(getComponentStyles().applyActiveStyle())
//            checkBoxState = PRESSED
//            redrawContent()
//            EventBus.broadcast(EventType.ComponentChange)
//        })
        EventBus.listenTo<InternalEvent.MouseReleased>(id) {
            getDrawSurface().applyStyle(getComponentStyles().applyMouseOverStyle())
            checkBoxState = if (checked) UNCHECKED else CHECKED
            checked = checked.not()
            redrawContent()
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
        getDrawSurface().applyStyle(getComponentStyles().applyFocusedStyle())
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
        getDrawSurface().applyStyle(getComponentStyles().reset())
    }

    override fun getText() = text

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.accentColor())
                        .backgroundColor(TileColor.transparent())
                        .build())
                .mouseOverStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.primaryBackgroundColor())
                        .backgroundColor(colorTheme.accentColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryBackgroundColor())
                        .backgroundColor(colorTheme.accentColor())
                        .build())
                .activeStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(colorTheme.accentColor())
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
