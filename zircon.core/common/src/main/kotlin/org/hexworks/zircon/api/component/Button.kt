package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue

interface Button : Component {

    val text: String
    val textProperty: Property<String>

    val isEnabled: Boolean
    val enabledValue: ObservableValue<Boolean>

    fun enable()

    fun disable()

}
