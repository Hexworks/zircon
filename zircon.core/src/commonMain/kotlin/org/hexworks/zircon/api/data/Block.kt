package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.data.impl.Position3D

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [Tile]s.
 * The layers are ordered from bottom to top.
 */
interface Block<T : Tile> {

    val layers: MutableList<T>
    var top: T
    var bottom: T
    var front: T
    var back: T
    var left: T
    var right: T

    fun createCopy(): Block<T>

    /**
     * Returns one of the [BlockSide]s of this [Block].
     */
    fun fetchSide(side: BlockSide): T

    /**
     * Sets one of the [BlockSide]s of this [Block].
     */
    fun setSide(side: BlockSide, tile: T)

    /**
     * Tells whether this [Block] is empty (all of its sides are [Tile.empty],
     * and it has no `layers`).
     */
    fun isEmpty(): Boolean

    fun withFlippedAroundX(): Block<T> {
        return createCopy().apply {
            val temp = right
            this.right = left
            left = temp
        }
    }

    fun withFlippedAroundY(): Block<T> {
        return createCopy().apply {
            val temp = front
            this.front = back
            back = temp
        }
    }

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
