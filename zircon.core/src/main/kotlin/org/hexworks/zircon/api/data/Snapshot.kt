package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.DefaultSnapshot

/**
 * Represents the contents of a [org.hexworks.zircon.api.behavior.DrawSurface]
 * at a given moment in time.
 */
interface Snapshot {

    val cells: Iterable<Cell>
    val tileset: TilesetResource

    /**
     * Returns the [Position]s of this [Snapshot].
     */
    fun fetchPositions(): Iterable<Position>

    /**
     * Returns the [Tile]s of this [Snapshot].
     */
    fun fetchTiles(): Iterable<Tile>

    operator fun component1() = cells

    operator fun component2() = tileset

    companion object {

        /**
         * Creates a new [Snapshot].
         */
        fun create(cells: Iterable<Cell>, tileset: TilesetResource): Snapshot {
            return DefaultSnapshot(cells, tileset)
        }
    }
}
