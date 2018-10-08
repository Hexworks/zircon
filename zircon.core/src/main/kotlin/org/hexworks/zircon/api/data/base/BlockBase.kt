package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.BlockSide.*
import org.hexworks.zircon.api.data.Tile

/**
 * Base class which implements common functionality from
 * [Block].
 */
abstract class BlockBase : Block {

    override val top: Tile
        get() = fetchSide(TOP)

    override val bottom: Tile
        get() = fetchSide(BOTTOM)

    override val front: Tile
        get() = fetchSide(FRONT)

    override val back: Tile
        get() = fetchSide(BACK)

    override val left: Tile
        get() = fetchSide(LEFT)

    override val right: Tile
        get() = fetchSide(RIGHT)

    override fun fetchSide(side: BlockSide): Tile = Tile.empty()

    override fun isEmpty(): Boolean {
        return layers.isEmpty() and
                listOf(top, bottom, front, back, left, right).all { it.isEmpty() }
    }

}
