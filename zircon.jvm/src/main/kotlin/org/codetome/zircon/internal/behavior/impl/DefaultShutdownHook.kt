package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.internal.behavior.ShutdownHook
import org.codetome.zircon.util.Runnable

class DefaultShutdownHook : ShutdownHook {

    override fun onShutdown(listener: () -> Unit) {
        onShutdown(object : Runnable {
            override fun run() {
                listener.invoke()
            }
        })
    }

    override fun onShutdown(listener: Runnable) {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                listener.run()
            }
        })
    }
}
