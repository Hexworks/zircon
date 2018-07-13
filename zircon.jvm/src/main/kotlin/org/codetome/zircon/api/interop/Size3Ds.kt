package org.codetome.zircon.api.interop

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.game.Size3D

object Size3Ds {

    @JvmField
    val ONE = create(1, 1, 1)

    /**
     * Factory method for [Size3D].
     */
    @JvmStatic
    fun create(xLength: Int, yLength: Int, zLength: Int) = Size3D.of(
            xLength = xLength,
            yLength = yLength,
            zLength = zLength)

    /**
     * Creates a new [Size3D] from a [Size].
     * If `zLength` is not supplied, it defaults to `0`.
     */
    @JvmStatic
    @JvmOverloads
    fun from2DSize(size: Size, zLength: Int = 0) = create(
            xLength = size.xLength,
            yLength = size.yLength,
            zLength = zLength)
}
