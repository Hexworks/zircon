package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Maybe

interface RadioButtonGroup : Component, Scrollable {

    /**
     * Adds an option to this [RadioButtonGroup] and returns the
     * resulting [RadioButton] which was added.
     */
    fun addOption(key: String, text: String): RadioButton

    /**
     * Returns the currently selected item's key (if any).
     */
    fun getSelectedOption(): Maybe<String>

    /**
     * Adds a callback to this [RadioButtonGroup] which will be called
     * when a change in selection happens.
     */
    fun onSelection(callback: Consumer<Selection>)

    /**
     * Clears the selected item in this [RadioButtonGroup].
     * @return `true` if the selection changed `false` if not
     */
    fun clearSelection(): Boolean

    interface Selection {
        fun getKey(): String
        fun getValue(): String
    }
}
