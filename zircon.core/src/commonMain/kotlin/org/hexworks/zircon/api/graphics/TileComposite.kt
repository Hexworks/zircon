package org.hexworks.zircon.api.graphics

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.HasSize
import org.hexworks.zircon.api.behavior.Sizeable
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

    fun getTileAtOrNull(position: Position): Tile? {
        return if (size.containsPosition(position)) tiles[position] else null
    }

    /**
     * Returns the [Tile] stored at a particular position in this [TileComposite].
     * Returns an empty [Maybe] if [position] is outside of this [TileComposite]'s
     * [size].
     */
    fun getTileAt(position: Position): Maybe<Tile> {
        return if (size.containsPosition(position)) {
            Maybe.of(tiles[position] ?: Tile.empty())
        } else {
            Maybe.empty()
        }
    }
}
