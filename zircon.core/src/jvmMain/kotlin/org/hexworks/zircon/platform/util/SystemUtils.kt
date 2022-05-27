package org.hexworks.zircon.platform.util

actual object SystemUtils {

    actual fun getCurrentTimeMs() = System.currentTimeMillis()

    actual fun getLineSeparator() = System.lineSeparator()

    actual fun measureNanoTime(block: () -> Unit): Long {
        return kotlin.system.measureNanoTime(block)
    }
}
