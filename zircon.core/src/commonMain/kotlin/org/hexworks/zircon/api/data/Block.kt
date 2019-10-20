package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.builder.data.BlockBuilder

/**
 * A [Block] is a voxel which consists of [Tile]s representing
 * each side, and the internal [content] of the [Block].
 */
interface Block<T : Tile> {

    var content: T

    var top: T

    var bottom: T

    var front: T

    var back: T

    var left: T

    var right: T

    operator fun component1() = content
    operator fun component2() = top
    operator fun component3() = bottom
    operator fun component4() = front
    operator fun component5() = back
    operator fun component6() = left
    operator fun component7() = right

    fun createCopy(): Block<T>

    /**
     * Tells whether this [Block] is empty (all of its sides are [Tile.empty],
     * and its content is also [Tile.empty]).
     */
    fun isEmpty(): Boolean

    /**
     * Returns a new [Block] which is a rotation of this [Block]
     * around the *x* axis.
     */
    fun withFlippedAroundX(): Block<T> {
        return createCopy().apply {
            val temp = right
            this.right = left
            left = temp
        }
    }

    /**
     * Returns a new [Block] which is a rotation of this [Block]
     * around the *y* axis.
     */
    fun withFlippedAroundY(): Block<T> {
        return createCopy().apply {
            val temp = front
            this.front = back
            back = temp
        }
    }

    /**
     * Returns a new [Block] which is a rotation of this [Block]
     * around the *z* axis.
     */
    fun withFlippedAroundZ(): Block<T> {
        return createCopy().apply {
            val temp = top
            this.top = bottom
            bottom = temp
        }
    }

    companion object {

        fun <T : Tile> create(emptyTile: T): Block<T> {
            return BlockBuilder.newBuilder<T>()
                    .addLayer(emptyTile)
                    .build()
        }
    }
}
