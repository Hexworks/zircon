package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.api.Size
import org.codetome.zircon.internal.DefaultSize

actual object SizeFactory {
    actual fun create(xLength: Int, yLength: Int): Size = DefaultSize(xLength, yLength)
}
