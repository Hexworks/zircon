package org.hexworks.zircon.internal.component.renderer

import org.hexworks.cobalt.databinding.api.extension.orElseGet
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultTextArea

class DefaultTextAreaRenderer : ComponentRenderer<DefaultTextArea> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultTextArea>) {
        val style = context.currentStyle
        val component = context.component
        drawWindow.applyStyle(style)
        val tileTemplate = Tile.createCharacterTile(' ', style)
        drawWindow.size.fetchPositions().forEach { pos ->
            val fixedPos = pos + component.visibleOffset
            component.textBuffer().getCharAtOrNull(fixedPos)?.let { char ->
                drawWindow.draw(tileTemplate.withCharacter(char), pos)
            }.orElseGet { drawWindow.draw(tileTemplate, pos) }
        }
    }
}
