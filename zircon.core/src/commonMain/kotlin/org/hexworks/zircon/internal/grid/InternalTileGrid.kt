package org.hexworks.zircon.internal.grid

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.animation.InternalAnimationHandler
import org.hexworks.zircon.internal.uievent.UIEventProcessor

interface InternalTileGrid
    : TileGrid, InternalAnimationHandler, UIEventProcessor {

    var backend: TileGraphics
    var layerable: Layerable
    var animationHandler: InternalAnimationHandler

    fun delegateActionsTo(tileGrid: InternalTileGrid)

    fun reset()

}
