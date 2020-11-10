package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState

interface RadioButton : Component, Selectable, TextOverride {

    val state: RadioButtonState
    val key: String

}
