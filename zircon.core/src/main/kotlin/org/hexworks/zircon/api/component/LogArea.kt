package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.component.LogAreaBuilder

/**
 * A [LogArea] provides the possibility to display messages.
 * The messages are composed of log elements, which can be
 * for instance text or other Zircon components
 * New rows have to be explicitly created by calling
 * [LogArea.addNewRows]

 * Currently the log area scrolls automatically down. When later
 * Zircon provides scrollbars, this behavior will be then configurable
 */
interface LogArea : Component {

    fun addHeader(text: String, withNewLine: Boolean = true)

    fun addParagraph(paragraph: String, withNewLine: Boolean = true, withTypingEffect: Boolean = false)

    fun addListItem(item: String)

    fun addInlineText(text: String)

    fun addInlineComponent(component: Component)

    fun commitInlineElements()

    /**
     * Adds new rows
     */
    fun addNewRows(numberOfRows: Int = 1)

    /**
     * Clears the complete log
     */
    fun clear()
}






