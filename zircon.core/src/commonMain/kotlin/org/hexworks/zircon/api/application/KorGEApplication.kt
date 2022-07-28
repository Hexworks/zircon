package org.hexworks.zircon.api.application

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.internal.application.impl.BaseApplication
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.Renderer

class KorGEApplication(
    config: AppConfig,
    eventBus: EventBus,
    tileGrid: InternalTileGrid,
    override val renderer: Renderer<KorGEApplication>,
) : BaseApplication<KorGEApplication>(config, tileGrid, eventBus) {

    init {
        tileGrid.application = this
        // TODO: close when frame is closed
    }

    companion object : ApplicationFactory<KorGEApplication> {
        override fun createApplication(
            config: AppConfig,
            eventBus: EventBus,
            tileGrid: InternalTileGrid,
            renderer: Renderer<KorGEApplication>
        ): KorGEApplication {
            return KorGEApplication(config, eventBus, tileGrid, renderer)
        }

    }
}
