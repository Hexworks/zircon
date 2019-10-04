package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState

interface RadioButton : Component, TextHolder, Selectable {

    val radioButtonState: RadioButtonState

}
