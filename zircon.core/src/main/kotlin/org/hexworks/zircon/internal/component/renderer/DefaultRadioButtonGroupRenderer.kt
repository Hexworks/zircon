package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultRadioButtonGroup

class DefaultRadioButtonGroupRenderer : ComponentRenderer<DefaultRadioButtonGroup> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultRadioButtonGroup>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
    }
}
