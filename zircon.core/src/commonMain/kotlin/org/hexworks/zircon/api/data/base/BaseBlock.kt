package org.hexworks.zircon.api.data.base

import kotlinx.collections.immutable.PersistentMap
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.BlockTileType.BACK
import org.hexworks.zircon.api.data.BlockTileType.BOTTOM
import org.hexworks.zircon.api.data.BlockTileType.CONTENT
import org.hexworks.zircon.api.data.BlockTileType.FRONT
import org.hexworks.zircon.api.data.BlockTileType.LEFT
import org.hexworks.zircon.api.data.BlockTileType.RIGHT
import org.hexworks.zircon.api.data.BlockTileType.TOP
import org.hexworks.zircon.api.data.Tile

/**
 * Base class which implements common functionality of
 * [Block]. This class is intended as a base class for
 * [Block] to implement custom blocks.
 */
abstract class BaseBlock<T : Tile>(
    override val emptyTile: T,
    override var tiles: PersistentMap<BlockTileType, T>
) : Block<T> {

    override var top: T
        get() = tiles[TOP] ?: emptyTile
        set(value) {
            tiles = tiles.put(TOP, value)
        }

    override var bottom: T
        get() = tiles[BOTTOM] ?: emptyTile
        set(value) {
            tiles = tiles.put(BOTTOM, value)
        }

    override var front: T
        get() = tiles[FRONT] ?: emptyTile
        set(value) {
            tiles = tiles.put(FRONT, value)
        }

    override var back: T
        get() = tiles[BACK] ?: emptyTile
        set(value) {
            tiles = tiles.put(BACK, value)
        }

    override var left: T
        get() = tiles[LEFT] ?: emptyTile
        set(value) {
            tiles = tiles.put(LEFT, value)
        }

    override var right: T
        get() = tiles[RIGHT] ?: emptyTile
        set(value) {
            tiles = tiles.put(RIGHT, value)
        }

    override var content: T
        get() = tiles[CONTENT] ?: emptyTile
        set(value) {
            tiles = tiles.put(CONTENT, value)
        }

    override fun isEmpty() = tiles.isEmpty() || tiles.values.all { it == emptyTile }

    override fun getTileByType(blockTileType: BlockTileType): T {
        return tiles[blockTileType] ?: emptyTile
    }

    override fun withFlippedAroundX(): Block<T> {
        return createCopy().apply {
            val temp = right
            this.right = left
            left = temp
        }
    }

    override fun withFlippedAroundY(): Block<T> {
        return createCopy().apply {
            val temp = front
            this.front = back
            back = temp
        }
    }

    override fun withFlippedAroundZ(): Block<T> {
        return createCopy().apply {
            val temp = top
            this.top = bottom
            bottom = temp
        }
    }

    override fun createCopy(): Block<T> {
        throw UnsupportedOperationException(
            "This implementation of Block doesn't support creating a copy. Consider extending BlockBase and implementing it."
        )
    }

    override fun toBuilder() = Block.newBuilder<T>()
        .withEmptyTile(emptyTile)
        .withTiles(tiles)

}
