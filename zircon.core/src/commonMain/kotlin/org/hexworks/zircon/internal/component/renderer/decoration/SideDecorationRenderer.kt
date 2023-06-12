package org.hexworks.zircon.internal.component.renderer.decoration

import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toCharacterTile
import org.hexworks.zircon.api.graphics.impl.DrawWindow

data class SideDecorationRenderer(
    private val leftSideCharacter: Char = '[',
    private val rightSideCharacter: Char = ']',
    private val renderingMode: RenderingMode
) : ComponentDecorationRenderer {

    override val offset = Position.create(1, 0)

    override val occupiedSize = Size.create(2, 0)

    override fun render(drawWindow: DrawWindow, context: ComponentDecorationRenderContext) {
        0.until(drawWindow.height).forEach { idx ->
            drawWindow.draw(leftSideCharacter.toCharacterTile(), Position.create(0, idx))
            drawWindow.draw(rightSideCharacter.toCharacterTile(), Position.create(drawWindow.size.width - 1, idx))
        }
        drawWindow.applyStyle(context.fetchStyleFor(renderingMode))
    }
}
