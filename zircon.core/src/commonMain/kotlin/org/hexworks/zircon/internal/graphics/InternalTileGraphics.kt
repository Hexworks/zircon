package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.data.TileGraphicsState

interface InternalTileGraphics : TileGraphics {
    /**
     * Holds a snapshot of the current state of this [InternalTileGraphics].
     */
    val state: TileGraphicsState
}
