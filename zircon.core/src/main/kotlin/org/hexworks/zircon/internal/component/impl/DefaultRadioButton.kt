package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.ComponentDecorationRenderer
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.*
import org.hexworks.zircon.internal.util.ThreadSafeQueue

class DefaultRadioButton(private val text: String,
                         wrappers: ThreadSafeQueue<ComponentDecorationRenderer>,
                         width: Int,
                         initialTileset: TilesetResource,
                         position: Position,
                         componentStyleSet: ComponentStyleSet)
    : RadioButton, DefaultComponent(
        size = Size.create(width, 1),
        position = position,
        componentStyles = componentStyleSet,
        wrappers = wrappers,
        tileset = initialTileset) {

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
        tileGraphics().putText("${STATES[state]} $clearedText")
    }

    override fun isSelected() = state == SELECTED

    fun select() {
        if (state != SELECTED) {
            tileGraphics().applyStyle(componentStyleSet().applyMouseOverStyle())
            state = SELECTED
            redrawContent()
        }
    }

    fun removeSelection() =
            if (state != NOT_SELECTED) {
                tileGraphics().applyStyle(componentStyleSet().reset())
                state = NOT_SELECTED
                redrawContent()
                true
            } else {
                false
            }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        tileGraphics().applyStyle(componentStyleSet().applyFocusedStyle())
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
        tileGraphics().applyStyle(componentStyleSet().reset())
    }

    override fun getText() = text

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
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
                .build().also {
                    setComponentStyleSet(it)
                }
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
