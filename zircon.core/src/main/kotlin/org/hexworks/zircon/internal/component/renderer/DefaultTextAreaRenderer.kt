package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.api.kotlin.map

class DefaultTextAreaRenderer : ComponentRenderer<TextArea>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<TextArea>) {
        val style = context.componentStyle.currentStyle()
        val component = context.component
        tileGraphics.applyStyle(style)
        val tileTemplate = Tile.createCharacterTile(' ', style)
        tileGraphics.size.fetchPositions().forEach { pos ->
            val fixedPos = pos + component.visibleOffset
            component.textBuffer().getCharAt(fixedPos).map { char ->
                tileGraphics.setTileAt(pos, tileTemplate.withCharacter(char))
            }
        }
    }
}
