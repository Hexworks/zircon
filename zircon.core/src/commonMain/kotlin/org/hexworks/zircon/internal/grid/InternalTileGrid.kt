package org.hexworks.zircon.internal.grid

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.animation.InternalAnimationRunner
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.uievent.UIEventProcessor

interface InternalTileGrid : TileGrid, InternalAnimationRunner, InternalLayerable, UIEventProcessor {

    /**
     * The base layer of this [InternalTileGrid] (at index `0`).
     */
    var backend: Layer

    /**
     * The [Layerable] this [InternalTileGrid] currently uses.
     */
    var layerable: InternalLayerable

    /**
     * The [InternalAnimationRunner] this [InternalTileGrid] currently uses.
     */
    var animationHandler: InternalAnimationRunner

    /**
     * The [InternalCursorHandler] this [InternalTileGrid] currently uses.
     */
    var cursorHandler: InternalCursorHandler

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
