package org.hexworks.zircon.internal.component.impl.log

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

data class LogElementRow(val yPosition: Int, val logElements: MutableList<LogElement> = mutableListOf()) {
    fun size() = logElements.size
}

class LogElementBuffer {
    private val logElementRows = mutableListOf<LogElementRow>()
    fun getLogElementRows() = logElementRows.toList()

    init {
        addNewRows(1)
    }

    fun currentLogElementRow() = logElementRows.last()
    fun getAllLogElements() = getLogElementRows().flatMap { it.logElements }
    fun getLogElementContainingPosition(position: Position) =
            getAllLogElements().asSequence().filter { it.renderedPositionArea != null }.firstOrNull { it.renderedPositionArea!!.containsPosition(position) }


    fun getBoundingBoxSize() = Size.create(logElementRows.flatMap { it.logElements }.asSequence().map { it.length() }.max()
            ?: 0, logElementRows.size)


    fun addLogElement(logElement: LogElement) {
        currentLogElementRow().logElements.add(logElement)
    }


    fun addNewRows(numberOfRows: Int) {
        (1..numberOfRows).forEach { _ ->
            logElementRows.add(LogElementRow(logElementRows.size))
        }
    }

    fun clear() {
        logElementRows.clear()
    }


}



