package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultSelector
import kotlin.jvm.JvmStatic

class SelectorBuilder<T : Any> private constructor(
    var width: Int? = null,
    var position: Position = Position.zero(),
    var valuesProperty: ListProperty<T> = listOf<T>().toProperty(),
    /**
     * Whether the text on the label should be centered.
     */
    var centeredText: Boolean = true,
    /**
     * The method to use for the label text if not ::toString
     */
    var toStringMethod: (T) -> String = Any::toString,
    /**
     * When set to true the center component, showing the text, will be an undecorated button that also invokes the
     * callback (else it is just a simple label).
     */
    var clickableLabel: Boolean = false,
    /**
     * The value that should be selected by default.
     */
    var defaultSelected: T? = null,
) : FragmentBuilder<Selector<T>, SelectorBuilder<T>>, Builder<Selector<T>> {

    var valueList: List<T>
        get() = valuesProperty.value
        set(value) {
            valuesProperty = value.toProperty()
        }

    val selectedProperty: Property<T?> = null.toProperty()

    fun withWidth(width: Int) = also {
        this.width = width
    }

    fun withValueList(values: List<T>) = also {
        this.valuesProperty = values.toProperty()
    }

    fun withValues(valuesProperty: ListProperty<T>) = also {
        this.valuesProperty = valuesProperty
    }

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

    final override fun withPosition(position: Position) = also {
        this.position = position
    }

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

    override fun createCopy() = SelectorBuilder(
        width = width,
        position = position,
        valuesProperty = valuesProperty.value.toProperty(),
        centeredText = centeredText,
        toStringMethod = toStringMethod,
        clickableLabel = clickableLabel,
        defaultSelected = defaultSelected
    )

    private fun calculateWidth() = valuesProperty.value.maxOf { toStringMethod(it).length } + 2

    companion object {

        @JvmStatic
        fun <T : Any> newBuilder(): SelectorBuilder<T> = SelectorBuilder()
    }

}
