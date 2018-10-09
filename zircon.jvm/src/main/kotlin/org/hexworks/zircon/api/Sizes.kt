package org.hexworks.zircon.api

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.impl.Size3D

object Sizes {

    /**
     * Represents a [Size] which is an unknown (can be used instead of a `null` value).
     */
    @JvmStatic
    fun unknown() = Size.unknown()

    /**
     * The default grid size is (80 * 24)
     */
    @JvmStatic
    fun defaultTerminalSize() = Size.defaultGridSize()

    /**
     * Size of (0 * 0).
     */
    @JvmStatic
    fun zero() = Size.zero()

    /**
     * Size of (1 * 1).
     */
    @JvmStatic
    fun one() = Size.one()

    /**
     * Factory method for creating [Size]s.
     */
    @JvmStatic
    fun create(xLength: Int, yLength: Int) = Size.create(xLength, yLength)

    /**
     * Size of (1 * 1 * 1).
     */
    @JvmStatic
    fun one3D() = Size3D.one()

    /**
     * Factory method for [Size3D].
     */
    @JvmStatic
    fun create3DSize(xLength: Int, yLength: Int, zLength: Int) = Size3D.create(
            xLength = xLength,
            yLength = yLength,
            zLength = zLength)

    /**
     * Creates a new [Size3D] from a [Size].
     * If `zLength` is not supplied, it defaults to `0`.
     */
    @JvmStatic
    @JvmOverloads
    fun from2DTo3D(size: Size, zLength: Int = 0) = create3DSize(
            xLength = size.width,
            yLength = size.height,
            zLength = zLength)
}
