package org.codetome.zircon.api.grid

import org.codetome.zircon.api.data.Size

interface GridResizeListener<T: Any, S: Any> {

    /**
     * The grid has changed its size, most likely because the user has resized the window.
     */
    fun onResized(tileGrid: TileGrid<T, S>, newSize: Size) {}
}
