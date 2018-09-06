package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.ComponentDecorationRenderer

class DefaultPanel(private val title: String,
                   initialSize: Size,
                   position: Position,
                   initialTileset: TilesetResource,
                   componentStyleSet: ComponentStyleSet,
                   wrappers: Iterable<ComponentDecorationRenderer> = listOf())
    : Panel, DefaultContainer(initialSize = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = wrappers,
        initialTileset = initialTileset) {

    override fun getTitle() = title

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.primaryForegroundColor())
                        .backgroundColor(colorTheme.primaryBackgroundColor())
                        .build())
                .build().also { css ->
                    setComponentStyleSet(css)
                    getComponents().forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }
}
