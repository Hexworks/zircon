package org.codetome.zircon.internal.grid

import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.grid.TileGrid

interface InternalTileGrid<T: Any, S: Any>
    : TileGrid<T, S> {

    var backend: TileImage<T, S>
    var layerable: Layerable

    fun useContentsOf(tileGrid: InternalTileGrid<T, S>)

    fun reset()

}
