package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultNumberInput

class DefaultNumberInputRenderer : ComponentRenderer<DefaultNumberInput> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultNumberInput>) {
        val style = context.componentStyle.currentStyle()
        val text = context.component.text
        tileGraphics.applyStyle(style)

        (0 until text.length).forEach { idx ->
            tileGraphics.putText("${text[idx]}", Position.create(idx, 0))
        }
        (text.length until tileGraphics.width).forEach { idx ->
            tileGraphics.setTileAt(Positions.create(idx,0), Tiles.empty())
        }
    }
}