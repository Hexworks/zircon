package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.event.ObservableValueChanged
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription

/**
 * A [Slider] is a [Component] that can be used to select
 * values from a range of numbers with a visual sliding mechanism.
 */
interface Slider : Component {

    /**
     * Maximum value of the [Slider]
     */
    val maxValue: Int
    /**
     * Minimum value of the [Slider]
     */
    val minValue: Int

    /**
     * Number of visible steps
     */
    val numberOfSteps: Int

    /**
     * Current value with respect to the maxValue
     */
    var currentValue: Int

    /**
     * Bindable, current value
     */
    val currentValueProperty: Property<Int>

    /**
     * Current step with respect to the number of steps
     */
    var currentStep: Int

    /**
     * Bindable, current step
     */
    val currentStepProperty: Property<Int>

    fun decrementCurrentValue()
    fun incrementCurrentValue()
    fun decrementCurrentStep()
    fun incrementCurrentStep()

    /**
     * Callback called when value changes
     */
    fun onValueChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription

    /**
     * Callback called when step changes
     */
    fun onStepChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription
}
