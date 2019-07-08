package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.graphics.ConcurrentTileGraphics

/**
 * Creates [org.hexworks.zircon.api.graphics.TileGraphics]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `filler` is an `EMPTY` character
 */
@Suppress("ArrayInDataClass")
data class TileGraphicsBuilder(
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        private var size: Size = Size.one(),
        private var style: StyleSet = StyleSet.defaultStyle(),
        private val tiles: MutableMap<Position, Tile> = mutableMapOf()) : Builder<TileGraphics> {

    fun withTileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    /**
     * Sets the size for the new [TileGraphics].
     * Default is 1x1.
     */
    fun withSize(size: Size) = also {
        this.size = size
    }

    /**
     * Adds a [Tile] at the given [Position].
     */
    fun withTile(position: Position, tile: Tile) = also {
        if (size.containsPosition(position)) {
            tiles[position] = tile
        }
    }

    override fun build(): TileGraphics = ConcurrentTileGraphics(
            size = size,
            tileset = tileset).also { image ->
        tiles.forEach { (pos, tile) ->
            image.setTileAt(pos, tile)
        }
    }

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [TileGraphicsBuilder] to build [org.hexworks.zircon.api.graphics.TileGraphics]s.
         */
        fun newBuilder() = TileGraphicsBuilder()
    }
}
