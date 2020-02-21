package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property

/**
 * A [ProgressBar] visualizes the progress of an operation.
 */
interface ProgressBar : Component {

    /**
     * Range (0..value) of the [ProgressBar]
     */
    val range: Int

    /**
     * Number of visible steps
     */
    val numberOfSteps: Int

    /**
     * Indicates if the current progress is displayed next to the progress bar
     */
    val displayPercentValueOfProgress: Boolean

    /**
     * Current progress with respect to the maxValue
     */
    var progress: Double

    /**
     * Bindable, current progress
     */
    val progressProperty: Property<Double>

    fun increment() = incrementBy(1)

    fun decrement() = decrementBy(1)

    fun incrementBy(value: Int) {
        progressProperty.value = progressProperty.value + value
    }

    fun decrementBy(value: Int) {
        progressProperty.value = progressProperty.value - value
    }


}
