package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics

class DefaultLabelRenderer : ComponentRenderer<Label>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<Label>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
        tileGraphics.putText(context.component.text)
    }
}
