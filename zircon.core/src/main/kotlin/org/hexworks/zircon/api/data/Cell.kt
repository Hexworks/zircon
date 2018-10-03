package org.hexworks.zircon.api.data

import org.hexworks.zircon.internal.data.DefaultCell

interface Cell {
    val position: Position
    val tile: Tile

    operator fun component1() = position

    operator fun component2() = tile

    fun withPosition(position: Position): Cell {
        return if (position == this.position) {
            this
        } else {
            create(position, tile)
        }
    }

    fun withTile(tile: Tile): Cell {
        return if (tile == this.tile) {
            this
        } else {
            create(position, tile)
        }
    }

    companion object {

        /**
         * Creates a new [Tile].
         */
        fun create(position: Position, tile: Tile): Cell {
            return DefaultCell(position, tile)
        }
    }
}
