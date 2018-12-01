package org.hexworks.zircon.internal.input

import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.animation.InternalAnimationHandler

interface InternalTileGrid
    : TileGrid, Closeable, InternalAnimationHandler {

    var backend: TileGraphics
    var layerable: Layerable
    var animationHandler: InternalAnimationHandler

    fun delegateActionsTo(tileGrid: InternalTileGrid)

    fun reset()

}
