package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.RootContainer

class RootContainerRenderer : ComponentRenderer<RootContainer> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<RootContainer>) {
        tileGraphics.fill(Tiles.defaultTile())
        tileGraphics.applyStyle(context.currentStyle)
    }
}
