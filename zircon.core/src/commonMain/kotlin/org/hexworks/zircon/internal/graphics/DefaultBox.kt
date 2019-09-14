package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Box
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.shape.LineFactory

class DefaultBox(
        size: Size,
        styleSet: StyleSet,
        boxType: BoxType,
        tileset: TilesetResource,
        private val backend: TileGraphics = TileGraphicsBuilder.newBuilder()
                .withTileset(tileset)
                .withSize(size)
                .build())
    : Box, TileGraphics by backend {

    init {
        setStyleFrom(styleSet)
        val verticalChar = TileBuilder.newBuilder()
                .withStyleSet(styleSet)
                .withCharacter(boxType.vertical)
                .buildCharacterTile()
        val horizontalChar = verticalChar
                .withCharacter(boxType.horizontal)

        val horizontalLine = LineFactory.buildLine(
                fromPoint = Position.create(0, 0),
                toPoint = Position.create(size.width - 3, 0))
                .toTileGraphics(
                        tile = horizontalChar,
                        tileset = backend.currentTileset())
        val verticalLine = LineFactory.buildLine(
                fromPoint = Position.create(0, 0),
                toPoint = Position.create(0, size.height - 3))
                .toTileGraphics(verticalChar, tileset)
        draw(horizontalLine, Position.create(1, 0))
        draw(horizontalLine, Position.create(1, size.height - 1))
        draw(verticalLine, Position.create(0, 1))
        draw(verticalLine, Position.create(size.width - 1, 1))
        setTileAt(Position.create(0, 0),
                verticalChar.withCharacter(boxType.topLeft))
        setTileAt(Position.create(size.width - 1, 0),
                verticalChar.withCharacter(boxType.topRight))
        setTileAt(Position.create(0, size.height - 1),
                verticalChar.withCharacter(boxType.bottomLeft))
        setTileAt(Position.create(size.width - 1, size.height - 1),
                verticalChar.withCharacter(boxType.bottomRight))
    }

    override fun toString() = backend.toString()
}
