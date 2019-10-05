package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultTextArea

class DefaultTextAreaRenderer : ComponentRenderer<DefaultTextArea> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultTextArea>) {
        val style = context.componentStyle.currentStyle()
        val component = context.component
        tileGraphics.applyStyle(style)
        val tileTemplate = Tile.createCharacterTile(' ', style)
        tileGraphics.size.fetchPositions().forEach { pos ->
            val fixedPos = pos + component.visibleOffset
            component.textBuffer().getCharAt(fixedPos).fold(
                    whenEmpty = {
                        tileGraphics.draw(tileTemplate, pos)
                    },
                    whenPresent = { char ->
                        tileGraphics.draw(tileTemplate.withCharacter(char), pos)
                    })
        }
    }
}
