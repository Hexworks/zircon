package org.hexworks.zircon.internal.component.impl.log

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.data.Size

data class LogElementRow(val yPosition: Int, val logElements: MutableList<LogElement> = mutableListOf()) {
    fun size() = logElements.size
    fun getComponentsOfRow() = logElements.filter { it is LogComponentElement }
            .map { (it as LogComponentElement).component }
}

class LogElementBuffer(val visibleSize: Size, private val logArea: LogArea, private val numberOfBufferedRows: Int = 100) {
    private val logElementRows = mutableListOf<LogElementRow>()
    fun getLogElementRows() = logElementRows.toList()

    init {
        addNewRows(1)
    }

    fun currentLogElementRow() = logElementRows.last()
    fun getAllLogElements() = getLogElementRows().flatMap { it.logElements }


    fun addLogElement(logElement: LogElement) {
        logElement.logElementRow = currentLogElementRow()
        currentLogElementRow().logElements.add(logElement)
    }


    fun addNewRows(numberOfRows: Int) {
        (1..numberOfRows).forEach { _ ->
            if (logElementRows.size >= numberOfBufferedRows) {
                logElementRows[0].getComponentsOfRow().forEach { logArea.removeComponent(it) }
                logElementRows.removeAt(0)
            }
            logElementRows.add(LogElementRow(logElementRows.size))
        }
    }

    fun clear() {
        logElementRows.clear()
        addNewRows(1)
    }

    fun clearLogRenderPositions() {
        getAllLogElements().forEach { it.renderedPositionArea = null }
    }


}



