package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultLabel

class VerticalLabelRenderer : ComponentRenderer<DefaultLabel> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultLabel>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
        val text = context.component.text
        tileGraphics.putText(text)
        (0 until text.length).forEach { idx ->
            tileGraphics.putText("${text[idx]}", Position.create(0, idx))
        }
        (text.length until tileGraphics.height).forEach { idx ->
            tileGraphics.setTileAt(Positions.create(0, idx), Tiles.empty())
        }
    }
}