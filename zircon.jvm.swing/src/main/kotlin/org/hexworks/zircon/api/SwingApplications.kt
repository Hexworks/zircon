package org.hexworks.zircon.api

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.SwingApplication

object SwingApplications {

    /**
     * Builds a new [Application] using the given `appConfig`.
     */
    @JvmStatic
    @JvmOverloads
    fun buildApplication(appConfig: AppConfig = AppConfig.defaultConfiguration()): Application {
        return SwingApplication(appConfig)
    }

    /**
     * Builds and starts a new [Application] from the given `appConfig`.
     */
    @JvmStatic
    @JvmOverloads
    fun startApplication(appConfig: AppConfig = AppConfig.defaultConfiguration()): Application {
        return SwingApplication(appConfig).also {
            it.start()
        }
    }

    /**
     * Builds and starts a new [Application] and returns its [TileGrid].
     */
    @JvmStatic
    @JvmOverloads
    fun startTileGrid(appConfig: AppConfig = AppConfig.defaultConfiguration()): TileGrid {
        return SwingApplication(appConfig).also {
            it.start()
        }.tileGrid
    }


}
