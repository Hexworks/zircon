package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.fillWithText
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultListItem

class DefaultListItemRenderer(private val listItemChar: Char = '-') : ComponentRenderer<DefaultListItem> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultListItem>) {
        tileGraphics.fillWithText("$listItemChar ${context.component.text}", context.currentStyle)
    }
}
