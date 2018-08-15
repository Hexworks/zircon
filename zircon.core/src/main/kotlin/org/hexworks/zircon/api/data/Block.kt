package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.data.BlockSide.*

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [Tile]s.
 * The layers are ordered from bottom to top.
 */
interface Block {

    val position: Position3D
    val layers: MutableList<Tile>

    fun fetchSide(side: BlockSide): Tile = Tile.empty()

    fun isEmpty(): Boolean {
        return top() === Tile.empty() &&
                bottom() === Tile.empty() &&
                front() === Tile.empty() &&
                back() === Tile.empty() &&
                left() === Tile.empty() &&
                right() === Tile.empty() &&
                layers.isEmpty()
    }

    fun top(): Tile = fetchSide(TOP)

    fun bottom(): Tile = fetchSide(BOTTOM)

    fun front(): Tile = fetchSide(FRONT)

    fun back(): Tile = fetchSide(BACK)

    fun left(): Tile = fetchSide(LEFT)

    fun right(): Tile = fetchSide(RIGHT)

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
