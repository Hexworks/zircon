package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultSelector

@ZirconDsl
public class SelectorBuilder<T : Any> : FragmentBuilder<Selector<T>> {

    override var position: Position = Position.ZERO
    public var width: Int? = null
    public var valuesProperty: ListProperty<T> = listOf<T>().toProperty()

    /**
     * Whether the text on the label should be centered.
     */
    public var centeredText: Boolean = true

    /**
     * The method to use for the label text if not ::toString
     */
    public var toStringMethod: (T) -> String = Any::toString

    /**
     * When set to true the center component, showing the text, will be an undecorated button that also invokes the
     * callback (else it is just a simple label).
     */
    public var clickableLabel: Boolean = false

    /**
     * The value that is selected at first. Setting it creates a fresh [selectedProperty];
     * set [selectedProperty] instead to share one with the rest of the app. When neither is
     * set the first value is selected.
     */
    public var defaultSelected: T
        get() = if (::selectedProperty.isInitialized) selectedProperty.value else error(NO_SELECTION_MESSAGE)
        set(value) {
            selectedProperty = value.toProperty()
        }

    public var valueList: List<T>
        get() = valuesProperty.value
        set(value) {
            valuesProperty = value.toProperty()
        }

    /**
     * The property that holds the selection. The selector reads its initial value from it
     * and writes every change into it, so binding it (or observing it) is how the selection
     * reaches the rest of the app.
     */
    public lateinit var selectedProperty: Property<T>

    override fun build(): Selector<T> {
        require(valuesProperty.isNotEmpty()) {
            "No values supplied for Selector."
        }
        // Without a selection (or a property to share) the first value is selected, as before.
        if (::selectedProperty.isInitialized.not()) {
            selectedProperty = valuesProperty.first().toProperty()
        }
        val selection = selectedProperty.value
        require(valuesProperty.contains(selection)) {
            "values doesn't contain the supplied value ($selection)"
        }
        width?.let {
            require(it >= 3) {
                "The supplied width ($width) is less than the minimum width of 3."
            }
        }
        return DefaultSelector(
            position = position,
            width = width ?: calculateWidth(),
            valuesProperty = valuesProperty,
            selectedProperty = selectedProperty,
            centeredText = centeredText,
            toStringMethod = toStringMethod,
            clickable = clickableLabel
        )
    }

    private fun calculateWidth() = valuesProperty.value.maxOf { toStringMethod(it).length } + 2

    private companion object {
        private const val NO_SELECTION_MESSAGE = "There is no selected value"
    }
}

/**
 * Creates a new [Selector] using the fragment builder DSL and returns it.
 */
public fun <T : Any> selector(
    init: SelectorBuilder<T>.() -> Unit
): Selector<T> = SelectorBuilder<T>().apply(init).build()

/**
 * Creates a new [Selector] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Selector].
 */
public fun <T : Any> AnyContainerBuilder.selector(
    init: SelectorBuilder<T>.() -> Unit
): Selector<T> = buildFragmentFor(this, SelectorBuilder(), init)
