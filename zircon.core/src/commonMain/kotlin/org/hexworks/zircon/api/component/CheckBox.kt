package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxAlignment

interface CheckBox : Component, Selectable, TextHolder {

    val checkBoxState: CheckBoxState
    val labelAlignment: CheckBoxAlignment
}
