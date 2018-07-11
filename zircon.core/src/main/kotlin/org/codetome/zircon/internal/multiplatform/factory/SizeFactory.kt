package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.api.Size

expect object SizeFactory {

    fun create(xLength: Int, yLength: Int): Size
}
