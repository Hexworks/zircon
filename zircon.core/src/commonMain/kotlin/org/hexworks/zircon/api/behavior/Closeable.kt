package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.value.ObservableValue

/**
 * Represents an object that supports the notion of closing.
 * After closing an object, its resources are freed, and it is no longer usable.
 */
public interface Closeable {

    public val closed: Boolean
        get() = closedProperty.value
    public val closedProperty: ObservableValue<Boolean>

    /**
     * Closes this [Closeable].
     */
    public fun close()
}
