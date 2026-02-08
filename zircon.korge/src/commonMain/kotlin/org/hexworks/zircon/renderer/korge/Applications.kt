package org.hexworks.zircon.renderer.korge

import korlibs.datastructure.fastCastTo
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.AppConfig.Companion.defaultAppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.createTileGrid
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.tileset.DefaultTilesetLoader
import org.hexworks.zircon.renderer.korge.tileset.KorgeContext

fun createKorgeApplication(
    config: AppConfig = defaultAppConfig(),
    eventBus: EventBus = EventBus.create(),
): Application {
    val tileGrid = createTileGrid(config)
    return KorgeApplication(
        config = config,
        eventBus = eventBus,
        tileGrid = tileGrid.asInternal(),
        renderer = createKorgeRenderer(config, tileGrid)
    )
}

/**
 * Creates a new instance of the default [Renderer] implementation using the given parameters.
 *
 * This factory method uses sensible defaults, and it is fine to call it without parameters.
 *
 * Also make sure that all the objects you pass use the same [AppConfig].
 */
fun createKorgeRenderer(
    config: AppConfig = defaultAppConfig(),
    tileGrid: TileGrid = createTileGrid(config),
): KorgeRenderer = KorgeRenderer(
    tileGrid = tileGrid.asInternal(),
    // TODO: use tileset loaders from config
    tilesetLoader = DefaultTilesetLoader(
        config.tilesetFactories.filter { it.targetType == KorgeContext::class }.fastCastTo()
    ),
)
