package org.hexworks.zircon.api.behavior

/**
 * Interface for listening to the shutdown of the environment (JVM, browser, etc).
 */
interface ShutdownHook {

    /**
     * Adds a listener which will be notified when the environment shuts down.
     * Note that this is only true for graceful termination.
     * If a `SIGKILL` is sent for example graceful termination is not possible.
     */
    fun onShutdown(listener: () -> Unit)

}
