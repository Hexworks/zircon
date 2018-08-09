package org.hexworks.zircon.api

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.LibgdxApplication

object LibgdxApplications {

    /**
     * Builds a new [Application] using the given `appConfig`.
     */
    @JvmStatic
    fun buildApplication(appConfig: AppConfig): Application {
        return LibgdxApplication(appConfig)
    }

    /**
     * Builds and starts a new [Application] from the given `appConfig`.
     */
    @JvmStatic
    fun startApplication(appConfig: AppConfig): Application {
        return LibgdxApplication(appConfig).also {
            it.start()
        }
    }

    /**
     * Builds and starts a new [Application] and returns its [TileGrid].
     */
    @JvmStatic
    fun startTileGrid(appConfig: AppConfig): TileGrid {
        return LibgdxApplication(appConfig).also {
            it.start()
        }.tileGrid
    }


}
