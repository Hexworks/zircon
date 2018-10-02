package org.hexworks.zircon.api.component

import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState

interface CheckBox : Component {

    val text: String
    val state: CheckBoxState

    fun isChecked(): Boolean

}
