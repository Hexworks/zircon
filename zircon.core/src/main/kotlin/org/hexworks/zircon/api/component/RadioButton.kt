package org.hexworks.zircon.api.component

import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState

interface RadioButton : Component {

    val text: String
    val state: RadioButtonState

    fun isSelected(): Boolean


}
