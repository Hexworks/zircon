package org.hexworks.zircon.internal.grid

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.animation.InternalAnimationRunner
import org.hexworks.zircon.internal.event.ZirconScope
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
     * The [EventBus] Zircon uses for its internal events (component tree changes,
     * screen switches and so on). Shared by every [org.hexworks.zircon.api.screen.Screen]
     * created from this grid.
     */
    val eventBus: EventBus

    /**
     * The scope to publish internal events with on the [eventBus].
     */
    val eventScope: ZirconScope

    /**
     * Starts delegating all actions to the given [tileGrid].
     */
    infix fun delegateTo(tileGrid: InternalTileGrid)

    /**
     * Stops delegating actions to another [TileGrid].
     * Has no effect if no such delegation was present.
     */
    fun reset()

    override fun asInternal() = this

}
