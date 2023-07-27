package org.hexworks.zircon.internal.component.renderer.decoration

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.api.shape.LineFactory

data class ShadowDecorationRenderer(
    private val shadowChar: Char = DEFAULT_SHADOW_CHAR
) : ComponentDecorationRenderer {

    override val offset = Position.create(0, 0)

    override val occupiedSize = Size.create(1, 1)

    private val shadowTile = characterTile {
        character = shadowChar
        withStyleSet {
            backgroundColor = transparent()
            foregroundColor = TileColor.create(100, 100, 100)
        }
    }

    override fun render(drawWindow: DrawWindow, context: ComponentDecorationRenderContext) {
        val graphicsSize = drawWindow.size
        drawWindow.draw(
            tileMap = LineFactory.buildLine(
                fromPoint = Position.create(0, 0),
                toPoint = Position.create(graphicsSize.width - 1, 0)
            ).associateWith { shadowTile },
            drawPosition = Position.create(1, graphicsSize.height - 1)
        )
        drawWindow.draw(
            tileMap = LineFactory.buildLine(
                fromPoint = Position.create(0, 0),
                toPoint = Position.create(0, graphicsSize.height - 1)
            ).associateWith { shadowTile },
            drawPosition = Position.create(graphicsSize.width - 1, 1)
        )
    }

    companion object {

        const val DEFAULT_SHADOW_CHAR = Symbols.BLOCK_SPARSE
    }
}
