package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.base.BasePosition

/**
 * Represents an (x, y) coordinate on a grid. The actual
 * position on the screen can be determined by multiplying
 * the x and the y values with the width and the height of
 * a [org.hexworks.zircon.api.resource.TilesetResource] which
 * will be used for drawing on the screen.
 */
data class GridPosition(
    override val x: Int,
    override val y: Int
) : BasePosition() {

    override fun toString() = "(x=$x,y=$y)"

    companion object {

        /**
         * Creates a new [GridPosition].
         */
        fun create(x: Int, y: Int): Position = GridPosition(x, y)
    }
}
