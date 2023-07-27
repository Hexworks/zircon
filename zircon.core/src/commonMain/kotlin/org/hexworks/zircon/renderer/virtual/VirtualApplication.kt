package org.hexworks.zircon.renderer.virtual

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.application.BaseApplication
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.renderer.virtual.VirtualRenderer.VirtualView

/**
 * A [VirtualApplication] will not draw anything on a screen, it can be used as a headless implementation
 * of [Application] for testing purposes.
 */
class VirtualApplication(
    config: AppConfig,
    eventBus: EventBus,
    tileGrid: DefaultTileGrid = DefaultTileGrid(config),
) : BaseApplication<Char, VirtualApplication, VirtualView>(config, tileGrid, eventBus) {

    override val renderer = VirtualRenderer(tileGrid)
    override fun prepareRenderSurface(): Char {
        return ' '
    }

    override suspend fun start() {
    }

    init {
        tileGrid.application = this
    }

    companion object {

        fun create(
            config: AppConfig = AppConfig.defaultAppConfig(),
            eventBus: EventBus = EventBus.create()
        ): Application {
            return VirtualApplication(config, eventBus)
        }
    }
}
