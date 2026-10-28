package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.data.base.BasePosition

/**
 * Represents a position that has an `x` and a `y` pixel coordinate.
 */
public data class PixelPosition(
    override val x: Int,
    override val y: Int
) : BasePosition() {

    public companion object {

        public fun create(x: Int, y: Int): Position = PixelPosition(x, y)
    }
}
