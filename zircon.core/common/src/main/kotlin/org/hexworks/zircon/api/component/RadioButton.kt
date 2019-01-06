package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.util.Runnable
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState

interface RadioButton : Component, TextHolder {

    val state: RadioButtonState

    fun isSelected(): Boolean
    val selectedValue: ObservableValue<Boolean>

    fun onSelected(runnable: Runnable) : Subscription

    fun select()

}
