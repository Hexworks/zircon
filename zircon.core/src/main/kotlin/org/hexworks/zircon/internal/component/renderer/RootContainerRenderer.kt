package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.RootContainer

class RootContainerRenderer : ComponentRenderer<RootContainer>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<RootContainer>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
    }
}
