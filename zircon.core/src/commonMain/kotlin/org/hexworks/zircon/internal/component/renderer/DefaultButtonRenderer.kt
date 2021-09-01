package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.fillWithText
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultButton

class DefaultButtonRenderer : ComponentRenderer<DefaultButton> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultButton>) {
        tileGraphics.fillWithText(context.component.text, context.currentStyle)
    }

}
