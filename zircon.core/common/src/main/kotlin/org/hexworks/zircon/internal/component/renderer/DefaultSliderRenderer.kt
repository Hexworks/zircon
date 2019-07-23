package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalSlider

class DefaultSliderRenderer : ComponentRenderer<DefaultHorizontalSlider> {


    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultHorizontalSlider>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
    }
}