package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.Label
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.util.Maybe

class DefaultLabel(private val text: String,
                   initialSize: Size,
                   initialTileset: TilesetResource<out Tile>,
                   position: Position,
                   componentStyleSet: ComponentStyleSet) : Label, DefaultComponent(
        size = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = listOf(),
        tileset = initialTileset) {


    init {
        getDrawSurface().putText(text, Position.defaultPosition())
    }

    override fun getText() = text

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkForegroundColor())
                        .backgroundColor(TextColor.transparent())
                        .build())
                .build())
    }
}
