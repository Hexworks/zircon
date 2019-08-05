package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.graphics.ReadSafeTileGraphics
import org.hexworks.zircon.internal.graphics.ThreadSafeTileGraphics

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
        private val tiles: MutableMap<Position, Tile> = mutableMapOf(),
        private var filler: Tile = Tiles.empty()) : Builder<TileGraphics> {

    override fun createCopy() = copy(
            tiles = tiles.toMutableMap())

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
     * Sets the filler for the new [TileGraphics] which
     * will be used to fill the empty spaces. Default is
     * [Tiles.empty] which means no filling
     */
    fun withFiller(filler: Tile) = also {
        this.filler = filler
    }

    /**
     * Adds a [Tile] at the given [Position].
     */
    fun withTile(position: Position, tile: Tile) = also {
        if (size.containsPosition(position)) {
            tiles[position] = tile
        }
    }

    /**
     * Sets the given [tiles] to be used for the new [TileGraphics].
     */
    fun withTiles(tiles: Map<Position, Tile>) = also {
        this.tiles.clear()
        this.tiles.putAll(tiles)
    }

    /**
     * Builds a fast [TileGraphics] implementation which is not thread
     * safe and offers no consistent snapshots. Use this implementation
     * if you're not reading nor writing from multiple threads.
     */
    override fun build(): TileGraphics = FastTileGraphics(
            size = size,
            tileset = tileset,
            initialTiles = tiles.toMap()).apply {
        if(hasToFill()) fill(filler)
    }

    /**
     * Builds a read-safe [TileGraphics] implementation. Read safety means
     * that all read operations (like getTileAt, createSnapshot) are consistent
     * even if concurrent write operations are being performed. Use this implementation
     * if you own't use multiple threads when writing to this object.
     */
    fun buildReadSafeTileGraphics(): TileGraphics = ReadSafeTileGraphics(
            size = size,
            tileset = tileset,
            initialTiles = tiles.toMap()).apply {
        if(hasToFill()) fill(filler)
    }

    /**
     * Builds a thread-safe [TileGraphics] implementation. It offers consistent
     * snapshot creation, and consistent writes.
     * **Note that** writes are confined to one thread so batch writing might be
     * inefficient (eg: calling `setTileAt` repeatedly). Consider using [FastTileGraphics]
     * or [ReadSafeTileGraphics] if you need to do many transformations,
     * but you only use one thread.
     */
    fun buildThreadSafeTileGraphics(): TileGraphics = ThreadSafeTileGraphics(
            size = size,
            tileset = tileset,
            initialTiles = tiles.toMap()).apply {
        if(hasToFill()) fill(filler)
    }

    private fun hasToFill() = filler != Tiles.empty()

    companion object {

        /**
         * Creates a new [TileGraphicsBuilder] to build [org.hexworks.zircon.api.graphics.TileGraphics]s.
         */
        fun newBuilder() = TileGraphicsBuilder()
    }
}
