package org.hexworks.zircon.internal.component.impl.wrapping

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.modifier.Crop
import org.hexworks.zircon.api.shape.LineFactory
import org.hexworks.zircon.internal.component.ComponentDecorationRenderer

class HalfBlockComponentDecorationRenderer : ComponentDecorationRenderer {

    override fun getOccupiedSize() = Size.create(2, 2)

    override fun getOffset() = Position.offset1x1()

    override fun render(tileGraphic: TileGraphics, size: Size, offset: Position, style: StyleSet) {
        val topLeft = offset
        val topRight = offset + size.fetchTopRightPosition()
        val bottomLeft = offset + size.fetchBottomLeftPosition()
        val bottomRight = offset + size.fetchBottomRightPosition()

        val topTile = Tile.defaultTile()
                .withCharacter(Symbols.LOWER_HALF_BLOCK)
                .withBackgroundColor(TileColor.transparent())
                .withForegroundColor(style.foregroundColor())
                .asCharacterTile().get()

        LineFactory.buildLine(topLeft, topRight).positions()
                .drop(1)
                .dropLast(1).forEach {
            tileGraphic.setTileAt(it, topTile)
        }
        LineFactory.buildLine(topLeft, bottomLeft).positions()
                .drop(1)
                .dropLast(1).forEach {
            tileGraphic.setTileAt(it, topTile.withCharacter(Symbols.RIGHT_HALF_BLOCK))
        }
        LineFactory.buildLine(topRight, bottomRight).positions()
                .drop(1)
                .dropLast(1).forEach {
            tileGraphic.setTileAt(it, topTile.withCharacter(Symbols.LEFT_HALF_BLOCK))
        }
        LineFactory.buildLine(bottomLeft, bottomRight).positions()
                .drop(1)
                .dropLast(1).forEach {
            tileGraphic.setTileAt(it, topTile.withCharacter(Symbols.UPPER_HALF_BLOCK))
        }

        val tileset = tileGraphic.tileset()

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
                .withForegroundColor(style.foregroundColor())
                .asCharacterTile().get()

        val topRightTile = topLeftTile
                .withModifiers(cropLeft)

        val bottomLeftTile = topLeftTile
                .withCharacter(Symbols.UPPER_HALF_BLOCK)

        val bottomRightTile = bottomLeftTile
                .withModifiers(cropLeft)

        tileGraphic.setTileAt(
                position = topLeft,
                tile = topLeftTile)
        tileGraphic.setTileAt(
                position = topRight,
                tile = topRightTile)
        tileGraphic.setTileAt(
                position = bottomLeft,
                tile = bottomLeftTile)
        tileGraphic.setTileAt(
                position = bottomRight,
                tile = bottomRightTile)

    }
}
