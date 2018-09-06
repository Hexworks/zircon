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
data class TileGraphicBuilder(
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        private var filler: Tile = Tile.empty(),
        private var size: Size = Size.one(),
        private var style: StyleSet = StyleSet.defaultStyle(),
        private val tiles: MutableMap<Position, Tile> = mutableMapOf()) : Builder<TileGraphics> {

    fun tileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    fun style(style: StyleSet) = also {
        this.style = style
    }

    fun filler(filler: Tile) = also {
        this.filler = filler
    }

    /**
     * Sets the size for the new [TileGraphics].
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

    override fun build(): TileGraphics = ConcurrentTileGraphics(
            size = size,
            tileset = tileset,
            styleSet = StyleSet.defaultStyle()).also { image ->
        tiles.forEach { (pos, tile) ->
            image.setTileAt(pos, tile)
        }
    }

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [TileGraphicBuilder] to build [org.hexworks.zircon.api.graphics.TileGraphics]s.
         */
        fun newBuilder() = TileGraphicBuilder()
    }
}
