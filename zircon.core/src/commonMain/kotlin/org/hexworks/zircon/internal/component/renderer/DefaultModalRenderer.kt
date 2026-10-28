package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.isNotEmpty
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.internal.component.modal.DefaultModal

public class DefaultModalRenderer<T : ModalResult> : ComponentRenderer<DefaultModal<T>> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultModal<T>>) {
        val filler = Tile.defaultTile().withStyle(
            styleSet {
                // No alpha blending in 2.0: the backdrop is either see-through or solid black.
                // Dimming the layers below is a renderer concern (see TODO.md, M4).
                backgroundColor = if (context.component.darkenPercent > 0.0) Color.create(0, 0, 0) else Color.TRANSPARENT
            }
        )
        if (filler.isNotEmpty) {
            drawWindow.fill(filler)
        }
    }
}
