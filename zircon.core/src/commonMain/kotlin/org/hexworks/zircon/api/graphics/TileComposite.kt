package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.HasSize
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

/**
 * Represents an object which is composed of [Tile]s and has a [Size].
 */
interface TileComposite : HasSize {

    /**
     * The [Tile]s this [TileComposite] contains.
     */
    val tiles: Map<Position, Tile>

    /**
     * Returns the [Tile] stored at a particular position or `null`
     * if there is no such [Tile].
     */
    fun getTileAtOrNull(position: Position): Tile? {
        return tiles[position]
    }

    /**
     * Returns the [Tile] stored at a particular position or calls [orElse]
     * if there is no such [Tile].
     */
    fun getTileAtOrElse(position: Position, orElse: (Position) -> Tile): Tile {
        return getTileAtOrNull(position) ?: orElse(position)
    }
}
