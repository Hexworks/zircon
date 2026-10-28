package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.event.ObservableValueChanged
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription


public interface NumberInput : Component {

    //! TODO: why this?
    public var text: String

    /**
     * Current value with respect to the maxValue
     */
    public var currentValue: Int
    public val currentValueProperty: Property<Int>

    public fun incrementCurrentValue()

    public fun decrementCurrentValue()

    /**
     * Callback called when value changes
     */
    public fun onChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription
}
