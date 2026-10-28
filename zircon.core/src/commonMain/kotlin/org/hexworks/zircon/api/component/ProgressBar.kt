package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property

/**
 * A [ProgressBar] visualizes the progress of an operation.
 */
public interface ProgressBar : Component {

    /**
     * Range (`0 ..value`) of the [ProgressBar]
     */
    public val range: Int

    /**
     * Number of visible steps
     */
    public val numberOfSteps: Int

    /**
     * Indicates if the current progress is displayed on the progress bar
     */
    public val displayPercentValueOfProgress: Boolean

    /**
     * Current progress with respect to the maxValue
     */
    public var progress: Double
    public val progressProperty: Property<Double>

    public fun increment(): Unit = incrementBy(1)
    public fun decrement(): Unit = decrementBy(1)

    public fun incrementBy(value: Int) {
        progressProperty.value += value
    }

    public fun decrementBy(value: Int) {
        progressProperty.value -= value
    }


}
