package org.hexworks.zircon.api

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.dsl.tileset.buildTilesetFactory
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.modifier.*
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.application.SwingApplication
import org.hexworks.zircon.internal.modifier.TileCoordinate
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TileType.*
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.resource.TilesetType.*
import org.hexworks.zircon.internal.tileset.*
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetLoader
import org.hexworks.zircon.internal.tileset.transformer.*
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import kotlin.reflect.KClass

object SwingApplications {

    /**
     * Builds a new [Application] using the given `appConfig`.
     */
    @JvmStatic
    @JvmOverloads
    fun buildApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): Application {
        return SwingApplication.create(config, eventBus)
    }

    /**
     * Builds and starts a new [Application] from the given `appConfig`.
     */
    @JvmStatic
    @JvmOverloads
    fun startApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): Application = buildApplication(config, eventBus).also {
        it.start()
    }


    /**
     * Builds and starts a new [Application] and returns its [TileGrid].
     */
    @JvmStatic
    @JvmOverloads
    fun startTileGrid(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): TileGrid = startApplication(config, eventBus).tileGrid

    @JvmStatic
    fun defaultTilesetLoader(): TilesetLoader<Graphics2D> = DefaultTilesetLoader(DEFAULT_FACTORIES)

    internal val DEFAULT_FACTORIES: Map<Pair<TileType, TilesetType>, TilesetFactory<Graphics2D>> =
        listOf<TilesetFactory<Graphics2D>>(
            buildTilesetFactory {
                targetType = Graphics2D::class
                supportedTileType = CHARACTER_TILE
                supportedTilesetType = CP437Tileset
                factoryFunction = { resource: TilesetResource ->
                    Java2DCP437Tileset(
                        resource = resource,
                        source = ImageLoader.readImage(resource),
                        textureTransformers = DEFAULT_CHARACTER_TEXTURE_TRANSFORMERS
                    )
                }
            },
            buildTilesetFactory {
                targetType = Graphics2D::class
                supportedTileType = CHARACTER_TILE
                supportedTilesetType = TrueTypeFont
                factoryFunction = { resource: TilesetResource ->
                    MonospaceAwtFontTileset(resource)
                }
            },
            buildTilesetFactory {
                targetType = Graphics2D::class
                supportedTileType = GRAPHICAL_TILE
                supportedTilesetType = GraphicalTileset
                factoryFunction = { resource: TilesetResource ->
                    Java2DGraphicTileset(resource)
                }
            },
            buildTilesetFactory {
                targetType = Graphics2D::class
                supportedTileType = IMAGE_TILE
                supportedTilesetType = GraphicalTileset
                factoryFunction = { resource: TilesetResource ->
                    Java2DImageDictionaryTileset(resource)
                }
            }).associateBy { it.supportedTileType to it.supportedTilesetType }

    internal val DEFAULT_CHARACTER_TEXTURE_TRANSFORMERS: Map<KClass<out TextureTransformModifier>, TextureTransformer<BufferedImage>> =
        mapOf(
            SimpleModifiers.Underline::class to Java2DUnderlineTransformer(),
            SimpleModifiers.VerticalFlip::class to Java2DVerticalFlipper(),
            SimpleModifiers.Blink::class to Java2DNoOpTransformer(),
            SimpleModifiers.HorizontalFlip::class to Java2DHorizontalFlipper(),
            SimpleModifiers.CrossedOut::class to Java2DCrossedOutTransformer(),
            SimpleModifiers.Hidden::class to Java2DHiddenTransformer(),
            Glow::class to Java2DGlowTransformer(),
            Border::class to Java2DBorderTransformer(),
            Crop::class to Java2DCropTransformer(),
            TileCoordinate::class to Java2DTileCoordinateTransformer()
        ).toMap()

}
