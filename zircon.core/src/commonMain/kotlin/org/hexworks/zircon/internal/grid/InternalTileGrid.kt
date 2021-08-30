package org.hexworks.zircon.internal.grid

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.animation.InternalAnimationRunner
import org.hexworks.zircon.internal.application.InternalApplication
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

    val config: AppConfig

    /**
     * Holds a reference to the [Application] that uses this [TileGrid].
     * Note that you can write this variable, but it is something you should
     * only do if you know what you're doing.
     */
    var application: InternalApplication

    /**
     * Starts delegating all actions to the given [tileGrid].
     */
    fun delegateTo(tileGrid: InternalTileGrid)

    /**
     * Stops delegating actions to another [TileGrid].
     * Has no effect if no such delegation was present.
     */
    fun reset()

    override fun asInternal() = this

}
