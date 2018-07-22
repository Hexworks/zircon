package org.codetome.zircon.api.interop

import org.codetome.zircon.api.Size

object Sizes {

    @JvmStatic
    fun unknown() = Size.create(Int.MAX_VALUE, Int.MAX_VALUE)

    @JvmStatic
    fun defaultTerminalSize() = Size.create(80, 24)

    @JvmStatic
    fun zero() = Size.create(0, 0)

    @JvmStatic
    fun one() = Size.create(1, 1)

    /**
     * Factory method for creating [Size]s.
     */
    @JvmStatic
    fun create(xLength: Int, yLength: Int) = Size.create(xLength, yLength)
}
