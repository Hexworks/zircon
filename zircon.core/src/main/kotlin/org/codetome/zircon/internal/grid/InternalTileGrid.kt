package org.codetome.zircon.internal.grid

import org.codetome.zircon.api.data.Cell
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.behavior.FontOverrideSupport
import org.codetome.zircon.internal.behavior.InternalLayerable

/**
 * This is the internal API of a [TileGrid]
 */
interface InternalTileGrid : TileGrid, InternalLayerable, FontOverrideSupport {

    fun forEachDirtyCell(fn: (Cell) -> Unit)

    fun forEachCell(fn: (Cell) -> Unit)
}
