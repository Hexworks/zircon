package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.event.ObservableValueChanged
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription


interface NumberInput : Component {

    var text: String

    /**
     * Current value with respect to the maxValue
     */
    var currentValue: Int

    /**
     * Boundable, current value
     */
    val currentValueProperty: Property<Int>

    fun incrementCurrentValue()

    fun decrementCurrentValue()

    /**
     * Callback called when value changes
     */
    fun onChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription
}
