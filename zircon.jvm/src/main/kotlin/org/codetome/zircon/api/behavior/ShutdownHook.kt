package org.codetome.zircon.api.behavior

/**
 * Interface for listening to the JVM shutdown.
 */
interface ShutdownHook {

    /**
     * Adds a listener which will be notified when the JVM shuts down.
     * Note that this is only true for graceful termination.
     * If a `SIGKILL` is sent for example graceful termination is not possible.
     */
    fun onShutdown(listener: Runnable) {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                listener.run()
            }
        })
    }
}
