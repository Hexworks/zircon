package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.internal.graphics.DefaultTileComposite
import kotlin.jvm.JvmStatic

/**
 * Creates [TileComposite]s.
 * Defaults:
 * - Default [Size] is `ONE` (1x1).
 */
@Suppress("ArrayInDataClass")
data class TileCompositeBuilder(
    private var size: Size = Size.one(),
    private var tiles: MutableMap<Position, Tile> = mutableMapOf()
) : Builder<TileComposite> {

    /**
     * Sets the size for the new [TileComposite]. Note that [tiles] will
     * be filtered for the new [size] if it has a smaller width or height
     * than the original.
     */
    fun withSize(size: Size) = also {
        if (this.size.width > size.width || this.size.height > size.height) {
            removeOutOfBoundsTiles(size)
        }
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
     * Sets the given [tiles] to be used for the new [TileGraphics].
     */
    fun withTiles(tiles: Map<Position, Tile>) = also {
        this.tiles.clear()
        this.tiles.putAll(tiles)
        removeOutOfBoundsTiles()
    }

    private fun removeOutOfBoundsTiles(size: Size = this.size) {
        this.tiles = tiles
            .filterKeys { size.containsPosition(it) }
            .toMutableMap()
    }

    override fun build(): TileComposite {
        return DefaultTileComposite(tiles.toMap(), size)
    }

    override fun createCopy() = copy()

    companion object {

        /**
         * Creates a new [TileCompositeBuilder] to build [TileComposite]s.
         */
        @JvmStatic
        fun newBuilder() = TileCompositeBuilder()
    }
}
