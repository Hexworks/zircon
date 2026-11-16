package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.TextArea

/**
 * A labelled [TextArea] with a row of action [Button]s under it.
 */
public interface TextAreaField : Fragment {
    public val textArea: TextArea
    public val actions: List<Button>
    public var text: String
        get() = textArea.text
        set(value) {
            textArea.text = value
        }
}
