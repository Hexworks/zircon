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

    var textWrap: TextWrap

    fun addText(text: String, modifiers: Set<Modifier>? = null)

    fun addHyperLink(linkText: String, linkId: String)

    fun addNewRows(numberOfRows: Int = 1)

    fun getLogElementBuffer(): LogElementBuffer

    fun clear()

}






