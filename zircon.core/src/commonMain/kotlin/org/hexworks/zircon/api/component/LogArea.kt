package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.builder.component.buildHeader
import org.hexworks.zircon.api.builder.component.buildListItem
import org.hexworks.zircon.api.builder.component.buildParagraph

/**
 * A [LogArea] provides the possibility to display a stream of messages. The messages are composed
 * of log elements, that can be for instance text or other Zircon components. New rows have to be
 * explicitly created by calling [LogArea.addNewRows].
 *
 * Currently, the log area scrolls automatically down. When later Zircon provides scrollbars,
 * this behavior will be configurable.
 */
interface LogArea : Component, Clearable {

    /**
     * Adds new empty rows to the log area.
     */
    fun addNewRows(numberOfRows: Int = 1)

    /**
     * Adds an empty line to the log.
     */
    fun addEmptyLine()

    /**
     * Adds the given components as inline elements (within the same row) to the log.
     */
    fun addInlineRow(
        row: List<Component>,
        applyTheme: Boolean = true
    )

    /**
     * Adds a row level component to the log
     */
    fun addRow(
        row: Component,
        applyTheme: Boolean = true
    )
}

fun LogArea.addParagraph(text: String) {
    addRow(buildParagraph { +text })
}

fun LogArea.addHeader(text: String) {
    addRow(buildHeader { +text })
}

fun LogArea.addListItem(text: String) {
    addRow(buildListItem { +text })
}
