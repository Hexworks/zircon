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

/**
 * Draws the modal's backdrop: the screen as it looked when the modal opened, darkened by
 * `darkenPercent` (see `TileGridScreen.openModal`). A modal that was not opened through a
 * screen has no backdrop and falls back to a see-through area, or solid black when it asks
 * for darkening.
 */
public class DefaultModalRenderer<T : ModalResult> : ComponentRenderer<DefaultModal<T>> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultModal<T>>) {
        val backdrop = context.component.backdrop
        if (backdrop != null) {
            drawWindow.draw(backdrop)
            return
        }
        val filler = Tile.defaultTile().withStyle(
            styleSet {
                backgroundColor = if (context.component.darkenPercent > 0.0) Color.create(0, 0, 0) else Color.TRANSPARENT
            }
        )
        if (filler.isNotEmpty) {
            drawWindow.fill(filler)
        }
    }
}
