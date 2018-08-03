package org.codetome.zircon.api.grid

import org.codetome.zircon.api.data.Size

interface GridResizeListener {

    /**
     * The grid has changed its size, most likely because the user has resized the window.
     */
    fun onResized(tileGrid: TileGrid, newSize: Size) {}
}
