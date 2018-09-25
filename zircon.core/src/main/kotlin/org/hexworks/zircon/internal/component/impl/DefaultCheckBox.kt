package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.*

class DefaultCheckBox(private val text: String,
                      private val renderingStrategy: ComponentRenderingStrategy<CheckBox>,
                      position: Position,
                      size: Size,
                      tileset: TilesetResource,
                      componentStyleSet: ComponentStyleSet)
    : CheckBox, DefaultComponent(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    private var checkBoxState = UNCHECKED
    private var checked = false
    private var pressing = false

    init {
        render()
    }

    // TODO: test this rudimentary state machine
    override fun mouseEntered(action: MouseAction) {
        componentStyleSet().applyMouseOverStyle()
        render()
    }

    override fun mouseExited(action: MouseAction) {
        pressing = false
        checkBoxState = if (checked) CHECKED else UNCHECKED
        componentStyleSet().reset()
        render()
    }

    override fun mousePressed(action: MouseAction) {
        pressing = true
        checkBoxState = if (checked) UNCHECKING else CHECKING
        componentStyleSet().applyActiveStyle()
        render()
    }

    override fun mouseReleased(action: MouseAction) {
        componentStyleSet().applyMouseOverStyle()
        if (pressing) {
            // this is the case when the user starts pressing outside of this
            // component but releases here
            pressing = false
            checked = checked.not()
            checkBoxState = if (checked) CHECKED else UNCHECKED
        }
        render()
    }

    override fun isChecked() = checkBoxState == CHECKED

    override fun state() = checkBoxState

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

    enum class CheckBoxState {
        CHECKING,
        CHECKED,
        UNCHECKING,
        UNCHECKED
    }
}
