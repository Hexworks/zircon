package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultContainer

class DefaultContainerRenderer : ComponentRenderer<DefaultContainer>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultContainer>) {
        val style = context.componentStyle().getCurrentStyle()
        tileGraphics.applyStyle(style)
    }
}
