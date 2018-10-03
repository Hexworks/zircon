package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics

class DefaultRadioButtonGroupRenderer : ComponentRenderer<RadioButtonGroup>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<RadioButtonGroup>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
    }
}
