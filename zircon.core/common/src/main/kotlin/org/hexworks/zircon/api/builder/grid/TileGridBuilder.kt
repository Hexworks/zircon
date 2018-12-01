package org.hexworks.zircon.api.builder.grid

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.input.InternalTileGrid
import org.hexworks.zircon.internal.input.RectangleTileGrid
import org.hexworks.zircon.internal.screen.TileGridScreen

/**
 * Builds [TileGrid]s.
 * Defaults are:
 * - default `initialSize` is 80x24
 * - default `tileset` is `WANDERLUST` (cp437)
 */
open class TileGridBuilder(
        private var size: Size = Size.defaultGridSize(),
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
) : Builder<TileGrid> {

    override fun build(): TileGrid {
        return RectangleTileGrid(
                tileset = tileset,
                size = size)
    }

    override fun createCopy(): TileGridBuilder = TileGridBuilder(
            size = size,
            tileset = tileset)

    /**
     * Sets the initial grid [Size].
     * Default is 80x24.
     */
    fun withSize(size: Size) = also {
        this.size = size
    }

    /**
     * Sets a tileset for this [TileGrid].
     */
    fun withTileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    /**
     * Creates a [TileGrid] using this builder's settings and immediately wraps it up in a [Screen].
     */
    fun buildScreen() = TileGridScreen(
            tileGrid = build() as InternalTileGrid)

    companion object {

        /**
         * Creates a new [TileGridBuilder].
         */
        fun newBuilder() = TileGridBuilder()
    }
}
