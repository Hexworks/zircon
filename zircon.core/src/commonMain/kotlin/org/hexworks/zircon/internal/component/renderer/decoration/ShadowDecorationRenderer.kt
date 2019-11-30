package org.hexworks.zircon.internal.component.renderer.decoration

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.shape.LineFactory

data class ShadowDecorationRenderer(private val shadowChar: Char = DEFAULT_SHADOW_CHAR) : ComponentDecorationRenderer {

    override val offset = Position.create(0, 0)

    override val occupiedSize = Size.create(1, 1)

    private val shadowTile = TileBuilder.newBuilder()
            .withBackgroundColor(TileColor.transparent())
            .withForegroundColor(TileColor.create(100, 100, 100))
            .withCharacter(shadowChar)
            .build()

    override fun render(tileGraphics: TileGraphics, context: ComponentDecorationRenderContext) {
        val graphicsSize = tileGraphics.size
        tileGraphics.draw(
                tileMap = LineFactory.buildLine(
                        fromPoint = Position.create(0, 0),
                        toPoint = Position.create(graphicsSize.width - 1, 0))
                        .map { it to shadowTile }.toMap(),
                drawPosition = Position.create(1, graphicsSize.height - 1))
        tileGraphics.draw(
                tileMap = LineFactory.buildLine(
                        fromPoint = Position.create(0, 0),
                        toPoint = Position.create(0, graphicsSize.height - 1))
                        .map { it to shadowTile }.toMap(),
                drawPosition = Position.create(graphicsSize.width - 1, 1))
    }

    companion object {
        const val DEFAULT_SHADOW_CHAR = Symbols.BLOCK_SPARSE
    }
}
