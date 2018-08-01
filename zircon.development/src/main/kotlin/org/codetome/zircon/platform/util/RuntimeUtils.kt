package org.codetome.zircon.platform.util

object RuntimeUtils {

    fun onShutdown(listener: () -> Unit) {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                listener.invoke()
            }
        })
    }

}
