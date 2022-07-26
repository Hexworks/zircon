package org.hexworks.zircon.api

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.*
import org.hexworks.zircon.api.dsl.tileset.buildTilesetFactory
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.modifier.*
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.application.SwingApplication
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.renderer.SwingCanvasRenderer
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TileType.*
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.resource.TilesetType.*
import org.hexworks.zircon.internal.tileset.*
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetLoader
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.impl.SwingFrame
import org.hexworks.zircon.internal.modifier.TileCoordinate
import org.hexworks.zircon.internal.tileset.transformer.*
import java.awt.Canvas
import javax.swing.JFrame
import kotlin.reflect.KClass

object SwingApplications {

    /**
     * Builds a new [Application] using the given parameters. This factory method
     * uses sensible defaults, and it is fine to call it without parameters.
     *
     * **Note that** [buildApplication] will overwrite the [Application] that the
     * [TileGrid] is currently using.
     *
     * Also make sure that all the objects you pass use the same [AppConfig].
     */
    @JvmStatic
    @JvmOverloads
    fun buildApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create(),
        tileGrid: TileGrid = createTileGrid(config),
        renderer: SwingCanvasRenderer = createRenderer(config, tileGrid)
    ): Application = SwingApplication(
        config = config,
        eventBus = eventBus,
        tileGrid = tileGrid.asInternal(),
        renderer = renderer
    )

    /**
     * Builds a new [Application] using the given parameters and calls [Application.start] on it.
     * This factory method uses sensible defaults, and it is fine to call it without parameters.
     *
     * **Note that** [startApplication] will overwrite the [Application] that the
     * [TileGrid] is currently using.
     *
     * Also make sure that all the objects you pass use the same [AppConfig].
     */
    @JvmStatic
    @JvmOverloads
    fun startApplication(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create(),
        tileGrid: TileGrid = createTileGrid(config),
        renderer: SwingCanvasRenderer = createRenderer(config, tileGrid),
    ): Application = buildApplication(
        config = config,
        eventBus = eventBus,
        tileGrid = tileGrid,
        renderer = renderer
    )


    /**
     * Builds a new [Application] using the given parameters, calls [Application.start] on it
     * and returns its [TileGrid] only. Use this function if you don't care about how the
     * [Application] works and you don't want to manage its lifecycle.
     *
     * This factory method uses sensible defaults, and it is fine to call it without parameters.
     *
     * **Note that** [startTileGrid] will overwrite the [Application] that the
     * [TileGrid] is currently using.
     *
     * Also make sure that all the objects you pass use the same [AppConfig].
     */
    @JvmStatic
    @JvmOverloads
    fun startTileGrid(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): TileGrid = startApplication(config, eventBus).tileGrid

    /**
     * Creates a new [TileGrid]. Use this if you don't want an [Application],
     * just a [TileGrid] or a [TileGrid] with a [Renderer].
     *
     * **Note that** [createTileGrid] will attach a [NoOpApplication] to the
     * [TileGrid].
     *
     * This factory method uses sensible defaults, and it is fine to call it without parameters.
     *
     * Creating a [TileGrid] without an [Application] is a **beta** feature. Feel free to report
     * a bug if it is not working [here](https://github.com/Hexworks/zircon/issues/new?assignees=&labels=&template=bug_report.md&title=).
     */

    @JvmStatic
    @JvmOverloads
    fun createTileGrid(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): TileGrid = ThreadSafeTileGrid(config).apply {
        application = NoOpApplication(
            config = config,
            eventBus = eventBus,
            eventScope = ZirconScope()
        )
    }

    /**
     * Creates a new [SwingCanvasRenderer] using the given parameters.
     *
     * This factory method uses sensible defaults, and it is fine to call it without parameters.
     *
     * Also make sure that all the objects you pass use the same [AppConfig].
     *
     * Creating a [Renderer] without an [Application] is a **beta** feature. Feel free to report
     * a bug if it is not working [here](https://github.com/Hexworks/zircon/issues/new?assignees=&labels=&template=bug_report.md&title=).
     */

    @JvmStatic
    @JvmOverloads
    fun createRenderer(
        config: AppConfig = AppConfig.defaultConfiguration(),
        tileGrid: TileGrid = createTileGrid(config),
        /**
         * The grid will be drawn on this [Canvas]. Use this parameter
         * if you want to provide your own.
         */
        canvas: Canvas = Canvas(),
        /**
         * The [JFrame] that will display the [canvas]. Use this parameter
         * if you want to provide your own.
         */
        frame: JFrame = SwingFrame(
            tileGrid = tileGrid.asInternal(),
            canvas = canvas
        ),
        /**
         * If set to `false` initializations won't be run on [canvas] and [frame].
         * This includes disabling focus traversal (between Swing components) and
         * displaying and packing the [frame] itself.
         * Set this to `false` if you want to handle these yourself. A typical example
         * of this would be the case when you're using multiple [Application]s.
         */
        shouldInitializeSwingComponents: Boolean = true
    ): SwingCanvasRenderer = SwingCanvasRenderer.create(
        tileGrid = tileGrid.asInternal(),
        tilesetLoader = DefaultTilesetLoader(
            createDefaultFactories(
                DEFAULT_MODIFIER_SUPPORTS
            ) + config.tilesetLoaders.filterByType(Graphics2D::class)
        ),
        canvas = canvas,
        frame = frame,
        shouldInitializeSwingComponents = shouldInitializeSwingComponents
    )

    /**
     * Returns a [TilesetLoader] that uses the built-in [TilesetFactory] objects and
     * the overrides from the [config] object.
     */
    @JvmStatic
    @JvmOverloads
    fun createTilesetLoader(
        config: AppConfig = AppConfig.defaultConfiguration()
    ): TilesetLoader<Graphics2D> =
        DefaultTilesetLoader(
            createDefaultFactories(
                config.modifierSupports.filterByType(BufferedImage::class)
            ) + config.tilesetLoaders.filterByType(Graphics2D::class)
        )

    private fun createDefaultFactories(
        modifierOverrides: Map<KClass<out TextureTransformModifier>, ModifierSupport<BufferedImage>>
    ): Map<Pair<TileType, TilesetType>, TilesetFactory<Graphics2D>> =
        listOf<TilesetFactory<Graphics2D>>(
            buildTilesetFactory {
                targetType = Graphics2D::class
                supportedTileType = CHARACTER_TILE
                supportedTilesetType = CP437Tileset
                factoryFunction = { resource: TilesetResource ->
                    Java2DCP437Tileset(
                        resource = resource,
                        source = ImageLoader.readImage(resource),
                        modifierSupports = DEFAULT_MODIFIER_SUPPORTS + modifierOverrides
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

    private val DEFAULT_MODIFIER_SUPPORTS: Map<KClass<out TextureTransformModifier>, ModifierSupport<BufferedImage>> =
        listOf(
            buildSwingModifierSupport {
                modifierType = SimpleModifiers.Underline::class
                transformer = Java2DUnderlineTransformer()
            },
            buildSwingModifierSupport {
                modifierType = SimpleModifiers.VerticalFlip::class
                transformer = Java2DVerticalFlipper()
            },
            buildSwingModifierSupport {
                modifierType = SimpleModifiers.Blink::class
                transformer = Java2DNoOpTransformer()
            },
            buildSwingModifierSupport {
                modifierType = SimpleModifiers.HorizontalFlip::class
                transformer = Java2DHorizontalFlipper()
            },
            buildSwingModifierSupport {
                modifierType = SimpleModifiers.CrossedOut::class
                transformer = Java2DCrossedOutTransformer()
            },
            buildSwingModifierSupport {
                modifierType = SimpleModifiers.Hidden::class
                transformer = Java2DHiddenTransformer()
            },
            buildSwingModifierSupport {
                modifierType = Glow::class
                transformer = Java2DGlowTransformer()
            },
            buildSwingModifierSupport {
                modifierType = Border::class
                transformer = Java2DBorderTransformer()
            },
            buildSwingModifierSupport {
                modifierType = Crop::class
                transformer = Java2DCropTransformer()
            },
            buildSwingModifierSupport {
                modifierType = TileCoordinate::class
                transformer = Java2DTileCoordinateTransformer()
            },
        ).associateBy { it.modifierType }
}
