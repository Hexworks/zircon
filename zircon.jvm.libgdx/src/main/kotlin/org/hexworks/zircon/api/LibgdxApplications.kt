package org.hexworks.zircon.api

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.dsl.tileset.buildTilesetFactory
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.application.LibgdxApplication
import org.hexworks.zircon.internal.application.LibgdxGame
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TileType.*
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.resource.TilesetType.*
import org.hexworks.zircon.internal.tileset.LibgdxCP437Tileset
import org.hexworks.zircon.internal.tileset.LibgdxGraphicalTileset
import org.hexworks.zircon.internal.tileset.LibgdxMonospaceFontTileset
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetLoader
import java.awt.Graphics2D

object LibgdxApplications {

    /**
     * Builds a new [Application] using the given `appConfig`.
     */
    @JvmStatic
    @JvmOverloads
    fun buildApplication(
        appConfig: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create(),
        libgdxConfig: LwjglApplicationConfiguration = LwjglApplicationConfiguration()
    ): LibgdxApplication {
        return makeLibgdxGame(appConfig, eventBus, libgdxConfig).libgdxApplication
    }

    /**
     * Builds and starts a new [Application] from the given `appConfig`.
     */
    @JvmStatic
    @JvmOverloads
    fun startApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create(),
        libgdxConfig: LwjglApplicationConfiguration = LwjglApplicationConfiguration()
    ): LibgdxApplication {
        with(makeLibgdxGame(config, eventBus, libgdxConfig)) {
            start()
            return this.libgdxApplication
        }
    }

    /**
     * Builds JUST a new [Application], not a libgdx game
     */
    @JvmStatic
    @JvmOverloads
    fun buildRawApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): LibgdxApplication {
        return LibgdxApplication(config, eventBus)
    }

    /**
     * Starts JUST a new [Application], not a libgdx game
     */
    @JvmStatic
    @JvmOverloads
    fun startRawApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): LibgdxApplication {
        return LibgdxApplication(config, eventBus).also {
            it.start()
        }
    }

    /**
     * Builds and starts a new [Application] and returns its [TileGrid].
     */
    @JvmStatic
    @JvmOverloads
    fun startTileGrid(
        appConfig: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create(),
        libgdxConfig: LwjglApplicationConfiguration = LwjglApplicationConfiguration()
    ): TileGrid {
        val maxTries = 10
        var currentTryCount = 0
        val game = makeLibgdxGame(appConfig, eventBus, libgdxConfig)
        game.start()
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

    /**
     * Creates a [LibgdxGame] that contains a built and started [Application]
     *
     * Note that the Zircon `appConfig` fields title, size, and fpsLimit
     * will override the Libgdx `LwjglApplicationConfiguration` fields
     * title, width and height, and foregroundFPS respectively.
     */
    @JvmStatic
    private fun makeLibgdxGame(
        appConfig: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus,
        libgdxConfig: LwjglApplicationConfiguration = LwjglApplicationConfiguration()
    ): LibgdxGame {
        libgdxConfig.title = appConfig.title
        libgdxConfig.width = appConfig.size.width * appConfig.defaultTileset.width
        libgdxConfig.height = appConfig.size.height * appConfig.defaultTileset.height
        libgdxConfig.foregroundFPS = appConfig.fpsLimit
        libgdxConfig.useGL30 = false
        return LibgdxGame.build(appConfig, eventBus, libgdxConfig)
    }

    @JvmStatic
    fun defaultTilesetLoader(): TilesetLoader<SpriteBatch> = DefaultTilesetLoader(DEFAULT_FACTORIES)

    internal val DEFAULT_FACTORIES: Map<Pair<TileType, TilesetType>, TilesetFactory<SpriteBatch>> =
        listOf<TilesetFactory<SpriteBatch>>(
            buildTilesetFactory {
                targetType = SpriteBatch::class
                supportedTileType = CHARACTER_TILE
                supportedTilesetType = CP437Tileset
                factoryFunction = { resource: TilesetResource ->
                    LibgdxCP437Tileset(
                        resource = resource,
                        path = resource.path,
                    )
                }
            },
            buildTilesetFactory {
                targetType = SpriteBatch::class
                supportedTileType = CHARACTER_TILE
                supportedTilesetType = TrueTypeFont
                factoryFunction = { resource: TilesetResource ->
                    LibgdxGraphicalTileset(
                        resource = resource
                    )
                }
            },
            buildTilesetFactory {
                targetType = SpriteBatch::class
                supportedTileType = GRAPHICAL_TILE
                supportedTilesetType = GraphicalTileset
                factoryFunction = { resource: TilesetResource ->
                    LibgdxMonospaceFontTileset(
                        resource = resource
                    )
                }
            }
        ).associateBy { it.supportedTileType to it.supportedTilesetType }
}
