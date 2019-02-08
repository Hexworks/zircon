package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.BlockSide.*
import org.hexworks.zircon.api.data.Tile

/**
 * Base class which implements common functionality from
 * [Block].
 */
abstract class BlockBase<T : Tile> : Block<T> {

    override var top: T
        get() = fetchSide(TOP)
        set(value) {
            setSide(TOP, value)
        }

    override var bottom: T
        get() = fetchSide(BOTTOM)
        set(value) {
            setSide(BOTTOM, value)
        }

    override var front: T
        get() = fetchSide(FRONT)
        set(value) {
            setSide(FRONT, value)
        }

    override var back: T
        get() = fetchSide(BACK)
        set(value) {
            setSide(BACK, value)
        }

    override var left: T
        get() = fetchSide(LEFT)
        set(value) {
            setSide(LEFT, value)
        }

    override var right: T
        get() = fetchSide(RIGHT)
        set(value) {
            setSide(RIGHT, value)
        }

    override fun isEmpty(): Boolean {
        return layers.isEmpty() and
                listOf(top, bottom, front, back, left, right).all { it.isEmpty() }
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

    override fun setSide(side: BlockSide, tile: T) {
        throw UnsupportedOperationException(
                "This implementation of Block doesn't support setting a side. Consider extending BlockBase and implementing it.")
    }

    override fun createCopy(): Block<T> {
        throw UnsupportedOperationException(
                "This implementation of Block doesn't support creating a copy. Consider extending BlockBase and implementing it.")
    }

}
