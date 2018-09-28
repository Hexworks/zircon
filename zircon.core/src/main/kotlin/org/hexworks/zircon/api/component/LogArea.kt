package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.internal.component.impl.log.LogElementBuffer

/**
 * A [LogArea] provides the possibility to display messages.
 */
interface LogArea : Component, Scrollable {

    fun addText(text: String)

    fun addHyperLink(linkText: String, linkId: String)

    fun addNewRow()

    fun getLogElements(): LogElementBuffer



}
