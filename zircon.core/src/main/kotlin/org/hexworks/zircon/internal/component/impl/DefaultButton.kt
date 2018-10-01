package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.event.ZirconEvent

class DefaultButton(private val text: String,
                    private val renderingStrategy: ComponentRenderingStrategy<Button>,
                    position: Position,
                    size: Size,
                    tileset: TilesetResource,
                    componentStyleSet: ComponentStyleSet)
    : Button, DefaultComponent(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    init {
        render()
    }

    // TODO: move these to DefaultComponent since this is applicable almost everywhere

    override fun mouseEntered(action: MouseAction) {
        componentStyleSet().applyMouseOverStyle()
        render()
    }

    override fun mouseExited(action: MouseAction) {
        componentStyleSet().reset()
        render()
    }

    override fun mousePressed(action: MouseAction) {
        componentStyleSet().applyActiveStyle()
        render()
    }

    override fun mouseReleased(action: MouseAction) {
        componentStyleSet().applyMouseOverStyle()
        render()
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

    override fun render() {
        renderingStrategy.render(this, tileGraphics())
    }
}
