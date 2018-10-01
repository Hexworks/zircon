package org.hexworks.zircon.internal.component.impl.log

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

data class LogElementRow(val yPosition: Int, val logElements: MutableList<LogElement> = mutableListOf()) {
    fun size() = logElements.size
}

class LogElementBuffer(val visibleSize: Size) {
    private val logElementRows = mutableListOf<LogElementRow>()
    fun getLogElementRows() = logElementRows.toList()

    init {
        addNewRows(1)
    }

    fun currentLogElementRow() = logElementRows.last()
    fun getAllLogElements() = getLogElementRows().flatMap { it.logElements }
    fun getLogElementContainingPosition(position: Position) =
            getAllLogElements().asSequence().filter { it.renderedPositionArea != null }
                    .firstOrNull { it.renderedPositionArea!!.containsPosition(position) }


    fun addLogElement(logElement: LogElement) {
        currentLogElementRow().logElements.add(logElement)
    }


    fun addNewRows(numberOfRows: Int) {
        (1..numberOfRows).forEach {
            logElementRows.add(LogElementRow(logElementRows.size))
        }
    }

    fun clear() {
        logElementRows.clear()
    }

    fun clearLogRenderPositions() {
        getAllLogElements().forEach { it.renderedPositionArea = null }
    }


}



