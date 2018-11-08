package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile

/**
 * Base class for [Cell] implementations.
 */
abstract class BaseCell : Cell {

    override fun withPosition(position: Position): Cell {
        return if (position == this.position) {
            this
        } else {
            Cell.create(position, tile)
        }
    }

    override fun withTile(tile: Tile): Cell {
        return if (tile == this.tile) {
            this
        } else {
            Cell.create(position, tile)
        }
    }
}
