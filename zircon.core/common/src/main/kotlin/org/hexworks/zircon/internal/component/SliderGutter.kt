package org.hexworks.zircon.internal.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.component.Component

interface SliderGutter : Component, Disablable {
    /**
     * Current value with respect to the range
     */
    var currentValue: Int
    /**
     * Number of visible steps
     */
    val numberOfSteps: Int
    /**
     * Bindable, current value
     */
    val currentValueProperty: Property<Int>
}