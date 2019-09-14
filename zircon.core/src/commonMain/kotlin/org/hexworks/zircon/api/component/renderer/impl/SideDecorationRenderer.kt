package org.hexworks.zircon.api.component.renderer.impl

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics

data class SideDecorationRenderer(
        private val leftSideCharacter: Char = '[',
        private val rightSideCharacter: Char = ']') : ComponentDecorationRenderer {

    override val offset = Position.create(1, 0)

    override val occupiedSize = Size.create(2, 0)

    override fun render(tileGraphics: SubTileGraphics, context: ComponentDecorationRenderContext) {
        tileGraphics.applyStyle(context.component.componentStyleSet.currentStyle())
        0.until(tileGraphics.height).forEach { idx ->
            tileGraphics.putText("$leftSideCharacter", Positions.create(0, idx))
            tileGraphics.putText("$rightSideCharacter", Positions.create(tileGraphics.size.width - 1, idx))
        }
    }
}
