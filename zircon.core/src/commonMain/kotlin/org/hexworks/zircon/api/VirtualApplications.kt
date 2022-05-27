package org.hexworks.zircon.api

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.impl.VirtualApplication
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmOverloads

object VirtualApplications {

    /**
     * Builds a new [Application] using the given `appConfig`.
     */
    @JvmStatic
    @JvmOverloads
    fun buildApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): Application = VirtualApplication(config, eventBus)

    /**
     * Builds and starts a new [Application] from the given `appConfig`.
     */
    @JvmStatic
    @JvmOverloads
    fun startApplication(
        appConfig: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): Application = buildApplication(appConfig, eventBus).apply { start() }

    /**
     * Builds and starts a new [Application] and returns its [TileGrid].
     */
    @JvmStatic
    @JvmOverloads
    fun startTileGrid(
        appConfig: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): TileGrid = startApplication(appConfig, eventBus).tileGrid
}
