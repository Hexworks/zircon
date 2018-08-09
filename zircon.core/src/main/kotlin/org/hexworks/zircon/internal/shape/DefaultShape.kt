package org.hexworks.zircon.internal.shape

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphic
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.shape.Shape
import org.hexworks.zircon.api.util.Math
import org.hexworks.zircon.internal.graphics.MapTileGraphic

class DefaultShape(private val positions: Set<Position> = setOf())
    : Shape, Collection<Position> by positions {

    override fun positions() = positions

    override fun toTileGraphics(tile: Tile, tileset: TilesetResource): TileGraphic {
        val offsetPositions = offsetToDefaultPosition()
        var maxCol = Int.MIN_VALUE
        var maxRow = Int.MIN_VALUE
        offsetPositions.forEach { (col, row) ->
            maxCol = Math.max(maxCol, col)
            maxRow = Math.max(maxRow, row)
        }
        // TODO: builder here
        val result = MapTileGraphic(size = Size.create(maxCol + 1, maxRow + 1),
                tileset = tileset)
        offsetPositions.forEach {
            result.setTileAt(it, tile)
        }
        return result
    }

    override fun offsetToDefaultPosition(): Shape {
        require(positions.isNotEmpty()) {
            "You can't transform a Shape with zero points!"
        }
        val offset = Position.create(
                x = positions.minBy { it.x }!!.x,
                y = positions.minBy { it.y }!!.y
        )
        return DefaultShape(positions.map { it - offset }
                .toSet())
    }
}
