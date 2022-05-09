package org.hexworks.zircon.api.application

import org.hexworks.cobalt.core.api.behavior.DisposeState
import org.hexworks.cobalt.core.api.behavior.DisposedByHand
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.event.ZirconScope

/**
 * Use this class when you don't want to have an application implementation, and
 * you're using just a Renderer
 */
class NoOpApplication(
    override val config: AppConfig,
    override val eventBus: EventBus,
    override val eventScope: ZirconScope
) : InternalApplication {

    override lateinit var tileGrid: TileGrid

    override fun start() {}

    override fun pause() {}

    override fun resume() {}

    override fun stop() {}

    override fun beforeRender(listener: (RenderData) -> Unit) = NoOpSubscription

    override fun afterRender(listener: (RenderData) -> Unit) = NoOpSubscription

    override fun asInternal() = this

    object NoOpSubscription : Subscription {
        override val disposeState: DisposeState = DisposedByHand

        override fun dispose(disposeState: DisposeState) {}

    }
}
