package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe

class DefaultHeader(private val text: String,
                    initialSize: Size,
                    initialTileset: TilesetResource,
                    position: Position,
                    componentStyleSet: ComponentStyleSet) : Header, DefaultComponent(
        size = initialSize,
        position = position,
        componentStyles = componentStyleSet,
        wrappers = listOf(),
        tileset = initialTileset) {


    init {
        tileGraphic().putText(text, Position.defaultPosition())
    }

    override fun getText() = text

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.primaryForegroundColor())
                        .backgroundColor(TileColor.transparent())
                        .build())
                .build().also {
                    setComponentStyleSet(it)
                }
    }
}
