package org.codetome.zircon.platform.util

expect object RuntimeUtils {

    fun onShutdown(listener: () -> Unit)
}
