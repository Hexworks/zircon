package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState

interface CheckBox : Component {

    val text: String
    val state: CheckBoxState

    val textProperty: Property<String>
    val checkedValue: ObservableValue<Boolean>

    fun isChecked(): Boolean

}
