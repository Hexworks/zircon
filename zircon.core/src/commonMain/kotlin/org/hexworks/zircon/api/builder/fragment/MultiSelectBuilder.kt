package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.component.HBoxBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultMultiSelect
import kotlin.jvm.JvmStatic

/**
 * Builder for a [MultiSelect]
 */
class MultiSelectBuilder<T : Any>(
        val width: Int,
        val values: List<T>,
        private val boxBuilder: HBoxBuilder = HBoxBuilder().withSize(width, 1)
) : FragmentBuilder<MultiSelect<T>, MultiSelectBuilder<T>>,
        Builder<MultiSelect<T>> {

    init {
        require(values.isNotEmpty()) {
            "No values supplied."
        }
    }

    private val log = LoggerFactory.getLogger(this::class)

    private var callback: (oldValue: T, newValue: T) -> Unit = { _, _ -> log.warn("No callback defined for a MultiSelect input!") }
    private var centeredText = true
    private var toStringMethod: (T) -> String = Any::toString
    private var clickable: Boolean = false
    private var defaultSelected = values.first()

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

    fun withDefaultSelected(item: T) = also {
        require(values.contains(item)) {
            "$item is not present in ${values.joinToString()}"
        }
        this.defaultSelected = item
    }

    override fun withPosition(position: Position) = also {
        boxBuilder.withPosition(position)
    }

    override fun withPosition(x: Int, y: Int) = withPosition(Position.create(x, y))

    override fun build(): MultiSelect<T> =
            DefaultMultiSelect(
                    box = boxBuilder.build(),
                    initialValues = values,
                    callback = callback,
                    defaultSelected = defaultSelected,
                    centeredText = centeredText,
                    toStringMethod = toStringMethod,
                    clickable = clickable)

    override fun createCopy(): Builder<MultiSelect<T>> {
        return newBuilder(width, values)
                .withCallback(callback)
                .withCenteredText(centeredText)
                .withToStringMethod(toStringMethod)
                .withClickableLabel(clickable)
    }

    companion object {
        /**
         * Creates a builder to configure and build a [MultiSelect].
         *
         * @param width The width this [MultiSelect] can take in its parent component (height will be 1).
         * @param values The values to cycle through
         * @param N The type of the values
         */
        @JvmStatic
        fun <N : Any> newBuilder(width: Int, values: List<N>) = MultiSelectBuilder(width, values)
    }

}
