package org.hexworks.zircon.api.builder.data

import kotlinx.collections.immutable.toPersistentMap
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.BlockTileType.*
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.data.DefaultBlock

/**
 * Builds [Block]s.
 * Has no [Tile]s for either sides by default.
 * Setting an [emptyTile] is **mandatory** and has no default.
 */
@Suppress("UNCHECKED_CAST")
class BlockBuilder<T : Tile> private constructor(
    private var emptyTile: T? = null,
    private val tiles: MutableMap<BlockTileType, T> = mutableMapOf()
) : Builder<Block<T>> {

    fun withEmptyTile(tile: T) = also {
        this.emptyTile = tile
    }

    fun withTop(top: T) = also {
        tiles[TOP] = top
    }

    fun withBottom(bottom: T) = also {
        tiles[BOTTOM] = bottom
    }

    fun withFront(front: T) = also {
        tiles[FRONT] = front
    }

    fun withBack(back: T) = also {
        tiles[BACK] = back
    }

    fun withLeft(left: T) = also {
        tiles[LEFT] = left
    }

    fun withRight(right: T) = also {
        tiles[RIGHT] = right
    }

    fun withContent(content: T) = also {
        tiles[CONTENT] = content
    }

    /**
     * Sets this [tile] on all sides.
     */
    fun withTileOnAllSides(tile: T) = also {
        BlockTileType.values().forEach {
            tiles[it] = tile
        }
    }

    /**
     * Overwrites the [Tile]s in this [BlockBuilder] with the
     * given [BlockTileType] -> [Tile] mapping.
     */
    fun withTiles(tiles: Map<BlockTileType, T>) = also {
        this.tiles.clear()
        this.tiles.putAll(tiles)
    }

    override fun build(): Block<T> {
        requireNotNull(emptyTile) {
            "Can't build block: no empty tile supplied."
        }
        return DefaultBlock(
            emptyTile = emptyTile!!,
            initialTiles = tiles.toPersistentMap()
        )
    }

    override fun createCopy() = BlockBuilder(
        emptyTile = emptyTile,
        tiles = tiles.toMutableMap()
    )

    companion object {

        /**
         * Creates a new [BlockBuilder] for creating [Block]s.
         */
        fun <T : Tile> newBuilder() = BlockBuilder<T>()

    }
}
