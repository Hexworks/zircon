package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe

class DefaultLabel(private val text: String,
                   private val renderingStrategy: ComponentRenderingStrategy<Label>,
                   initialTileset: TilesetResource,
                   initialSize: Size,
                   position: Position,
                   componentStyleSet: ComponentStyleSet)
    : Label, DefaultComponent(
        size = initialSize,
        position = position,
        componentStyles = componentStyleSet,
        tileset = initialTileset) {


    init {
        render()
    }

    override fun getText() = text

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(TileColor.transparent())
                        .build())
                .build().also {
                    setComponentStyleSet(it)
                    render()
                }
    }

    private fun render() {
        renderingStrategy.render(this, tileGraphics())
    }
}
