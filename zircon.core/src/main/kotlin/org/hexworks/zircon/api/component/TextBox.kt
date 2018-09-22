package org.hexworks.zircon.api.component

/**
 * A TextBox is a component which contains non-editable
 * text. It differs from [Label] in that a [TextBox]
 * can be multi-line.
 */
interface TextBox : Component {

    fun getText(): String

}
