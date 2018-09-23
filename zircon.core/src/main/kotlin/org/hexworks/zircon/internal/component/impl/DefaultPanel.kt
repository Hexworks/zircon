package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultPanel(private val title: String,
                   private val renderingStrategy: ComponentRenderingStrategy<Panel>,
                   position: Position,
                   size: Size,
                   tileset: TilesetResource,
                   componentStyleSet: ComponentStyleSet)
    : Panel, DefaultContainer(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    init {
        render()
    }

    override fun getTitle() = title

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(colorTheme.primaryBackgroundColor())
                        .build())
                .build().also { css ->
                    setComponentStyleSet(css)
                    render()
                    children().forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }

    override fun render() {
        renderingStrategy.render(this, tileGraphics())
    }
}
