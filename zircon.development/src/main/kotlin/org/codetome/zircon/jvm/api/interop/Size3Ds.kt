package org.codetome.zircon.jvm.api.interop

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Size3D

object Size3Ds {

    @JvmStatic
    fun one() = Size3D.one()

    /**
     * Factory method for [Size3D].
     */
    @JvmStatic
    fun create(xLength: Int, yLength: Int, zLength: Int) = Size3D.create(
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
