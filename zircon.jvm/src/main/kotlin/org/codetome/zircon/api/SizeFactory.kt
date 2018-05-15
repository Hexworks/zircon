package org.codetome.zircon.api

actual object SizeFactory {
    actual fun create(xLength: Int, yLength: Int): Size {
        return JvmSize(xLength, yLength)
    }
}
