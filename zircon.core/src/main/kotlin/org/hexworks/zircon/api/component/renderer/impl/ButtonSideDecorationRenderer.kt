package org.hexworks.zircon.api.component.renderer.impl

import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.SubTileGraphics

class ButtonSideDecorationRenderer : ComponentDecorationRenderer {

    override fun offset(): Position = Position.create(1, 0)

    override fun occupiedSize(): Size = Size.create(2, 0)

    override fun render(tileGraphics: SubTileGraphics, context: ComponentDecorationRenderContext) {
        tileGraphics.applyStyle(context.component.componentStyleSet().currentStyle())
        tileGraphics.putText("[", Position.defaultPosition())
        tileGraphics.putText("]", Position.create(tileGraphics.width - 1, 0))
    }
}
