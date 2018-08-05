package org.codetome.zircon.jvm.api.interop

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Position3D

object Position3Ds {

    /**
     * Position3d(0, 0, 0)
     */
    @JvmStatic
    fun defaultPosition() = Position3D.defaultPosition()

    /**
     * Factory method for [Position3D].
     */
    @JvmStatic
    fun create(x: Int, y: Int, z: Int) = Position3D.create(x = x, y = y, z = z)

    /**
     * Creates a new [Position3D] from a [Position].
     * If `y` is not supplied it defaults to `0` (ground level).
     */
    @JvmStatic
    @JvmOverloads
    fun from2DPosition(position: Position, z: Int = 0) = create(
            x = position.x,
            y = position.y,
            z = z)
}
