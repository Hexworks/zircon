package org.hexworks.zircon.internal.component.renderer

import org.hexworks.cobalt.databinding.api.extension.orElseGet
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultVerticalNumberInput

class DefaultVerticalNumberInputRenderer : ComponentRenderer<DefaultVerticalNumberInput> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultVerticalNumberInput>) {
        val component = context.component
        drawWindow.applyStyle(context.currentStyle)
        val tileTemplate = characterTile {
            character = ' '
            styleSet = context.currentStyle
        }
        // TODO:
//        drawWindow.size.fetchPositions().forEach { pos ->
//            val invertedPos = Position.create(pos.y, pos.x)
//            component.fetchTextBuffer().getCharAtOrNull(invertedPos)?.let { char ->
//                drawWindow.draw(tileTemplate.withCharacter(char), pos)
//            }.orElseGet { drawWindow.draw(tileTemplate, pos) }
//        }
    }
}
