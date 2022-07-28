package org.hexworks.zircon.api.application

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.Renderer

interface ApplicationFactory<A : Application> {

    fun createApplication(
        config: AppConfig,
        eventBus: EventBus,
        tileGrid: InternalTileGrid,
        renderer: Renderer<A>,
    ): A
}