package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.event.ObservableValueChanged
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription

/**
 * A [Slider] is a [Component] that can be used to select
 * values from a range of numbers with a visual sliding mechanism.
 */
//! TODO: document this better
interface Slider : Component {

    val minValue: Int
    val maxValue: Int

    /**
     * Number of visible steps
     */
    val numberOfSteps: Int

    var currentValue: Int
    val currentValueProperty: Property<Int>

    var currentStep: Int
    val currentStepProperty: Property<Int>

    fun decrementCurrentValue()
    fun incrementCurrentValue()
    fun decrementCurrentStep()
    fun incrementCurrentStep()

    /**
     * Callback called when [currentValue] changes
     */
    fun onValueChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription

    /**
     * Callback called when [numberOfSteps] changes
     */
    fun onStepChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription
}
