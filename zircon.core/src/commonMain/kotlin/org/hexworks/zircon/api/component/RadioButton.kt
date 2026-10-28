package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride

/**
 * A radio button is a [Selectable] that can only be selected once.
 * It can be combined with other [RadioButton]s using a [RadioButtonGroup] that will
 * add an exclusive selection behavior: only one [RadioButton] can be in the [isSelected]
 * state in a [RadioButtonGroup].
 */
public interface RadioButton : Component, Selectable, TextOverride {
    //! TODO: use enums?
    public val key: String
    public val state: RadioButtonState

    public enum class RadioButtonState {
        PRESSED,
        SELECTED,
        NOT_SELECTED
    }
}
