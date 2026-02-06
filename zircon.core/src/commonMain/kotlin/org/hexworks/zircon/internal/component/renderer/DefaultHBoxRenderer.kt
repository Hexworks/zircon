package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.DrawWindow

class DefaultHBoxRenderer : ComponentRenderer<HBox> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<HBox>) {
        drawWindow.fill(Tile.defaultTile())
        drawWindow.applyStyle(context.currentStyle)
    }
}
