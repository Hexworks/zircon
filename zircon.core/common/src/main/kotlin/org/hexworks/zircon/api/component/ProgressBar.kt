package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer
import org.hexworks.zircon.internal.util.TextBuffer

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
     * Current progress with respect to the range
     */
    var progress: Double


    /**
     * Bindable, current progress
     */
    val progressProperty: Property<Double>

}
