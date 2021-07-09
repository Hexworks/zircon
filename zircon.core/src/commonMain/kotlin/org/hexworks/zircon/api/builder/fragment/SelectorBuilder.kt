package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.component.HBoxBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultSelector
import kotlin.jvm.JvmStatic

/**
 * Builder for a [Selector].
 */
open class SelectorBuilder<T : Any>(
    val width: Int,
    val values: List<T>,
    private val boxBuilder: HBoxBuilder = HBoxBuilder().withPreferredSize(width, 1)
) : FragmentBuilder<Selector<T>, SelectorBuilder<T>>,
    Builder<Selector<T>> {

    init {
        require(values.isNotEmpty()) {
            "No values supplied."
        }
        require(width >= 3) {
            "The supplied width ($width) is less than the minimum width of 3."
        }
    }

    private var centeredText = true
    private var toStringMethod: (T) -> String = Any::toString
    private var clickable: Boolean = false
    private var defaultSelected = values.first()

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
            "The provided default selected item ($item) is not present in values (${values.joinToString()})."
        }
        this.defaultSelected = item
    }

    final override fun withPosition(position: Position) = also {
        boxBuilder.withPosition(position)
    }

    final override fun withPosition(x: Int, y: Int) = withPosition(Position.create(x, y))

    override fun build(): Selector<T> {
        return DefaultSelector(
            parent = boxBuilder.build(),
            initialValues = values,
            defaultSelected = defaultSelected,
            centeredText = centeredText,
            toStringMethod = toStringMethod,
            clickable = clickable
        )
    }

    override fun createCopy(): Builder<Selector<T>> {
        return newBuilder(width, values)
            .withCenteredText(centeredText)
            .withToStringMethod(toStringMethod)
            .withClickableLabel(clickable)
            .withPosition(boxBuilder.position)
    }

    companion object {
        /**
         * Creates a builder to configure and build a [Selector].
         *
         * @param width The width this [Selector] can take in its parent component (height will be 1).
         * @param values The values to cycle through
         * @param N The type of the values
         */
        @JvmStatic
        fun <N : Any> newBuilder(width: Int, values: List<N>) = SelectorBuilder(width, values)
    }

}
