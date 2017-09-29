package org.codetome.zircon.api.component

import org.codetome.zircon.internal.behavior.Scrollable

interface TextBox : Component, Scrollable {

    fun getText(): String

    fun setText(text: String)

}