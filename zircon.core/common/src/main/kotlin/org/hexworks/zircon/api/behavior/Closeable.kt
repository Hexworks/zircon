package org.hexworks.zircon.api.behavior

/**
 * Represents an object which support the notion of closing.
 * After closing an object it no longer responds to operations
 * and its resources are freed.
 */
interface Closeable {

    /**
     * Closes this [Closeable].
     */
    fun close()
}
