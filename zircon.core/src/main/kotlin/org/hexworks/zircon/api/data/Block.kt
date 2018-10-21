package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.data.impl.Position3D

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [Tile]s.
 * The layers are ordered from bottom to top.
 */
interface Block<T : Tile> {

    val position: Position3D
    val layers: MutableList<T>
    val top: T
    val bottom: T
    val front: T
    val back: T
    val left: T
    val right: T

    /**
     * Returns one of the [BlockSide]s of this [Block].
     */
    fun fetchSide(side: BlockSide): T

    /**
     * Tells whether this [Block] is empty (all of its sides are [Tile.empty],
     * and it has no `layers`).
     */
    fun isEmpty(): Boolean

    fun withPosition(position: Position3D): Block<T>

    companion object {


        fun <T : Tile> create(position: Position, emptyTile: T): Block<T> {
            return BlockBuilder.newBuilder<T>()
                    .withPosition(position)
                    .addLayer(emptyTile)
                    .build()
        }

        fun <T : Tile> create(position: Position3D, emptyTile: T): Block<T> {
            return BlockBuilder.newBuilder<T>()
                    .withPosition(position)
                    .addLayer(emptyTile)
                    .build()
        }
    }
}
