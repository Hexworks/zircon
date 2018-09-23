package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource

class RootContainer(private val renderingStrategy: ComponentRenderingStrategy<RootContainer>,
                    position: Position,
                    size: Size,
                    tileset: TilesetResource,
                    componentStyleSet: ComponentStyleSet)
    : Container, DefaultContainer(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    init {
        render()
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        val css = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(colorTheme.secondaryBackgroundColor())
                        .build())
                .build()
        setComponentStyleSet(css)
        render()
        children().forEach {
            it.applyColorTheme(colorTheme)
        }
        return css
    }

    override fun render() {
        renderingStrategy.render(this, tileGraphics())
    }
}
