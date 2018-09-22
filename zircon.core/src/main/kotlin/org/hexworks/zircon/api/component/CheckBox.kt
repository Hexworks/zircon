package org.hexworks.zircon.api.component

import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState

interface CheckBox : Component {

    fun text(): String

    fun isChecked(): Boolean

    fun state(): CheckBoxState
}
