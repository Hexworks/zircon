package org.codetome.zircon.internal.util

actual object TimeFactory {
    actual fun getCurrentTimeMs() = System.currentTimeMillis()
}
