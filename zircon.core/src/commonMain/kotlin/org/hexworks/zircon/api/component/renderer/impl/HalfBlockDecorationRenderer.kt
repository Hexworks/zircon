package org.hexworks.zircon.api.component.renderer.impl

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.modifier.Crop
import org.hexworks.zircon.api.shape.LineFactory

class HalfBlockDecorationRenderer : ComponentDecorationRenderer {

    override val offset = Position.offset1x1()

    override val occupiedSize = Size.create(2, 2)

    override fun render(tileGraphics: TileGraphics, context: ComponentDecorationRenderContext) {
        val size = tileGraphics.size
        val style = context.component.componentStyleSet.currentStyle()
        val topLeft = Position.defaultPosition()
        val topRight = size.fetchTopRightPosition()
        val bottomLeft = size.fetchBottomLeftPosition()
        val bottomRight = size.fetchBottomRightPosition()

        val topTile = Tile.defaultTile()
                .withCharacter(Symbols.LOWER_HALF_BLOCK)
                .withBackgroundColor(TileColor.transparent())
                .withForegroundColor(style.foregroundColor)
                .asCharacterTile().get()

        LineFactory.buildLine(topLeft, topRight).positions
                .drop(1)
                .dropLast(1).forEach {
                    tileGraphics.draw(topTile, it)
                }
        LineFactory.buildLine(topLeft, bottomLeft).positions
                .drop(1)
                .dropLast(1).forEach {
                    tileGraphics.draw(topTile.withCharacter(Symbols.RIGHT_HALF_BLOCK), it)
                }
        LineFactory.buildLine(topRight, bottomRight).positions
                .drop(1)
                .dropLast(1).forEach {
                    tileGraphics.draw(topTile.withCharacter(Symbols.LEFT_HALF_BLOCK), it)
                }
        LineFactory.buildLine(bottomLeft, bottomRight).positions
                .drop(1)
                .dropLast(1).forEach {
                    tileGraphics.draw(topTile.withCharacter(Symbols.UPPER_HALF_BLOCK), it)
                }

        val tileset = tileGraphics.tileset

        val cropRight = Crop(
                x = tileset.width.div(2),
                y = 0,
                width = tileset.width.div(2),
                height = tileset.height)

        val cropLeft = Crop(
                x = 0,
                y = 0,
                width = tileset.width.div(2),
                height = tileset.height)

        val topLeftTile = Tile.defaultTile()
                .withCharacter(Symbols.LOWER_HALF_BLOCK)
                .withModifiers(cropRight)
                .withBackgroundColor(TileColor.transparent())
                .withForegroundColor(style.foregroundColor)
                .asCharacterTile().get()

        val topRightTile = topLeftTile
                .withModifiers(cropLeft)

        val bottomLeftTile = topLeftTile
                .withCharacter(Symbols.UPPER_HALF_BLOCK)

        val bottomRightTile = bottomLeftTile
                .withModifiers(cropLeft)

        tileGraphics.draw(topLeftTile, topLeft)
        tileGraphics.draw(topRightTile, topRight)
        tileGraphics.draw(bottomLeftTile, bottomLeft)
        tileGraphics.draw(bottomRightTile, bottomRight)
    }
}
