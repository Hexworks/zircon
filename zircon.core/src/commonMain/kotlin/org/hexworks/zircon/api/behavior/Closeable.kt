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
        get() = isClosed

    @Deprecated(
            message = "This will be replaced by closedValue in the next release",
            replaceWith = ReplaceWith("closedValue")
    )
    val isClosed: ObservableValue<Boolean>

    /**
     * Closes this [Closeable].
     */
    fun close()
}
