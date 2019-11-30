package org.hexworks.zircon.api.component

import org.hexworks.cobalt.datatypes.Maybe

import org.hexworks.cobalt.events.api.Event
import org.hexworks.zircon.api.behavior.Scrollable

// refactor this to be a logical `Group`, not a `Component`
interface RadioButtonGroup : Component, Scrollable {

    /**
     * Adds an option to this [RadioButtonGroup] and returns the
     * resulting [RadioButton] which was added.
     */
    // TODO: refactor this to take a `RadioButton` instead
    fun addOption(key: String, text: String): RadioButton

    /**
     * Removes the given [key] from this [RadioButtonGroup].
     * Has no effect if the given [key] is not present.
     */
    // TODO: refactor this to take a `RadioButton` instead
    fun removeOption(key: String)

    /**
     * Returns the currently selected item's key (if any).
     */
    fun fetchSelectedOption(): Maybe<String>

    /**
     * Adds a callback to this [RadioButtonGroup] which will be called
     * when a change in selection happens.
     */
    fun onSelection(fn: (Selection) -> Unit)

    /**
     * Clears the selected item in this [RadioButtonGroup].
     */
    fun clearSelection()

    interface Selection : Event {
        override val key: String
        val value: String
    }
}
