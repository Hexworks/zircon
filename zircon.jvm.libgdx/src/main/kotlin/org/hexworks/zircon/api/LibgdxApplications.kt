package org.hexworks.zircon.api

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.LibgdxApplication
import org.hexworks.zircon.internal.application.LibgdxGame

object LibgdxApplications {

    /**
     * Builds a new [Application] using the given `appConfig`.
     */
    @JvmStatic
    fun buildApplication(appConfig: AppConfig = AppConfigs.defaultConfiguration()): LibgdxApplication {
        return LibgdxApplication(appConfig)
    }

    /**
     * Builds and starts a new [Application] from the given `appConfig`.
     */
    @JvmStatic
    fun startApplication(appConfig: AppConfig = AppConfigs.defaultConfiguration()): LibgdxApplication {
        return startLibgdxGame(appConfig).libgdxApplication
    }

    /**
     * Builds and starts a new [Application] and returns its [TileGrid].
     */
    @JvmStatic
    fun startTileGrid(): TileGrid = startTileGrid(AppConfigs.defaultConfiguration())

    /**
     * Builds and starts a new [Application] and returns its [TileGrid].
     */
    @JvmStatic
    fun startTileGrid(appConfig: AppConfig = AppConfigs.defaultConfiguration()): TileGrid {
        val maxTries = 10
        var currentTryCount = 0
        val game = startLibgdxGame(appConfig)
        var notInitialized = true
        while (notInitialized) {
            try {
                game.libgdxApplication
                notInitialized = false
            } catch (e: Exception) {
                if (currentTryCount >= maxTries) {
                    throw e
                } else {
                    currentTryCount++
                    Thread.sleep(1000)
                }
            }
        }
        return game.libgdxApplication.tileGrid
    }

    private fun startLibgdxGame(appConfig: AppConfig = AppConfigs.defaultConfiguration()): LibgdxGame {
        val config = LwjglApplicationConfiguration()
        config.title = appConfig.title
        config.width = appConfig.size.width * appConfig.defaultTileset.width
        config.height = appConfig.size.height * appConfig.defaultTileset.height
        config.foregroundFPS = appConfig.fpsLimit
        config.useGL30 = false
        val game = LibgdxGame(appConfig)
        LwjglApplication(game, config)
        return game
    }

}
