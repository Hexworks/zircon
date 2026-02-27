package org.hexworks.zircon.renderer.compose

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.AppConfig.Companion.defaultAppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.builder.application.tilesetFactory
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.createTileGrid
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.data.TileType.CHARACTER_TILE
import org.hexworks.zircon.api.data.TileType.GRAPHICAL_TILE
import org.hexworks.zircon.api.data.TileType.IMAGE_TILE
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.api.resource.TilesetType.CP437Tileset
import org.hexworks.zircon.api.resource.TilesetType.GraphicalTileset
import org.hexworks.zircon.api.resource.TilesetType.TrueTypeFont
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.tileset.DefaultTilesetLoader
import org.hexworks.zircon.renderer.compose.tileset.ComposeCP437Tileset
import org.hexworks.zircon.renderer.compose.tileset.ComposeGraphicalTileset
import org.hexworks.zircon.renderer.compose.tileset.ComposeImageDictionaryTileset
import org.hexworks.zircon.renderer.compose.tileset.ComposeTrueTypeFontTileset

/**
 * Default tileset factories for Compose rendering.
 */
val DEFAULT_TILESET_FACTORIES: List<TilesetFactory<ComposeContext>> = listOf(
    tilesetFactory {
        targetType = ComposeContext::class
        tileType = CHARACTER_TILE
        tilesetType = CP437Tileset
        factoryFunction { resource: TilesetResource ->
            ComposeCP437Tileset(resource = resource)
        }
    },
    tilesetFactory {
        targetType = ComposeContext::class
        tileType = GRAPHICAL_TILE
        tilesetType = GraphicalTileset
        factoryFunction { resource: TilesetResource ->
            ComposeGraphicalTileset(resource = resource)
        }
    },
    tilesetFactory {
        targetType = ComposeContext::class
        tileType = CHARACTER_TILE
        tilesetType = TrueTypeFont
        factoryFunction { resource: TilesetResource ->
            ComposeTrueTypeFontTileset(resource = resource)
        }
    },
    tilesetFactory {
        targetType = ComposeContext::class
        tileType = IMAGE_TILE
        tilesetType = GraphicalTileset
        factoryFunction { resource: TilesetResource ->
            ComposeImageDictionaryTileset(resource = resource)
        }
    }
)

/**
 * Default tileset factories map keyed by (TileType, TilesetType).
 */
val DEFAULT_COMPOSE_TILESET_FACTORIES =
    DEFAULT_TILESET_FACTORIES.associateBy { it.supportedTileType to it.supportedTilesetType }.toMutableMap()

/**
 * Converts a Zircon Color to an ARGB integer.
 */
fun Color.toArgb(): Int {
    return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
}

/**
 * Creates a new Compose application with the given configuration.
 *
 * @param config The application configuration
 * @param eventBus The event bus for application events
 * @return A new Compose application instance
 */
fun createComposeApplication(
    config: AppConfig = defaultAppConfig(),
    eventBus: EventBus = EventBus.create(),
): Application {
    val tileGrid = createTileGrid(config)
    return ComposeApplication(
        config = config,
        eventBus = eventBus,
        tileGrid = tileGrid.asInternal(),
        renderer = createComposeRenderer(config, tileGrid)
    )
}

/**
 * Creates a new Compose renderer with the given configuration.
 *
 * @param config The application configuration
 * @param tileGrid The tile grid to render
 * @return A new Compose renderer instance
 */
fun createComposeRenderer(
    config: AppConfig = defaultAppConfig(),
    tileGrid: TileGrid = createTileGrid(config),
): ComposeRenderer = ComposeRenderer(
    tileGrid = tileGrid.asInternal(),
    tilesetLoader = DefaultTilesetLoader(
        config.tilesetFactories
            .filter { it.targetType == ComposeContext::class }
            .filterIsInstance<TilesetFactory<ComposeContext>>()
    ),
)

/**
 * Creates an AppConfig pre-configured with Compose tileset factories.
 *
 * @param init Configuration block
 * @return Configured AppConfig
 */
fun composeAppConfig(init: AppConfigBuilder.() -> Unit): AppConfig {
    return AppConfigBuilder().apply {
        @Suppress("UNCHECKED_CAST")
        tilesetFactories = DEFAULT_COMPOSE_TILESET_FACTORIES as MutableMap<Pair<TileType, TilesetType>, TilesetFactory<*>>
    }.apply(init).build()
}
