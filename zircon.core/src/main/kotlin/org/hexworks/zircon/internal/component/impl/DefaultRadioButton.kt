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
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.*
import org.hexworks.zircon.internal.event.ZirconEvent

class DefaultRadioButton(private val text: String,
                         private val renderingStrategy: ComponentRenderingStrategy<RadioButton>,
                         tileset: TilesetResource,
                         size: Size,
                         position: Position,
                         componentStyleSet: ComponentStyleSet)
    : RadioButton, DefaultComponent(
        size = size,
        position = position,
        componentStyles = componentStyleSet,
        tileset = tileset) {

    private var state = NOT_SELECTED
    private var selected = false

    init {
        render()
        EventBus.listenTo<ZirconEvent.MouseOver>(id) {
            componentStyleSet().applyMouseOverStyle()
            render()
        }
        EventBus.listenTo<ZirconEvent.MouseOut>(id) {
            state = if (selected) SELECTED else NOT_SELECTED
            componentStyleSet().reset()
            render()
        }
        EventBus.listenTo<ZirconEvent.MousePressed>(id) {
            state = PRESSED
            componentStyleSet().applyActiveStyle()
            render()
        }
        EventBus.listenTo<ZirconEvent.MouseReleased>(id) {
            componentStyleSet().applyMouseOverStyle()
            selected = true
            state = SELECTED
            render()
        }
    }

    override fun isSelected() = selected

    override fun state() = state

    fun select() {
        if (selected) {
            componentStyleSet().applyMouseOverStyle()
            state = SELECTED
            selected = true
            render()
        }
    }

    fun removeSelection() =
            if (selected) {
                componentStyleSet().reset()
                selected = false
                state = NOT_SELECTED
                render()
                true
            } else {
                false
            }

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

    private fun render() {
        renderingStrategy.render(this, tileGraphics())
    }

    enum class RadioButtonState {
        PRESSED,
        SELECTED,
        NOT_SELECTED
    }
}
