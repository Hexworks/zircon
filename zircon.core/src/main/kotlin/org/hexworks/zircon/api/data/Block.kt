package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.data.impl.Position3D

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [Tile]s.
 * The layers are ordered from bottom to top.
 */
interface Block {

    val position: Position3D
    val layers: MutableList<Tile>
    val top: Tile
    val bottom: Tile
    val front: Tile
    val back: Tile
    val left: Tile
    val right: Tile

    /**
     * Returns one of the [BlockSide]s of this [Block].
     */
    fun fetchSide(side: BlockSide): Tile

    /**
     * Tells whether this [Block] is empty (all of its sides are [Tile.empty],
     * and it has no `layers`).
     */
    fun isEmpty(): Boolean

    companion object {


        fun create(position: Position, tile: Tile = Tile.empty()): Block {
            return BlockBuilder.create()
                    .position(position)
                    .layer(tile)
                    .build()
        }

        fun create(position: Position3D, tile: Tile = Tile.empty()): Block {
            return BlockBuilder.create()
                    .position(position)
                    .layer(tile)
                    .build()
        }
    }
}
