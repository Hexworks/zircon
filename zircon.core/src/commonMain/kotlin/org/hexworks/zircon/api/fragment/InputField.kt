package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.Input

/**
 * A labelled [Input], optionally with an icon or a [Button] at its right end inside the frame.
 */
public interface InputField : Fragment {
    public val input: Input
    public val button: Button?
    public var text: String
        get() = input.text
        set(value) {
            input.text = value
        }
}
