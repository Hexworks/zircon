package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.HasSize
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

/**
 * Represents an object that is composed of [Tile]s and has a [Size].
 */
public interface TileComposite : HasSize {

    /**
     * Represents the tiles in this [TileComposite] as a [Map].
     */
    public val tiles: Map<Position, Tile>

    /**
     * Returns the [Tile] stored at a particular position or `null`
     * if there is no such [Tile].
     */
    public fun getTileAtOrNull(position: Position): Tile? {
        return tiles[position]
    }

    /**
     * Returns the [Tile] stored at a particular position or calls [orElse]
     * if there is no such [Tile].
     */
    public fun getTileAtOrElse(position: Position, orElse: (Position) -> Tile): Tile {
        return getTileAtOrNull(position) ?: orElse(position)
    }
}
