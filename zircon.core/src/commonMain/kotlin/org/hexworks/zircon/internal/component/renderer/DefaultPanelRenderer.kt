package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultPanel

public class DefaultPanelRenderer : ComponentRenderer<DefaultPanel> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultPanel>) {
        drawWindow.fill(Tile.defaultTile())
        drawWindow.applyStyle(context.currentStyle)
    }
}
