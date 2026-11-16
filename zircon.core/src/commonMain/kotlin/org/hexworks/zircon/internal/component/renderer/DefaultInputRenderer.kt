package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.internal.component.extensions.whenConnectedToRoot
import org.hexworks.zircon.internal.component.impl.DefaultInput
import org.hexworks.zircon.internal.event.ZirconEvent.HideCursor
import org.hexworks.zircon.internal.event.ZirconEvent.RequestCursorAt

/**
 * The visible slice of the text, and the grid cursor at the caret while the field has focus.
 */
public class DefaultInputRenderer : ComponentRenderer<DefaultInput> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultInput>) {
        val component = context.component
        val style = context.currentStyle
        val visible = component.text.drop(component.scrollOffset).take(drawWindow.size.width)
        drawWindow.fill(characterTile { character = ' '; styleSet = style })
        visible.forEachIndexed { x, char ->
            drawWindow.draw(characterTile { character = char; styleSet = style }, Position(x, 0))
        }
        val caret = component.cursorIndex - component.scrollOffset
        component.whenConnectedToRoot { root ->
            val event = if (component.hasFocus && caret in 0 until drawWindow.size.width) {
                RequestCursorAt(component.absolutePosition + component.contentOffset + Position(caret, 0), component)
            } else HideCursor(component)
            root.eventBus.publish(event, root.eventScope)
        }
    }
}
