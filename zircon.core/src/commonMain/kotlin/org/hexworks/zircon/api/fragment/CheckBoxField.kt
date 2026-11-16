package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.Fragment

/**
 * A [CheckBox] with an optional description under its label.
 */
public interface CheckBoxField : Fragment {
    public val checkBox: CheckBox
    public var isSelected: Boolean
        get() = checkBox.isSelected
        set(value) {
            checkBox.isSelected = value
        }
}
