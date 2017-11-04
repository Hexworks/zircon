package org.codetome.zircon.api.component

import org.codetome.zircon.internal.behavior.Scrollable

interface TextBox : Component, Scrollable {

    fun getText(): String

    /**
     * Sets the text of this [TextBox].
     * @return `true` if the box was changed `false` if the old text was the same as the new
     */
    fun setText(text: String): Boolean

    fun disable()

    fun enable()

}