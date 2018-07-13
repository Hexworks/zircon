package org.codetome.zircon.internal.multiplatform.util

actual object RuntimeUtils {

    actual fun onShutdown(listener: () -> Unit) {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                listener.invoke()
            }
        })
    }

}
