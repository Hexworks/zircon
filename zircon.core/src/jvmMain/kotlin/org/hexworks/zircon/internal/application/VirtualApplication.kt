package org.hexworks.zircon.internal.application

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.renderer.VirtualRenderer

class VirtualApplication(
    config: AppConfig,
    eventBus: EventBus,
    tileGrid: ThreadSafeTileGrid = ThreadSafeTileGrid(config),
) : BaseApplication(config, tileGrid, eventBus) {

    override val renderer = VirtualRenderer(tileGrid)

    init {
        tileGrid.application = this
    }

    companion object {

        fun create(
            config: AppConfig = AppConfig.defaultConfiguration(),
            eventBus: EventBus = EventBus.create()
        ): Application {
            return VirtualApplication(config, eventBus)
        }

    }
}
