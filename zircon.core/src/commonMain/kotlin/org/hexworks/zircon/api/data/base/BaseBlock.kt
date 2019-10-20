package org.hexworks.zircon.api.data.base

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentMap
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.BlockTileType.*
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.platform.extension.getOrDefault

/**
 * Base class which implements common functionality from
 * [Block].
 */
abstract class BaseBlock<T : Tile>(
        private val emptyTile: T,
        initialTiles: Map<BlockTileType, T>) : Block<T> {

    private var tiles: PersistentMap<BlockTileType, T> = initialTiles.toPersistentMap()

    override var top: T
        get() = tiles.getOrDefault(TOP, emptyTile)
        set(value) {
            tiles = tiles.put(TOP, value)
        }

    override var bottom: T
        get() = tiles.getOrDefault(BOTTOM, emptyTile)
        set(value) {
            tiles = tiles.put(BOTTOM, value)
        }

    override var front: T
        get() = tiles.getOrDefault(FRONT, emptyTile)
        set(value) {
            tiles = tiles.put(FRONT, value)
        }

    override var back: T
        get() = tiles.getOrDefault(BACK, emptyTile)
        set(value) {
            tiles = tiles.put(BACK, value)
        }

    override var left: T
        get() = tiles.getOrDefault(LEFT, emptyTile)
        set(value) {
            tiles = tiles.put(LEFT, value)
        }

    override var right: T
        get() = tiles.getOrDefault(RIGHT, emptyTile)
        set(value) {
            tiles = tiles.put(RIGHT, value)
        }

    override var content: T
        get() = tiles.getOrDefault(CONTENT, emptyTile)
        set(value) {
            tiles = tiles.put(CONTENT, value)
        }

    override fun isEmpty() = tiles.isEmpty() || tiles.values.all { it.isEmpty }

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
