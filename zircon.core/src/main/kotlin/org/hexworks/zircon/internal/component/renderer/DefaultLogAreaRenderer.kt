package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics

class DefaultLogAreaRenderer : ComponentRenderer<LogArea>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
    }

}
