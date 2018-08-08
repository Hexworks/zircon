package org.codetome.zircon.api.builder.graphics

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.internal.config.RuntimeConfig
import org.codetome.zircon.internal.graphics.MapTileImage

/**
 * Creates [org.codetome.zircon.api.graphics.TileImage]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `filler` is an `EMPTY` character
 */
@Suppress("ArrayInDataClass")
data class TileImageBuilder(
        private var tileset: TilesetResource<out Tile> = RuntimeConfig.config.defaultTileset,
        private var filler: Tile = Tile.empty(),
        private var size: Size = Size.one(),
        private var style: StyleSet = StyleSet.defaultStyle(),
        private val tiles: MutableMap<Position, Tile> = mutableMapOf()) : Builder<TileImage> {

    fun tileset(tileset: TilesetResource<out Tile>) = also {
        this.tileset = tileset
    }

    fun style(style: StyleSet) = also {
        this.style = style
    }

    fun filler(filler: Tile) = also {
        this.filler = filler
    }

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
            tileset = tileset,
            styleSet = StyleSet.defaultStyle())

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [TileImageBuilder] to build [org.codetome.zircon.api.graphics.TileImage]s.
         */
        fun newBuilder() = TileImageBuilder()
    }
}
