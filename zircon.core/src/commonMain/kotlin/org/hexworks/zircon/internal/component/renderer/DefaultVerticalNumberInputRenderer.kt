package org.hexworks.zircon.internal.component.renderer

import org.hexworks.cobalt.databinding.api.extension.orElseGet
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultVerticalNumberInput

class DefaultVerticalNumberInputRenderer : ComponentRenderer<DefaultVerticalNumberInput> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultVerticalNumberInput>) {
        val component = context.component
        tileGraphics.applyStyle(context.currentStyle)
        val tileTemplate = Tile.createCharacterTile(' ', context.currentStyle)
        tileGraphics.size.fetchPositions().forEach { pos ->
            val invertedPos = Position.create(pos.y, pos.x)
            component.textBuffer().getCharAtOrNull(invertedPos)?.let { char ->
                tileGraphics.draw(tileTemplate.withCharacter(char), pos)
            }.orElseGet { tileGraphics.draw(tileTemplate, pos) }
        }
    }
}
