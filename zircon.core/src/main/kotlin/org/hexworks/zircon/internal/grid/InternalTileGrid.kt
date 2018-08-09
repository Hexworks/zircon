package org.hexworks.zircon.internal.grid

import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.TileGraphic
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.animation.InternalAnimationHandler

interface InternalTileGrid
    : TileGrid, Closeable, InternalAnimationHandler {

    var backend: TileGraphic
    var layerable: Layerable
    var animationHandler: InternalAnimationHandler

    fun useContentsOf(tileGrid: InternalTileGrid)

    fun reset()

}
