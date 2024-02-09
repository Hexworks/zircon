package org.hexworks.zircon.internal.component.renderer

import org.hexworks.cobalt.databinding.api.extension.orElseGet
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultTextArea
import org.hexworks.zircon.internal.event.ZirconEvent

class DefaultTextAreaRenderer : ComponentRenderer<DefaultTextArea> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultTextArea>) {
        val (component, _, style) = context
        drawWindow.applyStyle(style)

        val tileTemplate = characterTile {
            character = ' '
            styleSet = style
        }

        drawWindow.size.fetchPositions().forEach { pos ->
            val fixedPos = pos + component.visibleOffset
            component.getTileAtOrNull(fixedPos)?.let { tile ->
                drawWindow.draw(tileTemplate.withCharacter(tile.character), pos)
            }.orElseGet { drawWindow.draw(tileTemplate, pos) }
        }

        val cursorPos = component.editorState.cursor - component.visibleOffset

        if (component.hasFocus && drawWindow.size.containsPosition(cursorPos)) {
            component.whenConnectedToRoot { root ->
                root.eventBus.publish(
                    event = ZirconEvent.RequestCursorAt(
                        position = component.position + cursorPos,
                        emitter = this
                    ),
                    eventScope = root.eventScope
                )
            }
        } else {
            component.whenConnectedToRoot { root ->
                root.eventBus.publish(
                    event = ZirconEvent.HideCursor(this),
                    eventScope = root.eventScope
                )
            }
        }
    }
}
