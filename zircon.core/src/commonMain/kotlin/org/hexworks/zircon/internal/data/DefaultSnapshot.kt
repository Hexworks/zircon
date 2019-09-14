package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Snapshot
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultSnapshot(override val cells: Iterable<Cell>,
                      override val tileset: TilesetResource) : Snapshot {

    override fun fetchPositions() = cells.map { it.position }

    override fun fetchTiles() = cells.map { it.tile }
}
