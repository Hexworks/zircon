package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultListItem

class DefaultListItemRenderer(private val listItemChar: Char = '-') : ComponentRenderer<DefaultListItem> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultListItem>) {
        drawWindow.fillWithText("$listItemChar ${context.component.text}", context.currentStyle)
    }
}
