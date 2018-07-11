package org.codetome.zircon.internal.multiplatform.factory

actual object TimeFactory {
    actual fun getCurrentTimeMs() = System.currentTimeMillis()
}
