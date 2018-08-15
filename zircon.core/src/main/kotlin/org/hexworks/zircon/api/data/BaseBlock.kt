package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.data.BlockSide.*

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [Tile]s.
 * The layers are ordered from bottom to top.
 */
abstract class BaseBlock : Block {

    override fun fetchSide(side: BlockSide): Tile = Tile.empty()

    override fun isEmpty(): Boolean {
        return top() === Tile.empty() &&
                bottom() === Tile.empty() &&
                front() === Tile.empty() &&
                back() === Tile.empty() &&
                left() === Tile.empty() &&
                right() === Tile.empty() &&
                layers.isEmpty()
    }

    override fun top(): Tile = fetchSide(TOP)

    override fun bottom(): Tile = fetchSide(BOTTOM)

    override fun front(): Tile = fetchSide(FRONT)

    override fun back(): Tile = fetchSide(BACK)

    override fun left(): Tile = fetchSide(LEFT)

    override fun right(): Tile = fetchSide(RIGHT)

}
