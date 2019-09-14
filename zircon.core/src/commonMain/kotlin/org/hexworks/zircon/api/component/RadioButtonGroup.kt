package org.hexworks.zircon.api.component

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.cobalt.events.api.Event
import org.hexworks.zircon.api.behavior.Scrollable

interface RadioButtonGroup : Component, Scrollable {

    /**
     * Adds an option to this [RadioButtonGroup] and returns the
     * resulting [RadioButton] which was added.
     */
    fun addOption(key: String, text: String): RadioButton

    /**
     * Removes the given [key] from this [RadioButtonGroup].
     * Has no effect if the given [key] is not present.
     */
    fun removeOption(key: String)

    /**
     * Returns the currently selected item's key (if any).
     */
    fun fetchSelectedOption(): Maybe<String>

    /**
     * Adds a callback to this [RadioButtonGroup] which will be called
     * when a change in selection happens.
     */
    fun onSelection(callback: Consumer<Selection>)

    /**
     * Clears the selected item in this [RadioButtonGroup].
     */
    fun clearSelection()

    interface Selection : Event {
        override val key: String
        val value: String
    }
}
