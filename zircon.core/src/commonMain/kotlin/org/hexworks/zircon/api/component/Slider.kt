package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.event.ObservableValueChanged
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription

/**
 * A [Slider] is a [Component] that can be used to select
 * values from a range of numbers with a visual sliding mechanism.
 */
//! TODO: document this better
public interface Slider : Component {

    public val minValue: Int
    public val maxValue: Int

    /**
     * Number of visible steps
     */
    public val numberOfSteps: Int

    public var currentValue: Int
    public val currentValueProperty: Property<Int>

    public var currentStep: Int
    public val currentStepProperty: Property<Int>

    public fun decrementCurrentValue()
    public fun incrementCurrentValue()
    public fun decrementCurrentStep()
    public fun incrementCurrentStep()

    /**
     * Callback called when [currentValue] changes
     */
    public fun onValueChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription

    /**
     * Callback called when [numberOfSteps] changes
     */
    public fun onStepChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription
}
