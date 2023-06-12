package org.hexworks.zircon.api

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.renderer.virtual.VirtualApplication

object VirtualApplications {

    /**
     * Builds a new [Application] using the given `appConfig` and `eventBus`.
     */
    fun startApplication(
        appConfig: AppConfig = AppConfig.defaultAppConfig(),
        eventBus: EventBus = EventBus.create()
    ): Application = VirtualApplication(appConfig, eventBus)

    /**
     * Build a new [Application] and returns its [TileGrid].
     * Use this when you don't want to interact with the [Application] itself.
     */
    fun startTileGrid(
        appConfig: AppConfig = AppConfig.defaultAppConfig(),
        eventBus: EventBus = EventBus.create()
    ): TileGrid = startApplication(appConfig, eventBus).tileGrid
}
