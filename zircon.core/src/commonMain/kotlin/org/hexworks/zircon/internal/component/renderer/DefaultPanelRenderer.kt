package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultPanel

class DefaultPanelRenderer : ComponentRenderer<DefaultPanel> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultPanel>) {
        tileGraphics.fill(Tile.defaultTile())
        tileGraphics.applyStyle(context.currentStyle)
    }
}
