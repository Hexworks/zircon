package org.hexworks.zircon.api

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.application.KorGEApplication
import org.hexworks.zircon.api.application.NoOpApplication
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.dsl.tileset.buildTilesetFactory
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.renderer.impl.KorGERenderer
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetLoader
import org.hexworks.zircon.internal.tileset.impl.korge.KorGECP437DrawSurface
import org.hexworks.zircon.internal.tileset.impl.korge.KorGECP437Tileset
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

object Applications {

    /**
     * Builds a new [Application] using the given parameters. This factory method
     * uses sensible defaults, and it is fine to call it without parameters.
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
        renderer: Renderer<KorGEApplication> = createRenderer(config, tileGrid)
    ): Application = KorGEApplication(
        config = config,
        eventBus = eventBus,
        tileGrid = tileGrid.asInternal(),
        renderer = renderer
    )

    @JvmStatic
    @JvmOverloads
    fun startTileGrid(
        config: AppConfig = AppConfig.defaultConfiguration(),
        eventBus: EventBus = EventBus.create()
    ): TileGrid = startApplication(config, eventBus).tileGrid

    /**
     * Creates a new [Renderer] for the default [Application] using the given parameters.
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
    ): KorGERenderer = KorGERenderer(
        tileGrid = tileGrid.asInternal(),
        tilesetLoader = DefaultTilesetLoader(listOf<TilesetFactory<KorGECP437DrawSurface, CharacterTile>>(
            buildTilesetFactory {
                targetType = KorGECP437DrawSurface::class
                supportedTileType = TileType.CharacterTileType
                supportedTilesetType = TilesetType.CP437Tileset
                factoryFunction = { resource: TilesetResource ->
                    KorGECP437Tileset(
                        resource = resource,
                    )
                }
            }).associateBy { it.supportedTileType to it.supportedTilesetType }),
    )

    /**
     * Creates a new [TileGrid] with a [NoOpApplication] implementation
     * (eg: no continuous rendering).
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
}