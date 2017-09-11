package org.codetome.zircon.api.component

import org.codetome.zircon.internal.behavior.Scrollable
import java.util.*

interface RadioButtonGroup : Component, Scrollable {

    fun addOption(key: String, text: String)

    fun removeOption(key: String)

    fun getSelectedOption(): Optional<String>

}