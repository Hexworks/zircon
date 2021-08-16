package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultHBox

class DefaultHBoxRenderer : ComponentRenderer<HBox> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<HBox>) {
        tileGraphics.fill(Tile.defaultTile())
        tileGraphics.applyStyle(context.currentStyle)
    }
}
