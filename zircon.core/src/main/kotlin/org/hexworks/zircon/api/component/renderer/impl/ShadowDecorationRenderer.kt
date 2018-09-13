package org.hexworks.zircon.api.component.renderer.impl

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.shape.LineFactory

class ShadowDecorationRenderer(shadowChar: Char = DEFAULT_SHADOW_CHAR) : ComponentDecorationRenderer {

    override val offset: Position = Position.create(0, 0)

    override val occupiedSize: Size = Size.create(1, 1)

    private val shadowTile = TileBuilder.newBuilder()
            .backgroundColor(TileColor.transparent())
            .foregroundColor(TileColor.create(100, 100, 100))
            .character(shadowChar)
            .build()

    override fun render(tileGraphics: SubTileGraphics, context: ComponentDecorationRenderContext) {
        val graphicsSize = tileGraphics.size()
        LineFactory.buildLine(
                fromPoint = Position.create(1, 0),
                toPoint = Position.create(graphicsSize.xLength - 1, 0))
                .toTileGraphics(shadowTile, tileGraphics.tileset())
                .drawOnto(tileGraphics, Position.create(1, graphicsSize.yLength - 1))
        LineFactory.buildLine(
                fromPoint = Position.create(0, 1),
                toPoint = Position.create(0, graphicsSize.yLength - 1))
                .toTileGraphics(shadowTile, tileGraphics.tileset())
                .drawOnto(tileGraphics, Position.create(graphicsSize.xLength - 1, 1))
    }

    companion object {
        const val DEFAULT_SHADOW_CHAR = Symbols.BLOCK_SPARSE
    }
}
