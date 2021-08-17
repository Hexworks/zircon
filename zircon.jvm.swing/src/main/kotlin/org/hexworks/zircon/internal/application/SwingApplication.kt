package org.hexworks.zircon.internal.application

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.impl.SwingFrame

class SwingApplication private constructor(
    config: AppConfig,
    eventBus: EventBus,
    tileGrid: ThreadSafeTileGrid = ThreadSafeTileGrid(config),
) : BaseApplication(config, tileGrid, eventBus) {

    init {
        System.setProperty("sun.java2d.opengl", "true")
        tileGrid.application = this
    }

    override val renderer = SwingFrame(
        tileGrid = tileGrid,
        config = config,
        app = this
    ).renderer

    companion object {

        /**
         * Creates a new [Application] that uses the Swing backend.
         */
        fun create(
            config: AppConfig = AppConfig.defaultConfiguration(),
            eventBus: EventBus
        ): Application {
            return SwingApplication(config, eventBus)
        }

    }
}
