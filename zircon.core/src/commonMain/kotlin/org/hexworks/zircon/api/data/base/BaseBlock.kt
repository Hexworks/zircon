package org.hexworks.zircon.api.data.base

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentMap
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
 * Base class which implements common functionality from
 * [Block].
 */
abstract class BaseBlock<T : Tile>(
        initialTiles: Map<BlockTileType, T>) : Block<T> {

    private var tiles: PersistentMap<BlockTileType, T> = initialTiles.toPersistentMap()

    override var top: T
        get() = tiles.getValue(TOP)
        set(value) {
            tiles = tiles.put(TOP, value)
        }

    override var bottom: T
        get() = tiles.getValue(BOTTOM)
        set(value) {
            tiles = tiles.put(BOTTOM, value)
        }

    override var front: T
        get() = tiles.getValue(FRONT)
        set(value) {
            tiles = tiles.put(FRONT, value)
        }

    override var back: T
        get() = tiles.getValue(BACK)
        set(value) {
            tiles = tiles.put(BACK, value)
        }

    override var left: T
        get() = tiles.getValue(LEFT)
        set(value) {
            tiles = tiles.put(LEFT, value)
        }

    override var right: T
        get() = tiles.getValue(RIGHT)
        set(value) {
            tiles = tiles.put(RIGHT, value)
        }

    override var content: T
        get() = tiles.getValue(CONTENT)
        set(value) {
            tiles = tiles.put(CONTENT, value)
        }

    override fun isEmpty() = tiles.values.all { it.isEmpty }

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
                "This implementation of Block doesn't support creating a copy. Consider extending BlockBase and implementing it.")
    }

}
