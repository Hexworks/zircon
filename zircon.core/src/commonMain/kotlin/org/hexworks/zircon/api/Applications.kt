package org.hexworks.zircon.api

import korlibs.korge.render.BatchBuilder2D
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.application.NoOpApplication
import org.hexworks.zircon.api.dsl.tileset.buildTilesetFactory
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetLoader
import org.hexworks.zircon.renderer.korge.KorgeApplication
import org.hexworks.zircon.renderer.korge.KorgeRenderer

object Applications {

    /**
     * Creates and starts a new application (see [startApplication])
     * and returns its [TileGrid].
     */
    suspend fun startTileGrid(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): TileGrid = startApplication(config, eventBus).first.tileGrid

    /**
     * Builds a new [Application] using the given parameters. This factory method
     * uses sensible defaults, and it is fine to call it without parameters.
     *
     * **Note that** [startApplication] will overwrite the [Application] that the
     * [TileGrid] is currently using.
     *
     * Also make sure that all the objects you pass use the same [AppConfig].
     */
    suspend fun startApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create(),
        tileGrid: TileGrid = createTileGrid(config),
    ): Pair<Application<Unit>, Unit> {
        val app = KorgeApplication(
            config = config,
            eventBus = eventBus,
            tileGrid = tileGrid.asInternal(),
            renderer = createRenderer(config, tileGrid)
        )
        val fw = app.start()
        return app to fw
    }

    /**
     * Creates a new [Application] using the given parameters. This factory method
     * uses sensible defaults, and it is fine to call it without parameters.
     *
     * 📙 Note that this will not `start` the given application (no continuous renering).
     *
     */
    fun createApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create(),
    ): Application<Unit> {
        val tileGrid = createTileGrid(config)
        return KorgeApplication(
            config = config,
            eventBus = eventBus,
            tileGrid = tileGrid.asInternal(),
            renderer = createRenderer(config, tileGrid)
        )
    }


    /**
     * Creates a new instance of the default [Renderer] implementation using the given parameters.
     *
     * This factory method uses sensible defaults, and it is fine to call it without parameters.
     *
     * Also make sure that all the objects you pass use the same [AppConfig].
     */
    fun createRenderer(
        config: AppConfig = AppConfig.defaultConfiguration(),
        tileGrid: TileGrid = createTileGrid(config),
    ): KorgeRenderer = KorgeRenderer(
        tileGrid = tileGrid.asInternal(),
        // TODO: use tileset loaders from config
        tilesetLoader = DefaultTilesetLoader(
            listOf(
                buildTilesetFactory {
                    targetType = BatchBuilder2D::class
                    supportedTileType = TileType.CHARACTER_TILE
                    supportedTilesetType = TilesetType.CP437Tileset
                    factoryFunction = { resource: TilesetResource ->
                        KorGECP437Tileset(
                            resource = resource,
                        )
                    }
                })
        ),
    )

    /**
     * Creates a new [TileGrid] with a [NoOpApplication] implementation
     * (eg: no continuous rendering). Use this if you embed a [TileGrid] into your
     * own application and you have your own renderer.
     */
    fun createTileGrid(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): TileGrid = DefaultTileGrid(config).apply {
        application = NoOpApplication(
            config = config,
            eventBus = eventBus,
            eventScope = ZirconScope()
        )
    }
}