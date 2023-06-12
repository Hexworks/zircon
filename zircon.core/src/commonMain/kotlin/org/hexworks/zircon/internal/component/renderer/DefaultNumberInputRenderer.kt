package org.hexworks.zircon.internal.component.renderer

import org.hexworks.cobalt.databinding.api.extension.orElseGet
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalNumberInput

class DefaultNumberInputRenderer : ComponentRenderer<DefaultHorizontalNumberInput> {

    override fun render(
        drawWindow: DrawWindow,
        context: ComponentRenderContext<DefaultHorizontalNumberInput>
    ) {
        val component = context.component
        val style = context.currentStyle

        val tileTemplate = characterTile {
            character = ' '
            styleSet = style
        }
        drawWindow.size.fetchPositions().forEach { pos ->
            component.fetchTextBuffer().getCharAtOrNull(pos)?.let { char ->
                drawWindow.draw(tileTemplate.withCharacter(char), pos)
            }.orElseGet { drawWindow.draw(tileTemplate, pos) }
        }
        drawWindow.applyStyle(style)
    }
}
