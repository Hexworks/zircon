package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.Fragment

/**
 * A check box ([BinarySelector]) with an optional description under its label.
 */
public interface CheckBoxField : Fragment {
    public val checkBox: BinarySelector
    public var isSelected: Boolean
        get() = checkBox.isSelected
        set(value) {
            checkBox.isSelected = value
        }
}
