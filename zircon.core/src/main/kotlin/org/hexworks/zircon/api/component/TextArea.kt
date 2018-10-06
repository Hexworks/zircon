package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer
import org.hexworks.zircon.internal.util.TextBuffer

/**
 * A [TextArea] is an editable [TextBox].
 */
interface TextArea : Component, Scrollable {

    var text: String

    fun textBuffer(): EditableTextBuffer

}
