package org.codetome.zircon.internal.multiplatform.util

expect object RuntimeUtils {

    fun onShutdown(listener: () -> Unit)
}
