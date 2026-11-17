package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawWindow

public class DefaultVBoxRenderer : ComponentRenderer<VBox> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<VBox>) {
        drawWindow.fill(Tile.defaultTile())
        drawWindow.applyStyle(context.currentStyle)
    }
}
