package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Box
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.shape.LineFactory

class DefaultBox internal constructor(
    size: Size,
    style: StyleSet,
    override val boxType: BoxType,
    tileset: TilesetResource,
    private val backend: TileGraphics = tileGraphics {
        this.tileset = tileset
        this.size = size
    }
) : Box, TileGraphics by backend {

    init {
        val verticalChar = characterTile {
            styleSet = style
            character = boxType.vertical
        }
        val horizontalChar = verticalChar
            .withCharacter(boxType.horizontal)

        val horizontalLine = LineFactory.buildLine(
            fromPoint = Position.create(0, 0),
            toPoint = Position.create(size.width - 3, 0)
        )
            .toTileGraphics(
                tile = horizontalChar,
                tileset = backend.tileset
            )
        val verticalLine = LineFactory.buildLine(
            fromPoint = Position.create(0, 0),
            toPoint = Position.create(0, size.height - 3)
        )
            .toTileGraphics(verticalChar, tileset)
        draw(horizontalLine, Position.create(1, 0))
        draw(horizontalLine, Position.create(1, size.height - 1))
        draw(verticalLine, Position.create(0, 1))
        draw(verticalLine, Position.create(size.width - 1, 1))
        draw(verticalChar.withCharacter(boxType.topLeft), Position.create(0, 0))
        draw(verticalChar.withCharacter(boxType.topRight), Position.create(size.width - 1, 0))
        draw(verticalChar.withCharacter(boxType.bottomLeft), Position.create(0, size.height - 1))
        draw(verticalChar.withCharacter(boxType.bottomRight), Position.create(size.width - 1, size.height - 1))
    }

    override fun toString() = backend.toString()
}
