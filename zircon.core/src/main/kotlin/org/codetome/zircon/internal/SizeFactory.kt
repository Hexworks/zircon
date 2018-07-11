package org.codetome.zircon.internal

import org.codetome.zircon.api.Size

expect object SizeFactory {

    fun create(xLength: Int, yLength: Int): Size
}
