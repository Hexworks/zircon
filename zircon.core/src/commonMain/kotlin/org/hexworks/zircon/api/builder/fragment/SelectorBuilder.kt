package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.component.HBoxBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultSelector
import kotlin.jvm.JvmStatic

// TODO: remove open once subclasses are removed
open class SelectorBuilder<T : Any>(
    val width: Int,
    private val valuesProperty: ListProperty<T>,
    private val boxBuilder: HBoxBuilder = HBoxBuilder().withPreferredSize(width, 1)
) : FragmentBuilder<Selector<T>, SelectorBuilder<T>>, Builder<Selector<T>> {

    init {
        require(valuesProperty.isNotEmpty()) {
            "No values supplied."
        }
        require(width >= 3) {
            "The supplied width ($width) is less than the minimum width of 3."
        }
    }

    /**
     * Whether the text on the label should be centered.
     */
    var centeredText = true

    /**
     * The method to use for the label text if not ::toString
     */
    var toStringMethod: (T) -> String = Any::toString

    /**
     * When set to true the center component, showing the text, will be an undecorated button that also invokes the
     * callback (else it is just a simple label).
     */
    var clickableLabel: Boolean = false

    /**
     * The value that should be selected by default.
     */
    var defaultSelected = valuesProperty.first()
        set(value) {
            require(valuesProperty.contains(value)) {
                "values doesn't contain the supplied value ($value)"
            }
        }

    val values: List<T>
        get() = valuesProperty.value

    fun withCenteredText(centerText: Boolean) = also {
        this.centeredText = centerText
    }

    fun withToStringMethod(function: (T) -> String) = also {
        this.toStringMethod = function
    }

    fun withClickableLabel(clickable: Boolean) = also {
        this.clickableLabel = clickable
    }

    fun withDefaultSelected(item: T) = also {
        this.defaultSelected = item
    }

    override fun build(): Selector<T> {
        return DefaultSelector(
            parent = boxBuilder.build(),
            valuesProperty = valuesProperty,
            defaultSelected = defaultSelected,
            centeredText = centeredText,
            toStringMethod = toStringMethod,
            clickable = clickableLabel
        )
    }

    override fun createCopy(): Builder<Selector<T>> {
        return newBuilder(width, valuesProperty)
            .withCenteredText(centeredText)
            .withToStringMethod(toStringMethod)
            .withClickableLabel(clickableLabel)
            .withPosition(boxBuilder.position)
    }

    final override fun withPosition(position: Position) = also {
        boxBuilder.withPosition(position)
    }

    final override fun withPosition(x: Int, y: Int) = withPosition(Position.create(x, y))


    companion object {

        /**
         * Creates a builder to configure and build a [Selector].
         *
         * @param width The width this [Selector] can take in its parent component (height will be 1).
         * @param values The values to cycle through
         * @param N The type of the values
         */
        @JvmStatic
        fun <N : Any> newBuilder(width: Int, values: List<N>) = SelectorBuilder(width, values.toProperty())

        /**
         * Creates a builder to configure and build a [Selector].
         *
         * @param width The width this [Selector] can take in its parent component (height will be 1).
         * @param values The values to cycle through
         * @param N The type of the values
         */
        @JvmStatic
        fun <N : Any> newBuilder(width: Int, values: ListProperty<N>) = SelectorBuilder(width, values)
    }

}
