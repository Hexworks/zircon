package org.hexworks.zircon.api.grid

import org.hexworks.zircon.api.builder.grid.TileGridBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * The settings a [TileGrid] is created with. Build one through [TileGridBuilder]
 * (`tileGrid { ... }`); the grid exposes it as `config`.
 */
public data class TileGridConfig(
    /**
     * The [Size] of the grid in tiles.
     * Default is `80x40`
     */
    val size: Size,
    /**
     * The tileset the grid starts with. Components attached to the grid inherit it.
     * Default is [org.hexworks.zircon.api.CP437TilesetResources.rogueYun16x16]
     */
    val tileset: TilesetResource,
    /**
     * The amount of time (in milliseconds) that should pass before the next
     * blink of the cursor (if cursor is displayed and blink is used).
     * Default is `500`
     */
    val blinkLengthInMilliSeconds: Long,
    /**
     * Controls whether the cursor should be blinking when rendered.
     * Default is `false`
     */
    val isCursorBlinking: Boolean,
)
