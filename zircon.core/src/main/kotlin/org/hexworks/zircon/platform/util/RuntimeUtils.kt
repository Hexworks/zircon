package org.hexworks.zircon.platform.util

expect object RuntimeUtils {

    fun onShutdown(listener: () -> Unit)

}
