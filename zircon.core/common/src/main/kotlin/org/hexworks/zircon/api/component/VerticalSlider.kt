package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.ChangeListener
import org.hexworks.zircon.api.behavior.Disablable

//TODO: Remove this. It's the same as Slider
interface VerticalSlider: Component, Disablable {

    /**
     * Range (0..value) of the [VerticalSlider]
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
     * Bindable, current progress
     */
    val currentValueProperty: Property<Int>

    fun onChange(fn: ChangeListener<Int>): Subscription
}