package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultLogArea

class DefaultLogAreaRenderer : ComponentRenderer<DefaultLogArea> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultLogArea>) {
        tileGraphics.fill(Tiles.defaultTile())
        tileGraphics.applyStyle(context.currentStyle)
    }

}
