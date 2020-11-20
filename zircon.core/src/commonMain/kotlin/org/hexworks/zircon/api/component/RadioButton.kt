package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState

/**
 * A radio button is a [Selectable] that can only be selected once.
 * It can be combined with other [RadioButton]s using a [RadioButtonGroup] that will
 * add an exclusive selection behavior: only one [RadioButton] can be in the [isSelected]
 * state in a [RadioButtonGroup].
 */
interface RadioButton : Component, Selectable, TextOverride {
    val key: String
    val state: RadioButtonState
}
