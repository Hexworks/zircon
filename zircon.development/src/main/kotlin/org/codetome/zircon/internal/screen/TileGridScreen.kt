package org.codetome.zircon.internal.screen

import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.internal.grid.InternalTileGrid
import org.codetome.zircon.internal.grid.RectangleTileGrid

class TileGridScreen<T : Any, S : Any>(
        private val tileGrid: TileGrid<T, S>,
        private val buffer: InternalTileGrid<T, S> = RectangleTileGrid(
                tileset = tileGrid.tileset(),
                size = tileGrid.getBoundableSize()))
    : Screen<T, S>,
        TileGrid<T, S> by buffer {

    init {
        require(tileGrid is InternalTileGrid<T, S>) {
            "The supplied TileGrid is not an instance of InternalTileGrid."
        }
    }

    override fun display() {
        (tileGrid as InternalTileGrid<T, S>).useContentsOf(buffer)
    }

}
