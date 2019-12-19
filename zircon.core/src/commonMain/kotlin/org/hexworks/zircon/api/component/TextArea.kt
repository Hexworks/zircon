package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer

/**
 * A [TextArea] is an editable text box.
 */
interface TextArea : Component, Scrollable {

    var text: String

    fun textBuffer(): EditableTextBuffer

}
