package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.builder.component.ParagraphBuilder

/**
 * A [LogArea] provides the possibility to display a stream of messages. The messages are composed
 * of log elements, that can be for instance text or other Zircon components. New rows have to be
 * explicitly created by calling [LogArea.addNewRows].
 *
 * Currently the log area scrolls automatically down. When later
 * Zircon provides scrollbars, this behavior will be configurable
 */
interface LogArea : Component, Clearable {

    /**
     * Adds a header
     */
    fun addHeader(text: String, withNewLine: Boolean = true)

    /**
     * Adds a paragraph
     */
    fun addParagraph(paragraph: String, withNewLine: Boolean = true, withTypingEffectSpeedInMs: Long = 0)

    /**
     * Adds a paragraph by providing a builder
     * Hint: Use this function if you want to style a paragraph within a log area
     */
    fun addParagraph(paragraphBuilder: ParagraphBuilder, withNewLine: Boolean = true)

    /**
     * Adds an list item
     */
    fun addListItem(item: String)

    /**
     * Adds an inline component, for instance a Button
     */
    fun addInlineText(text: String)

    /**
     * Adds an inline component, for instance a Button
     */
    fun addInlineComponent(component: Component)

    /**
     * Commits (adds to the underlying container) all inline elements.
     */
    fun commitInlineElements()

    /**
     * Adds new rows
     */
    fun addNewRows(numberOfRows: Int = 1)
}
