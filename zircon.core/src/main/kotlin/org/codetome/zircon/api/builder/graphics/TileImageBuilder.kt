package org.codetome.zircon.api.builder.graphics

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.internal.graphics.MapTileImage

/**
 * Creates [org.codetome.zircon.api.graphics.TileImage]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `filler` is an `EMPTY` character
 */
@Suppress("ArrayInDataClass")
data class TileImageBuilder(
        private var size: Size = Size.one(),
        private var filler: Tile = Tile.empty(),
        private val tiles: MutableMap<Position, Tile> = mutableMapOf()) : Builder<TileImage> {

    /**
     * Sets the size for the new [TileImage].
     * Default is 1x1.
     */
    fun size(size: Size) = also {
        this.size = size
    }


    /**
     * Adds a [Tile] at the given [Position].
     */
    fun tile(position: Position, tile: Tile) = also {
        require(size.containsPosition(position)) {
            "The given character's position ($position) is out create bounds for text image size: $size."
        }
        tiles[position] = tile
    }

    override fun build(): TileImage = MapTileImage(
            size = size,
            tiles = tiles)

    override fun createCopy() = copy()

    companion object {
        /**
         * Creates a new [TileImageBuilder] to build [org.codetome.zircon.api.graphics.TileImage]s.
         */
        fun newBuilder() = TileImageBuilder()
    }
}
