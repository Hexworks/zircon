package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalNumberInput
import org.hexworks.zircon.internal.util.orElse

class DefaultNumberInputRenderer : ComponentRenderer<DefaultHorizontalNumberInput> {

    override fun render(
        tileGraphics: TileGraphics,
        context: ComponentRenderContext<DefaultHorizontalNumberInput>
    ) {
        val component = context.component
        val style = context.currentStyle

        val tileTemplate = Tile.createCharacterTile(' ', style)
        tileGraphics.size.fetchPositions().forEach { pos ->
            component.textBuffer().getCharAtOrNull(pos)?.let { char ->
                tileGraphics.draw(tileTemplate.withCharacter(char), pos)
            }.orElse { tileGraphics.draw(tileTemplate, pos) }
        }
        tileGraphics.applyStyle(style)
    }
}
