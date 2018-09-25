package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.*

class DefaultRadioButton(private val text: String,
                         private val renderingStrategy: ComponentRenderingStrategy<RadioButton>,
                         position: Position,
                         size: Size,
                         tileset: TilesetResource,
                         componentStyleSet: ComponentStyleSet)
    : RadioButton, DefaultComponent(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    private var state = NOT_SELECTED
    private var selected = false

    init {
        render()
    }

    override fun mouseEntered(action: MouseAction) {
        componentStyleSet().applyMouseOverStyle()
        render()
    }

    override fun mouseExited(action: MouseAction) {
        state = if (selected) SELECTED else NOT_SELECTED
        componentStyleSet().reset()
        render()
    }

    override fun mousePressed(action: MouseAction) {
        state = PRESSED
        componentStyleSet().applyActiveStyle()
        render()
    }

    override fun mouseReleased(action: MouseAction) {
        select()
    }

    override fun isSelected() = selected

    override fun state() = state

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        componentStyleSet().applyFocusedStyle()
        render()
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
        componentStyleSet().reset()
        render()
    }

    override fun text() = text

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
                    render()
                }
    }

    override fun render() {
        renderingStrategy.render(this, tileGraphics())
    }

    fun select() {
        componentStyleSet().applyMouseOverStyle()
        state = SELECTED
        selected = true
        render()
    }

    fun removeSelection() {
        componentStyleSet().reset()
        state = NOT_SELECTED
        selected = false
        render()
    }

    enum class RadioButtonState {
        PRESSED,
        SELECTED,
        NOT_SELECTED
    }
}
