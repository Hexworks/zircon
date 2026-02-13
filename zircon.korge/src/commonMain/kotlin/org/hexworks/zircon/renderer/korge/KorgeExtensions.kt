package org.hexworks.zircon.renderer.korge

import korlibs.datastructure.fastCastTo
import korlibs.image.color.RGBA
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.AppConfig.Companion.defaultAppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.builder.application.tilesetFactory
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.createTileGrid
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.data.TileType.*
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.api.resource.TilesetType.*
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.tileset.DefaultTilesetLoader
import org.hexworks.zircon.renderer.korge.tileset.*

val DEFAULT_TILESET_FACTORIES: List<TilesetFactory<KorgeContext>> = listOf(
    tilesetFactory {
        targetType = KorgeContext::class
        tileType = CHARACTER_TILE
        tilesetType = CP437Tileset
        factoryFunction { resource: TilesetResource ->
            KorgeCP437Tileset(
                resource = resource,
            )
        }
    },
    tilesetFactory {
        targetType = KorgeContext::class
        tileType = GRAPHICAL_TILE
        tilesetType = GraphicalTileset
        factoryFunction { resource: TilesetResource ->
            KorgeGraphicalTileset(
                resource = resource,
            )
        }
    },
    tilesetFactory {
        targetType = KorgeContext::class
        tileType = CHARACTER_TILE
        tilesetType = TrueTypeFont
        factoryFunction { resource: TilesetResource ->
            KorgeTrueTypeFontTileset(
                resource = resource,
            )
        }
    },
    tilesetFactory {
        targetType = KorgeContext::class
        tileType = IMAGE_TILE
        tilesetType = GraphicalTileset
        factoryFunction { resource: TilesetResource ->
            KorgeImageDictionaryTileset(
                resource = resource,
            )
        }
    }
)

val DEFAULT_KORGE_TILESET_FACTORIES =
    DEFAULT_TILESET_FACTORIES.associateBy { it.supportedTileType to it.supportedTilesetType }.toMutableMap()

fun Color.toRGBA() = RGBA(red, green, blue, alpha)

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

fun korgeAppConfig(init: AppConfigBuilder.() -> Unit): AppConfig {
    return AppConfigBuilder().apply {
        tilesetFactories = DEFAULT_KORGE_TILESET_FACTORIES as MutableMap<Pair<TileType, TilesetType>, TilesetFactory<*>>
    }.apply(init).build()
}

