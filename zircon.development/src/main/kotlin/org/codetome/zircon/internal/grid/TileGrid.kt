package org.codetome.zircon.internal.grid

import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.grid.TileGrid

interface InternalTileGrid
    : TileGrid {

    var backend: TileImage
    var layerable: Layerable

    fun useContentsOf(tileGrid: InternalTileGrid)

    fun reset()

}
