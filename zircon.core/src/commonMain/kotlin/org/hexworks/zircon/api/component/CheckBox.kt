package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxAlignment
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState

/**
 * A [CheckBox] is a [Selectable] that represents its [Selectable.isSelected]
 * state with a check box.
 */
interface CheckBox : Component, Selectable, TextOverride {
    /**
     * Contains the current state of the [CheckBox]
     * @see CheckBoxState
     */
    val checkBoxState: CheckBoxState

    /**
     * Contains the [CheckBoxAlignment].
     * @see CheckBoxAlignment
     */
    val labelAlignment: CheckBoxAlignment
}
