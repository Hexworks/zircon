package org.hexworks.zircon.api

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.SwingApplication

object SwingApplications {

    /**
     * Builds a new [Application] using the given `appConfig`.
     */
    @JvmStatic
    @JvmOverloads
    fun buildApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): Application {
        return SwingApplication.create(config, eventBus)
    }

    /**
     * Builds and starts a new [Application] from the given `appConfig`.
     */
    @JvmStatic
    @JvmOverloads
    fun startApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): Application = buildApplication(config, eventBus).also {
        it.start()
    }


    /**
     * Builds and starts a new [Application] and returns its [TileGrid].
     */
    @JvmStatic
    @JvmOverloads
    fun startTileGrid(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): TileGrid = startApplication(config, eventBus).tileGrid


}
