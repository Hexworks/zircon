package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics

class DefaultTextBoxRenderer : ComponentRenderer<TextBox>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<TextBox>) {
        val style = context.componentStyle().getCurrentStyle()
        val component = context.component

    }
}
