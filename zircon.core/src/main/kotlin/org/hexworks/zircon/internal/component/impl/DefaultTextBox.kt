package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.platform.util.SystemUtils

class DefaultTextBox(private val text: String,
                     initialSize: Size,
                     initialTileset: TilesetResource,
                     position: Position,
                     componentStyleSet: ComponentStyleSet) : TextBox, DefaultComponent(
        size = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = listOf(),
        tileset = initialTileset) {


    init {
        text.trim().split(SystemUtils.getLineSeparator()).forEachIndexed { idx, line ->
            getDrawSurface().putText(line, Position.create(0, idx))
        }
    }

    override fun getText() = text

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(TileColor.transparent())
                        .build())
                .build())
    }
}
