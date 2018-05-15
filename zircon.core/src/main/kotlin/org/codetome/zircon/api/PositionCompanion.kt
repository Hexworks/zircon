package org.codetome.zircon.api

interface PositionCompanion {

    fun create(x: Int, y: Int) = PositionFactory.create(x, y)

    /**
     * Constant for the top-left corner (0x0)
     */
    fun topLeftCorner() = create(0, 0)

    /**
     * Constant for the 1x1 position (one offset in both directions from top-left)
     */
    fun offset1x1() = create(1, 1)

    /**
     * This position can be considered as the default
     */
    fun defaultPosition() = topLeftCorner()

    /**
     * Used in place of a possible null value. Means that the position is unknown (cursor for example)
     */
    fun unknown() = create(Int.MAX_VALUE, Int.MAX_VALUE)

    fun generateCacheKey(x: Int, y: Int) = "Position-$x-$y"
}
