package org.hexworks.zircon.internal.component.renderer

import org.hexworks.cobalt.datatypes.extensions.fold
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultTextArea

class DefaultTextAreaRenderer : ComponentRenderer<DefaultTextArea> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultTextArea>) {
        val style = context.componentStyle.currentStyle()
        val component = context.component
        tileGraphics.applyStyle(style)
        val tileTemplate = Tile.createCharacterTile(' ', style)
        tileGraphics.size.fetchPositions().forEach { pos ->
            val fixedPos = pos + component.visibleOffset
            component.textBuffer().getCharAt(fixedPos).fold(
                    whenEmpty = {
                        tileGraphics.setTileAt(pos, tileTemplate)
                    },
                    whenPresent = { char ->
                        tileGraphics.setTileAt(pos, tileTemplate.withCharacter(char))
                    })
        }
    }
}
