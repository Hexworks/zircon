package org.hexworks.zircon.internal.component.renderer.decoration

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.Color.Companion.TRANSPARENT
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.api.shape.LineFactory

public data class ShadowDecorationRenderer(
    private val shadowChar: Char = DEFAULT_SHADOW_CHAR
) : ComponentDecorationRenderer {

    override val offset: Position = Position(0, 0)

    override val occupiedSize: Size = Size(1, 1)

    private val shadowTile = characterTile {
        character = shadowChar
        withStyleSet {
            backgroundColor = TRANSPARENT
            foregroundColor = Color.create(100, 100, 100)
        }
    }

    override fun render(drawWindow: DrawWindow, context: ComponentDecorationRenderContext) {
        val graphicsSize = drawWindow.size
        drawWindow.draw(
            tileMap = LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(graphicsSize.width - 1, 0)
            ).associateWith { shadowTile },
            drawPosition = Position(1, graphicsSize.height - 1)
        )
        drawWindow.draw(
            tileMap = LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(0, graphicsSize.height - 1)
            ).associateWith { shadowTile },
            drawPosition = Position(graphicsSize.width - 1, 1)
        )
    }

    public companion object {

        public const val DEFAULT_SHADOW_CHAR: Char = Symbols.BLOCK_SPARSE
    }
}
