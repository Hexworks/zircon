package org.hexworks.zircon.api.builder.grid

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.grid.TileGridConfig
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.extensions.asInternal
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.internal.screen.TileGridScreen

public const val DEFAULT_BLINK_LENGTH: Long = 500L
public const val DEFAULT_IS_CURSOR_BLINKING: Boolean = false
public val DEFAULT_TILESET: TilesetResource = CP437TilesetResources.rogueYun16x16()
public val DEFAULT_SIZE: Size = Size(80, 40)

/**
 * Builds [TileGrid]s. A renderer module (for example `zircon.compose`) takes the grid
 * and draws it; core itself never renders.
 *
 * ```kotlin
 * val grid = tileGrid {
 *     size = Size(80, 40)
 *     tileset = CP437TilesetResources.wanderlust16x16()
 * }
 * ```
 */
@ZirconDsl
public class TileGridBuilder : Builder<TileGrid> {

    /**
     * The [Size] of the grid in tiles.
     * Default is `80x40`
     */
    public var size: Size = DEFAULT_SIZE

    /**
     * The tileset the grid starts with. Components attached to the grid inherit it.
     * Default is [CP437TilesetResources.rogueYun16x16]
     */
    public var tileset: TilesetResource = DEFAULT_TILESET

    /**
     * The amount of time (in milliseconds) that should pass before the next
     * blink of the cursor (if cursor is displayed and blink is used).
     * Default is `500`
     */
    public var blinkLengthInMilliSeconds: Long = DEFAULT_BLINK_LENGTH

    /**
     * Controls whether the cursor should be blinking when rendered.
     * Default is `false`
     */
    public var isCursorBlinking: Boolean = DEFAULT_IS_CURSOR_BLINKING

    /**
     * The [EventBus] the grid publishes its events on. Default is a fresh bus.
     */
    public var eventBus: EventBus = EventBus.create()

    /**
     * Assembles the [TileGridConfig] the grid will be created with.
     */
    public fun buildConfig(): TileGridConfig = TileGridConfig(
        size = size,
        tileset = tileset,
        blinkLengthInMilliSeconds = blinkLengthInMilliSeconds,
        isCursorBlinking = isCursorBlinking,
    )

    /**
     * Creates a [TileGrid] using this builder's settings and immediately wraps it up in a [Screen].
     */
    public fun buildScreen(): Screen = TileGridScreen(build().asInternal())

    override fun build(): TileGrid = DefaultTileGrid(config = buildConfig(), eventBus = eventBus)
}

/**
 * Creates a new [TileGrid] using the builder DSL and returns it.
 */
public fun tileGrid(init: TileGridBuilder.() -> Unit = {}): TileGrid =
    TileGridBuilder().apply(init).build()

/**
 * Creates a new [TileGridConfig] using the builder DSL and returns it, without creating a grid.
 */
public fun tileGridConfig(init: TileGridBuilder.() -> Unit = {}): TileGridConfig =
    TileGridBuilder().apply(init).buildConfig()
