package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultMultiSelect

/**
 * Builder for a [MultiSelect]
 */
class MultiSelectBuilder<T : Any>(val width: Int, val values: List<T>) : FragmentBuilder<MultiSelect<T>, MultiSelectBuilder<T>> {

    private val log = LoggerFactory.getLogger(this::class)

    private var callback: (oldValue: T, newValue: T) -> Unit = { _, _ -> log.warn("No callback defined for a MultiSelect input!")}
    private var centeredText = true
    private var toStringMethod: (T) -> String = Any::toString
    private var clickable: Boolean = false

    /**
     * The callback to be used when the value changes. It gets the old and the new value as parameters.
     */
    fun withCallback(callbackFunction: (oldValue: T, newValue: T) -> Unit) = also {
        this.callback = callbackFunction
    }

    /**
     * Whether the text on the label should be centered.
     */
    fun withCenteredText(centerText: Boolean) = also {
        this.centeredText = centerText
    }

    /**
     * The method to use for the label text if not ::toString
     */
    fun withToStringMethod(function: (T) -> String) = also {
        this.toStringMethod = function
    }

    /**
     * When set to true the center component, showing the text, will be an undecorated button that also invokes the
     * callback (else it is just a simple label).
     */
    fun withClickableLabel(clickable: Boolean) = also {
        this.clickable = clickable
    }

    override fun build(): MultiSelect<T> = DefaultMultiSelect(width, values, callback, centeredText, toStringMethod, clickable)

    override fun createCopy(): Builder<MultiSelect<T>> {
        return newBuilder(width, values).
                withCallback(callback).
                withCenteredText(centeredText).
                withToStringMethod(toStringMethod).
                withClickableLabel(clickable)
    }

    companion object {
        /**
         * Creates a builder to configure and build a [MultiSelect].
         *
         * @param width The width this [MultiSelect] can take in its parent component (height will be 1).
         * @param values The values to cycle through
         * @param N The type of the values
         */
        fun <N: Any> newBuilder(width: Int, values: List<N>) = MultiSelectBuilder(width, values)
    }
}
