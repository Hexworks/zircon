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

    /**
     * Starts delegating all actions to the given [tileGrid].
     */
    fun delegateTo(tileGrid: InternalTileGrid)

    /**
     * Stops delegating actions to an other [TileGrid].
     * Has no effect if no such delegation was present.
     */
    fun reset()

}
