package org.hexworks.zircon.api

import korlibs.datastructure.fastCastTo
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.AppConfig.Companion.defaultAppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.application.NoOpApplication
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetLoader
import org.hexworks.zircon.renderer.korge.KorgeApplication
import org.hexworks.zircon.renderer.korge.KorgeRenderer
import org.hexworks.zircon.renderer.korge.tileset.KorgeContext

/**
 * Creates and starts a new application (see [startApplication])
 * and returns its [TileGrid].
 */
suspend fun startTileGrid(
    config: AppConfig = defaultAppConfig(),
    eventBus: EventBus = EventBus.create()
): TileGrid = startApplication(config, eventBus).tileGrid

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
    config: AppConfig = defaultAppConfig(),
    eventBus: EventBus = EventBus.create(),
    tileGrid: TileGrid = createTileGrid(config),
): Application {
    val app = KorgeApplication(
        config = config,
        eventBus = eventBus,
        tileGrid = tileGrid.asInternal(),
        renderer = createRenderer(config, tileGrid)
    )
    app.start()
    return app
}

/**
 * Creates a new [Application] using the given parameters. This factory method
 * uses sensible defaults, and it is fine to call it without parameters.
 *
 * 📙 Note that this will not `start` the given application (no continuous renering).
 *
 */
fun createApplication(
    config: AppConfig = defaultAppConfig(),
    eventBus: EventBus = EventBus.create(),
): Application {
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
    config: AppConfig = defaultAppConfig(),
    tileGrid: TileGrid = createTileGrid(config),
): KorgeRenderer = KorgeRenderer(
    tileGrid = tileGrid.asInternal(),
    // TODO: use tileset loaders from config
    tilesetLoader = DefaultTilesetLoader(
        config.tilesetFactories.filter { it.targetType == KorgeContext::class }.fastCastTo()
    ),
)

/**
 * Creates a new [TileGrid] with a [NoOpApplication] implementation
 * (eg: no continuous rendering). Use this if you embed a [TileGrid] into your
 * own application, and you have your own renderer.
 */
fun createTileGrid(
    config: AppConfig = defaultAppConfig(),
    eventBus: EventBus = EventBus.create()
): TileGrid = DefaultTileGrid(config).apply {
    application = NoOpApplication(
        config = config,
        eventBus = eventBus,
        eventScope = ZirconScope()
    )
}