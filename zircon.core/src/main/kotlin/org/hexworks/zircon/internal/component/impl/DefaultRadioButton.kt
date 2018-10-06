package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.*

class DefaultRadioButton(componentMetadata: ComponentMetadata,
                         override val text: String,
                         private val renderingStrategy: ComponentRenderingStrategy<RadioButton>)
    : RadioButton, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    override val state: RadioButtonState
        get() = currentState

    private var currentState = NOT_SELECTED
    private var selected = false

    init {
        render()
    }

    override fun mouseEntered(action: MouseAction) {
        componentStyleSet.applyMouseOverStyle()
        render()
    }

    override fun mouseExited(action: MouseAction) {
        currentState = if (selected) SELECTED else NOT_SELECTED
        componentStyleSet.reset()
        render()
    }

    override fun mousePressed(action: MouseAction) {
        currentState = PRESSED
        componentStyleSet.applyActiveStyle()
        render()
    }

    override fun mouseReleased(action: MouseAction) {
        select()
    }

    override fun isSelected() = selected

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        componentStyleSet.applyFocusedStyle()
        render()
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
        componentStyleSet.reset()
        render()
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.accentColor)
                        .backgroundColor(TileColor.transparent())
                        .build())
                .mouseOverStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.primaryBackgroundColor)
                        .backgroundColor(colorTheme.accentColor)
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryBackgroundColor)
                        .backgroundColor(colorTheme.accentColor)
                        .build())
                .activeStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor)
                        .backgroundColor(colorTheme.accentColor)
                        .build())
                .build().also {
                    componentStyleSet = it
                    render()
                }
    }

    override fun render() {
        renderingStrategy.render(this, tileGraphics)
    }

    fun select() {
        componentStyleSet.applyMouseOverStyle()
        currentState = SELECTED
        selected = true
        render()
    }

    fun removeSelection() {
        componentStyleSet.reset()
        currentState = NOT_SELECTED
        selected = false
        render()
    }

    enum class RadioButtonState {
        PRESSED,
        SELECTED,
        NOT_SELECTED
    }
}
