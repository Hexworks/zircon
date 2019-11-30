package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultRadioButtonGroup

class DefaultRadioButtonGroupRenderer : ComponentRenderer<DefaultRadioButtonGroup> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultRadioButtonGroup>) {
        tileGraphics.fill(Tile.defaultTile())
        tileGraphics.applyStyle(context.currentStyle)
    }
}
