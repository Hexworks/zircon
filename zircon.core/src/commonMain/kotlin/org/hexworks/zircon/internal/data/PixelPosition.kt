package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.base.BasePosition

/**
 * Represents a position that has an `x` and a `y` pixel coordinate.
 */
data class PixelPosition(
    override val x: Int,
    override val y: Int
) : BasePosition() {

    companion object {

        fun create(x: Int, y: Int): Position = PixelPosition(x, y)
    }
}
