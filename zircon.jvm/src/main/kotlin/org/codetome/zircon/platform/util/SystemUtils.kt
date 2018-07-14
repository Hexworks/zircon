package org.codetome.zircon.platform.util

actual object SystemUtils {

    actual fun getCurrentTimeMs() = System.currentTimeMillis()

    actual fun getLineSeparator() = System.lineSeparator()
}
