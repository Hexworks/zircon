package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.isNotEmpty
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.modal.DefaultModal

class DefaultModalRenderer<T : ModalResult> : ComponentRenderer<DefaultModal<T>> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultModal<T>>) {
        val filler = Tile.defaultTile().withStyle(
            styleSet {
                backgroundColor = TileColor.create(
                    0, 0, 0,
                    alpha = 255.0.times(context.component.darkenPercent).toInt()
                )
            }
        )
        if (filler.isNotEmpty) {
            drawWindow.fill(filler)
        }
    }
}
