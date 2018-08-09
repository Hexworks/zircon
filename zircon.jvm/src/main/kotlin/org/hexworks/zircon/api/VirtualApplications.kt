package org.hexworks.zircon.api

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.VirtualApplication

object VirtualApplications {

    /**
     * Builds a new [Application] using the given `appConfig`.
     */
    @JvmStatic
    fun buildApplication(appConfig: AppConfig): Application {
        return VirtualApplication(appConfig)
    }

    /**
     * Builds and starts a new [Application] from the given `appConfig`.
     */
    @JvmStatic
    fun startApplication(appConfig: AppConfig): Application {
        return VirtualApplication(appConfig).also {
            it.start()
        }
    }

    /**
     * Builds and starts a new [Application] and returns its [TileGrid].
     */
    @JvmStatic
    fun startTileGrid(appConfig: AppConfig): TileGrid {
        return VirtualApplication(appConfig).also {
            it.start()
        }.tileGrid
    }
}
