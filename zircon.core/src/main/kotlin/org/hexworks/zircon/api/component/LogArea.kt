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
 */
interface LogArea : Component, Scrollable {


    /**
     * Mode of Text Wrap. TextWrap.Wrap is currently not supported
     */
    var textWrapMode: TextWrap


    /**
     * Adds a new text element in the current row
     */
    fun addText(text: String, modifiers: Set<Modifier>? = null)

    /**
     * Adds a new hyper link in the current row
     */
    fun addHyperLink(linkText: String, linkId: String)

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






