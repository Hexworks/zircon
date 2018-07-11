package org.codetome.zircon.internal

import org.codetome.zircon.api.Size

actual object SizeFactory {
    actual fun create(xLength: Int, yLength: Int): Size = DefaultSize(xLength, yLength)
}
