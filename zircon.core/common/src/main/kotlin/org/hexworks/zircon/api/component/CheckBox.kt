package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState

interface CheckBox : Component, TextHolder, Selectable {

    val state: CheckBoxState

    val isEnabled: Boolean
    val enabledProperty: Property<Boolean>

    /**
     * Enables interaction with this [Button].
     */
    fun enable()

    /**
     * Disables interaction with this [Button].
     */
    fun disable()
}
