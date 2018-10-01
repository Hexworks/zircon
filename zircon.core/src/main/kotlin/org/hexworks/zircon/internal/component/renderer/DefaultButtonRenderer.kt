package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics

class DefaultButtonRenderer : ComponentRenderer<Button>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<Button>) {
        val style = context.componentStyle().currentStyle()
        tileGraphics.applyStyle(style)
        tileGraphics.putText(context.component.text())
    }

}
