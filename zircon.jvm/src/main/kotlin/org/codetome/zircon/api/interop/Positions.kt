package org.codetome.zircon.api.interop

import org.codetome.zircon.api.Position

object Positions {

    /**
     * Constant for the top-left corner (0x0)
     */
    @JvmField
    val TOP_LEFT_CORNER = Position.TOP_LEFT_CORNER

    /**
     * Constant for the 1x1 position (one offset in both directions from top-left)
     */
    @JvmField
    val OFFSET_1x1 = Position.OFFSET_1x1

    /**
     * This position can be considered as the default
     */
    @JvmField
    val DEFAULT_POSITION = Position.TOP_LEFT_CORNER

    /**
     * Used in place of a possible null value. Means that the position is unknown (cursor for example)
     */
    @JvmField
    val UNKNOWN = Position.UNKNOWN

    /**
     * Factory method for creating a [Position].
     */
    @JvmStatic
    fun create(x: Int, y: Int) = Position.of(x, y)
}
