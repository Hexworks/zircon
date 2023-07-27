package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.fragment.impl.DefaultSelector

@ZirconDsl
class SelectorBuilder<T : Any> : FragmentBuilder<Selector<T>> {

    override var position: Position = Position.zero()

    var width: Int? = null

    var valuesProperty: ListProperty<T> = listOf<T>().toProperty()

    /**
     * Whether the text on the label should be centered.
     */
    var centeredText: Boolean = true

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
    var defaultSelected: T? = null

    var valueList: List<T>
        get() = valuesProperty.value
        set(value) {
            valuesProperty = value.toProperty()
        }

    val selectedProperty: Property<T?> = null.toProperty()

    override fun build(): Selector<T> {
        defaultSelected?.let {
            require(valuesProperty.contains(it)) {
                "values doesn't contain the supplied value ($it)"
            }
        }
        width?.let {
            require(it >= 3) {
                "The supplied width ($width) is less than the minimum width of 3."
            }
        }
        require(valuesProperty.isNotEmpty()) {
            "No values supplied for Selector."
        }
        return DefaultSelector(
            position = position,
            width = width ?: calculateWidth(),
            valuesProperty = valuesProperty,
            selectedValue = (defaultSelected ?: valuesProperty.first()).toProperty(),
            centeredText = centeredText,
            toStringMethod = toStringMethod,
            clickable = clickableLabel
        )
    }

    private fun calculateWidth() = valuesProperty.value.maxOf { toStringMethod(it).length } + 2
}

/**
 * Creates a new [Selector] using the fragment builder DSL and returns it.
 */
fun <T : Any> buildSelector(
    init: SelectorBuilder<T>.() -> Unit
): Selector<T> = SelectorBuilder<T>().apply(init).build()

/**
 * Creates a new [Selector] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Selector].
 */
fun <S : Any> AnyContainerBuilder.selector(
    init: SelectorBuilder<S>.() -> Unit
): Selector<S> = buildFragmentFor(this, SelectorBuilder(), init)
