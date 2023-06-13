package org.hexworks.zircon.internal.component.renderer

import org.hexworks.cobalt.databinding.api.extension.orElseGet
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalNumberInput

class DefaultNumberInputRenderer : ComponentRenderer<DefaultHorizontalNumberInput> {

    override fun render(
        drawWindow: DrawWindow,
        context: ComponentRenderContext<DefaultHorizontalNumberInput>
    ) {
        val component = context.component
        val style = context.currentStyle

        val tileTemplate = Tile.createCharacterTile(' ', style)
        drawWindow.size.fetchPositions().forEach { pos ->
            component.textBuffer().getCharAtOrNull(pos)?.let { char ->
                drawWindow.draw(tileTemplate.withCharacter(char), pos)
            }.orElseGet { drawWindow.draw(tileTemplate, pos) }
        }
        drawWindow.applyStyle(style)
    }
}
