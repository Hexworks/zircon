package org.codetome.zircon.internal.shape

import org.codetome.zircon.api.builder.graphics.TileImageBuilder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.shape.Shape
import org.codetome.zircon.api.util.Math

class DefaultShape(private val positions: Set<Position> = setOf())
    : Shape, Collection<Position> by positions {

    override fun getPositions() = positions

    override fun toTextImage(tile: Tile): TileImage {
        val offsetPositions = offsetToDefaultPosition()
        var maxCol = Int.MIN_VALUE
        var maxRow = Int.MIN_VALUE
        offsetPositions.forEach { (col, row) ->
            maxCol = Math.max(maxCol, col)
            maxRow = Math.max(maxRow, row)
        }
        val result = TileImageBuilder.newBuilder()
                .size(Size.create(maxCol + 1, maxRow + 1))
                .build()
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
