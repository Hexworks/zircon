package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.graphics.DefaultTileImage
import kotlin.jvm.JvmStatic

/**
 * Creates [org.hexworks.zircon.api.graphics.TileGraphics]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 * - Default `filler` is an `EMPTY` character
 */
@Suppress("ArrayInDataClass")
data class TileImageBuilder(
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        private var filler: Tile = Tile.empty(),
        private var size: Size = Size.one(),
        private val tiles: MutableMap<Position, Tile> = mutableMapOf()
) : Builder<TileImage> {

    /**
     * Sets the [tileset] to be used when building this [TileImage].
     * The default comes from [RuntimeConfig.config].
     */
    fun withTileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    /**
     * Sets the [filler] to be used when the [TileImage] is built.
     * Default is [Tile.empty]
     */
    fun withFiller(filler: Tile) = also {
        this.filler = filler
    }

    /**
     * Sets the size for the new [TileImage].
     * Default is [Size.one].
     */
    fun withSize(size: Size) = also {
        this.size = size
    }

    /**
     * Adds a [Tile] at the given [Position].
     */
    fun withTile(position: Position, tile: Tile) = also {
        require(size.containsPosition(position)) {
            "The given character's position ($position) is out create bounds for text image size: $size."
        }
        tiles[position] = tile
    }

    /**
     * Sets the given [tiles] to be used for the new [TileImage].
     */
    fun withTiles(tiles: Map<Position, Tile>) = also {
        this.tiles.clear()
        this.tiles.putAll(tiles)
    }

    override fun build(): TileImage {
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                initialTiles = tiles.filter { size.containsPosition(it.key) }
        ).withFiller(filler)
    }

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [TileImageBuilder] to build [TileImage] objects.
         */
        @JvmStatic
        fun newBuilder() = TileImageBuilder()
    }
}
