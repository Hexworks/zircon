package org.codetome.zircon.api.component

import org.codetome.zircon.internal.behavior.Scrollable
import java.util.*
import java.util.function.Consumer

interface RadioButtonGroup : Component, Scrollable {

    fun addOption(key: String, text: String)

    fun getSelectedOption(): Optional<String>

    fun onSelection(callback: Consumer<Selection>)

    fun clearSelection()

    interface Selection {
        fun getKey() : String
        fun getValue() : String
    }

}