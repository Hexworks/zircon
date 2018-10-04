package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.event.Event
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.component.impl.log.LogElementBuffer
import org.hexworks.zircon.internal.event.ZirconEvent

/**
 * A [LogArea] provides the possibility to display messages.
 * The messages are composed of log elements, which can be
 * for instance text or other Zircon components
 * New rows have to be explicitly created by calling
 * [LogArea.addNewRows]

 * Currently the log area scrolls automatically down. When later
 * Zircon provides scrollbars, this behavior will be then configurable
 */
interface LogArea : Container, Scrollable {


    /**
     * If true, words of text elements get wrapped, components are also
     * inserted at a new row if they do not fit
     */
    var wrapLogElements: Boolean

    val delayInMsForTypewriterEffect: Int


    /**
     * Adds a new text element in the current row
     */
    fun addTextElement(text: String, modifiers: Set<Modifier>? = null)

    /**
     * Adds a new component in the current Row
     * You do not need to specify a position of the component,
     * since it is auto-arranged in the text flow
     * Only components with a height of 1 are supported
     */
    fun addComponentElement(component: Component)

    /**
     * Adds new rows
     */
    fun addNewRows(numberOfRows: Int = 1)

    /**
     * Clears the complete log
     */
    fun clear()


    fun getLogElementBuffer(): LogElementBuffer


}






