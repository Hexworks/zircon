package org.hexworks.zircon.api.data

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [Tile]s.
 * The layers are ordered from bottom to top.
 */
abstract class BaseBlock : Block {

    override fun fetchSide(side: BlockSide): Tile = Tile.empty()

    override fun isEmpty(): Boolean {
        return top === Tile.empty() &&
                bottom === Tile.empty() &&
                front === Tile.empty() &&
                back === Tile.empty() &&
                left === Tile.empty() &&
                right === Tile.empty() &&
                layers.isEmpty()
    }

    override val top: Tile
        get() = fetchSide(BlockSide.TOP)

    override val bottom: Tile
        get() = fetchSide(BlockSide.BOTTOM)

    override val front: Tile
        get() = fetchSide(BlockSide.FRONT)

    override val back: Tile
        get() = fetchSide(BlockSide.BACK)

    override val left: Tile
        get() = fetchSide(BlockSide.LEFT)

    override val right: Tile
        get() = fetchSide(BlockSide.RIGHT)

}
