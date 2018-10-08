package org.hexworks.zircon.api.data.impl

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.base.BasePosition

/**
 * Represents a position which has an x and an y pixel
 * coordinate.
 */
data class PixelPosition(override val x: Int,
                         override val y: Int) : BasePosition() {

    companion object {

        fun create(x: Int, y: Int): Position = PixelPosition(x, y)
    }
}
