package org.codetome.zircon.api.interop

import org.codetome.zircon.api.Size

object Sizes {

    @JvmField
    val UNKNOWN = Size.unknown()

    @JvmField
    val DEFAULT_TERMINAL_SIZE = Size.defaultTerminalSize()

    @JvmField
    val ZERO = Size.zero()

    @JvmField
    val ONE = Size.one()

    /**
     * Factory method for [Size].
     */
    @JvmStatic
    fun create(xLength: Int, yLength: Int) = Size.create(xLength, yLength)
}
