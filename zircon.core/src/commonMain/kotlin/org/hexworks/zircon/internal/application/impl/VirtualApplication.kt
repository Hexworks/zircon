package org.hexworks.zircon.internal.application.impl

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.internal.renderer.impl.VirtualRenderer

/**
 * A [VirtualApplication] will not draw anything on a screen, it can be used as a headless implementation
 * of [Application] for testing purposes.
 */
class VirtualApplication(
    config: AppConfig,
    eventBus: EventBus,
    tileGrid: DefaultTileGrid = DefaultTileGrid(config),
) : BaseApplication<VirtualApplication>(config, tileGrid, eventBus) {

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
