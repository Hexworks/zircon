package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription

interface ScrollBar : Component {

    /**
     * Maximum value of the [ScrollBar]
     */
    val maxValue: Int
    /**
     * Minimum value of the [ScrollBar]
     */
    val minValue: Int

    /**
     * Number of visible steps
     */
    val numberOfSteps: Int

    /**
     * Size of bar in steps
     */
    val barSizeInSteps: Int

    /**
     * Number of visible items
     */
    val itemsShownAtOnce: Int

    /**
     * Current low value with respect to the maxValue
     */
    var currentValue: Int

    /**
     * Bindable, current low value
     */
    val currentValueProperty: Property<Int>

    /**
     * Current low step with respect to the number of steps
     */
    var currentStep: Int

    /**
     * Bindable, current low step
     */
    val currentStepProperty: Property<Int>

    fun incrementStep()
    fun decrementStep()
    fun incrementValues()
    fun decrementValues()

    /**
     * Function to specify a new maximum value and resize the scrollbar accordingly
     */
    fun resizeScrollBar(maxValue: Int)

    /**
     * Callback called when low value changes
     */
    fun onValueChange(fn: (ChangeEvent<Int>) -> Unit): Subscription

    /**
     * Callback called when low step changes
     */
    fun onStepChange(fn: (ChangeEvent<Int>) -> Unit): Subscription
}
