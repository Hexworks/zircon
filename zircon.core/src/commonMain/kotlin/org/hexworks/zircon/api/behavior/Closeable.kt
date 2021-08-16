package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.value.ObservableValue

/**
 * Represents an object which support the notion of closing.
 * After closing an object its resources are freed.
 */
interface Closeable {

    val closed: Boolean
        get() = closedValue.value
    val closedValue: ObservableValue<Boolean>

    /**
     * Closes this [Closeable].
     */
    fun close()
}
