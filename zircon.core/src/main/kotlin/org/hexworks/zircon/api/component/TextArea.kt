package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.internal.util.TextBuffer

/**
 * A [TextArea] is an editable [TextBox].
 */
interface TextArea : Component, Scrollable {

    /**
     * Returns the text of this [TextArea].
     */
    fun text(): String

    /**
     * Sets the text of this [TextArea].
     * @return `true` if the box was changed `false` if the old text was the same as the new
     */
    fun setText(text: String): Boolean

    fun textBuffer(): TextBuffer

    fun enable()

    fun disable()


}
