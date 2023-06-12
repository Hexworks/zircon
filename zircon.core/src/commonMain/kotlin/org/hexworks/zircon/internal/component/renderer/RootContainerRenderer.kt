package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.RootContainer

class RootContainerRenderer : ComponentRenderer<RootContainer> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<RootContainer>) {
        // TODO: make this tile configurable in AppConfig
        drawWindow.fill(Tile.defaultTile())
        drawWindow.applyStyle(context.currentStyle)
    }
}
