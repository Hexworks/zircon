package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.builder.data.BlockBuilder

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [Tile]s.
 * The layers are ordered from bottom to top.
 */
interface Block {
    val position: Position3D
    val top: Tile
    val back: Tile
    val front: Tile
    val bottom: Tile
    val layers: MutableList<Tile>

    fun isEmpty(): Boolean {
        return top === Tile.empty() &&
                back === Tile.empty() &&
                front === Tile.empty() &&
                bottom === Tile.empty() &&
                layers.isEmpty()
    }

    companion object {


        fun create(position: Position): Block {
            return BlockBuilder.create()
                    .position(position)
                    .build()
        }

        fun create(position: Position3D): Block {
            return BlockBuilder.create()
                    .position(position)
                    .build()
        }
    }
}
