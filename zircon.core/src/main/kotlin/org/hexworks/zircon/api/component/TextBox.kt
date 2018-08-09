package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Scrollable

interface TextBox : Component, Scrollable {

    /**
     * Returns the text of this [TextBox].
     */
    fun getText(): String

    /**
     * Sets the text of this [TextBox].
     * @return `true` if the box was changed `false` if the old text was the same as the new
     */
    fun setText(text: String): Boolean

    fun disable()

    fun enable()

}
