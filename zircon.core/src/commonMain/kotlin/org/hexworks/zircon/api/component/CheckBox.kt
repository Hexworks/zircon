package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState

interface CheckBox : Component, TextHolder, Selectable, Disablable {

    val checkBoxState: CheckBoxState

}
