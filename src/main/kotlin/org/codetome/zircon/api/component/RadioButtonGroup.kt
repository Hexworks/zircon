package org.codetome.zircon.api.component

import org.codetome.zircon.internal.behavior.Scrollable
import java.util.*
import java.util.function.Consumer

interface RadioButtonGroup : Component, Scrollable {

    /**
     * Adds an option to this [RadioButtonGroup] and returns the
     * resulting [RadioButton] which was added.
     */
    fun addOption(key: String, text: String): RadioButton

    /**
     * Returns the currently selected item's key (if any).
     */
    fun getSelectedOption(): Optional<String>

    /**
     * Adds a callback to this [RadioButtonGroup] which will be called
     * when a change in selection happens.
     */
    fun onSelection(callback: Consumer<Selection>)

    /**
     * Clears the selected item in this [RadioButtonGroup].
     */
    fun clearSelection()

    interface Selection {
        fun getKey(): String
        fun getValue(): String
    }
}