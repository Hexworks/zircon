package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.Panel
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.internal.component.WrappingStrategy

class DefaultPanel(private val title: String,
                   initialSize: Size,
                   position: Position,
                   initialTileset: TilesetResource<out Tile>,
                   componentStyleSet: ComponentStyleSet,
                   wrappers: Iterable<WrappingStrategy> = listOf())
    : Panel, DefaultContainer(initialSize = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = wrappers,
        initialTileset = initialTileset) {

    override fun getTitle() = title

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getBrightForegroundColor())
                        .backgroundColor(colorTheme.getBrightBackgroundColor())
                        .build())
                .build())
        getComponents().forEach {
            it.applyColorTheme(colorTheme)
        }
    }
}
