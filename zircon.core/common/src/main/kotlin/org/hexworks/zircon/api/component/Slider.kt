package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.ChangeListener
import org.hexworks.zircon.api.behavior.Disablable

interface Slider: Component, Disablable {

    /**
     * Range (0..value) of the [Slider]
     */
    val range: Int

    /**
     * Number of visible steps
     */
    val numberOfSteps: Int

    /**
     * Current value with respect to the range
     */
    var currentValue: Int

    /**
     * Bindable, current value
     */
    val currentValueProperty: Property<Int>

    /**
     * Callback called when value changes
     */
    fun onChange(fn: ChangeListener<Int>): Subscription
}