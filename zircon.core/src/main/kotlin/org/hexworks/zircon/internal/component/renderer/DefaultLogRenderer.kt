package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics

class DefaultLogRenderer : ComponentRenderer<LogArea>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>) {
        val style = context.componentStyle().getCurrentStyle()
        val component = context.component
        tileGraphics.applyStyle(style)
        tileGraphics.size().fetchPositions().forEach { pos ->
            val fixedPos = pos + component.visibleOffset()
            tileGraphics.setTileAt(pos, TileBuilder.newBuilder()
                    .character(component.getLogElements().getCharAt(fixedPos).orElse(' '))
                    .build())
        }
    }
}
