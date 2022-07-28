package org.hexworks.zircon.api

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.impl.VirtualApplication
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

object VirtualApplications {

    /**
     * Builds a new [Application] using the given `appConfig` and `eventBus`.
     */
    @JvmStatic
    @JvmOverloads
    @Deprecated("Use startApplication instead", ReplaceWith("startApplication(appConfig, eventBus)"))
    fun buildApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): Application = startApplication(config, eventBus)

    /**
     * Builds a new [Application] using the given `appConfig` and `eventBus`.
     */
    @JvmStatic
    @JvmOverloads
    fun startApplication(
        appConfig: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): Application = VirtualApplication(appConfig, eventBus)

    /**
     * Build a new [Application] and returns its [TileGrid].
     * Use this when you don't want to interact with the [Application] itself.
     */
    @JvmStatic
    @JvmOverloads
    fun startTileGrid(
        appConfig: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): TileGrid = startApplication(appConfig, eventBus).tileGrid
}
