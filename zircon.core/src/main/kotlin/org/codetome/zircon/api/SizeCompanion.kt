package org.codetome.zircon.api

interface SizeCompanion {

    fun create(xLength: Int, yLength: Int) = SizeFactory.create(xLength, yLength)

    fun unknown() = create(Int.MAX_VALUE, Int.MAX_VALUE)

    fun defaultTerminalSize() = create(80, 24)

    fun zero() = create(0, 0)

    fun one() = create(1, 1)
}
