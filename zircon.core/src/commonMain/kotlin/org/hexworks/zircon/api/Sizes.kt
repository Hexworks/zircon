package org.hexworks.zircon.api

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

object Sizes {

    /**
     * Represents a [Size] which is an unknown (can be used instead of a `null` value).
     */
    @JvmStatic
    @Deprecated("Use Size.unknown instead", replaceWith = ReplaceWith(
            "Size.unknown()", "org.hexworks.zircon.api.data.Size"))
    fun unknown() = Size.unknown()

    /**
     * The default grid size is (80 * 24)
     */
    @JvmStatic
    @Deprecated("Use Size.defaultGridSize instead", replaceWith = ReplaceWith(
            "Size.defaultGridSize()", "org.hexworks.zircon.api.data.Size"))
    fun defaultGridSize() = Size.defaultGridSize()

    /**
     * Size of (0 * 0).
     */
    @JvmStatic
    @Deprecated("Use Size.zero instead", replaceWith = ReplaceWith(
            "Size.zero()", "org.hexworks.zircon.api.data.Size"))
    fun zero() = Size.zero()

    /**
     * Size of (1 * 1).
     */
    @JvmStatic
    @Deprecated("Use Size.one instead", replaceWith = ReplaceWith(
            "Size.one()", "org.hexworks.zircon.api.data.Size"))
    fun one() = Size.one()

    /**
     * Factory method for creating [Size]s.
     */
    @JvmStatic
    @Deprecated("Use Size.create instead", replaceWith = ReplaceWith(
            "Size.create(xLength, yLength)", "org.hexworks.zircon.api.data.Size"))
    fun create(xLength: Int, yLength: Int) = Size.create(xLength, yLength)

    /**
     * Size of (1 * 1 * 1).
     */
    @JvmStatic
    @Deprecated("Use Size.one3D instead", replaceWith = ReplaceWith(
            "Size.one3D()", "org.hexworks.zircon.api.data.Size"))
    fun one3D() = Size3D.one()

    /**
     * Factory method for [Size3D].
     */
    @JvmStatic
    @Deprecated("Use Size3D.create instead", replaceWith = ReplaceWith(
            "Size3D.create(xLength, yLength, zLength)", "org.hexworks.zircon.api.data.Size3D"))
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
    @Deprecated("Use Size.to3DSize instead", replaceWith = ReplaceWith(
            "size.to3DSize(zLength)", "org.hexworks.zircon.api.data.Size"))
    fun from2DTo3D(size: Size, zLength: Int = 0) = Size3D.create(xLength = size.width,
            yLength = size.height,
            zLength = zLength)
}
