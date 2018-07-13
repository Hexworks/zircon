package org.codetome.zircon.internal.multiplatform.util

actual object SystemUtils {

    actual fun getCurrentTimeMs() = System.currentTimeMillis()

    actual fun getLineSeparator() = System.lineSeparator()
}
