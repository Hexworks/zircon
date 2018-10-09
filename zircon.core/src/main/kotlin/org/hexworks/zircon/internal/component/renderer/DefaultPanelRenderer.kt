package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics

class DefaultPanelRenderer : ComponentRenderer<Panel> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<Panel>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
    }
}
