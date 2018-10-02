package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.DefaultSnapshot

interface Snapshot {

    val cells: Iterable<Cell>
    val tileset: TilesetResource

    fun fetchPositions() = cells.map { it.position }
    fun fetchTiles() = cells.map { it.tile }

    operator fun component1() = cells
    operator fun component2() = tileset

    companion object {

        fun create(cells: Iterable<Cell>, tileset: TilesetResource): Snapshot {
            return DefaultSnapshot(cells, tileset)
        }
    }
}
