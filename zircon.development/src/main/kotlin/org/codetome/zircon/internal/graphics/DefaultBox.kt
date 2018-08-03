package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.builder.graphics.TileImageBuilder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.Box
import org.codetome.zircon.api.graphics.BoxType
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.shape.LineFactory
import org.codetome.zircon.api.tileset.Tileset

class DefaultBox(
        size: Size,
        styleSet: StyleSet,
        boxType: BoxType,
        tileset: TilesetResource<out Tile>,
        private val backend: TileImage = TileImageBuilder.newBuilder(tileset)
                .size(size)
                .build())
    : Box, TileImage by backend {

    init {
        setStyleFrom(styleSet)
        val verticalChar = TileBuilder.newBuilder()
                .styleSet(styleSet)
                .character(boxType.vertical)
                .buildCharacterTile()
        val horizontalChar = verticalChar
                .withCharacter(boxType.horizontal)

        val horizontalLine = LineFactory.buildLine(
                fromPoint = Position.create(0, 0),
                toPoint = Position.create(size.xLength - 3, 0))
                .toTileImage(
                        tile = horizontalChar,
                        tileset = backend.tileset())
        val verticalLine = LineFactory.buildLine(
                fromPoint = Position.create(0, 0),
                toPoint = Position.create(0, size.yLength - 3))
                .toTileImage(verticalChar, tileset)
        draw(horizontalLine, Position.create(1, 0))
        draw(horizontalLine, Position.create(1, size.yLength - 1))
        draw(verticalLine, Position.create(0, 1))
        draw(verticalLine, Position.create(size.xLength - 1, 1))
        setTileAt(Position.create(0, 0),
                verticalChar.withCharacter(boxType.topLeft))
        setTileAt(Position.create(size.xLength - 1, 0),
                verticalChar.withCharacter(boxType.topRight))
        setTileAt(Position.create(0, size.yLength - 1),
                verticalChar.withCharacter(boxType.bottomLeft))
        setTileAt(Position.create(size.xLength - 1, size.yLength - 1),
                verticalChar.withCharacter(boxType.bottomRight))
    }

    override fun toString() = backend.toString()
}
