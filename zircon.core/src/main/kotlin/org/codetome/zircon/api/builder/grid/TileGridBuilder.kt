package org.codetome.zircon.api.builder.grid

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.internal.config.RuntimeConfig
import org.codetome.zircon.internal.grid.InternalTileGrid
import org.codetome.zircon.internal.grid.RectangleTileGrid
import org.codetome.zircon.internal.screen.TileGridScreen

/**
 * Builds [TileGrid]s.
 * Defaults are:
 * - default `initialSize` is 80x24
 * - default `tileset` is `WANDERLUST` (cp437)
 */
open class TileGridBuilder(
        private var size: Size = Size.defaultTerminalSize(),
        private var tileset: TilesetResource<out Tile> = RuntimeConfig.config.defaultTileset
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
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * Sets a tileset for this [TileGrid].
     */
    fun tileset(tileset: TilesetResource<out Tile>) = also {
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
