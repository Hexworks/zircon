package org.hexworks.zircon.internal.component.renderer

import org.hexworks.cobalt.datatypes.extensions.fold
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultVerticalNumberInput

class DefaultVerticalNumberInputRenderer : ComponentRenderer<DefaultVerticalNumberInput> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultVerticalNumberInput>) {
        val style = context.componentStyle.currentStyle()
        val component = context.component
        tileGraphics.applyStyle(style)
        val tileTemplate = Tile.createCharacterTile(' ', style)
        tileGraphics.size.fetchPositions().forEach { pos ->
            val invertedPos = Positions.create(pos.y, pos.x)
            component.textBuffer().getCharAt(invertedPos).fold(
                    whenEmpty = {
                        tileGraphics.setTileAt(pos, tileTemplate)
                    },
                    whenPresent = { char ->
                        tileGraphics.setTileAt(pos, tileTemplate.withCharacter(char))
                    })
        }
    }
}