package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState

interface CheckBox : Component, TextHolder {

    val state: CheckBoxState
    val checkedValue: ObservableValue<Boolean>

    fun isChecked(): Boolean

}
