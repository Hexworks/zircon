package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockSide.*
import org.hexworks.zircon.api.data.Tile

/**
 * Base class which implements common functionality from
 * [Block].
 */
abstract class BlockBase<T : Tile> : Block<T> {

    override val top: T
        get() = fetchSide(TOP)

    override val bottom: T
        get() = fetchSide(BOTTOM)

    override val front: T
        get() = fetchSide(FRONT)

    override val back: T
        get() = fetchSide(BACK)

    override val left: T
        get() = fetchSide(LEFT)

    override val right: T
        get() = fetchSide(RIGHT)

    override fun isEmpty(): Boolean {
        return layers.isEmpty() and
                listOf(top, bottom, front, back, left, right).all { it.isEmpty() }
    }

}
