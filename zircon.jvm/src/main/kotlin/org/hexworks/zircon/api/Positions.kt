package org.hexworks.zircon.api

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D

object Positions {

    /**
     * Constant for the top-left corner (0x0)
     */
    @JvmStatic
    fun topLeftCorner() = Position.topLeftCorner()

    /**
     * Constant for the 1x1 position (one offset in both directions from top-left)
     */
    @JvmStatic
    fun offset1x1() = Position.offset1x1()

    /**
     * This position can be considered as the default
     */
    @JvmStatic
    fun defaultPosition() = Position.defaultPosition()

    /**
     * Used in place of a possible null value. Means that the position is unknown (cursor for example)
     */
    @JvmStatic
    fun unknown() = Position.unknown()

    /**
     * Factory method for creating a [Position].
     */
    @JvmStatic
    fun create(x: Int, y: Int) = Position.create(x, y)

    /**
     * Position3d(0, 0, 0)
     */
    @JvmStatic
    fun default3DPosition() = Position3D.defaultPosition()

    /**
     * Factory method for [Position3D].
     */
    @JvmStatic
    fun create3DPosition(x: Int, y: Int, z: Int) = Position3D.create(x = x, y = y, z = z)

    /**
     * Creates a new [Position3D] from a [Position].
     * If `y` is not supplied it defaults to `0` (ground level).
     */
    @JvmStatic
    @JvmOverloads
    fun from2DTo3D(position: Position, z: Int = 0) = create3DPosition(
            x = position.x,
            y = position.y,
            z = z)

}
