package org.hexworks.zircon.api.component

import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState

interface RadioButton : Component {

    fun text(): String

    fun isSelected(): Boolean

    fun state(): RadioButtonState

}
