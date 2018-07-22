package org.codetome.zircon.api.interop

import org.codetome.zircon.api.Position

object Positions {

    /**
     * Constant for the top-left corner (0x0)
     */
    @JvmStatic
    fun topLeftCorner() = create(0, 0)

    /**
     * Constant for the 1x1 position (one offset in both directions from top-left)
     */
    @JvmStatic
    fun offset1x1() = create(1, 1)

    /**
     * This position can be considered as the default
     */
    @JvmStatic
    fun defaultPosition() = topLeftCorner()

    /**
     * Used in place of a possible null value. Means that the position is unknown (cursor for example)
     */
    @JvmStatic
    fun unknown() = create(Int.MAX_VALUE, Int.MAX_VALUE)

    /**
     * Factory method for creating a [Position].
     */
    @JvmStatic
    fun create(x: Int, y: Int) = Position.create(x, y)

    internal fun generateCacheKey(x: Int, y: Int) = "Position-$x-$y"
}
