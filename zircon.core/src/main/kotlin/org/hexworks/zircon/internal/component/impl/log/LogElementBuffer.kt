package org.hexworks.zircon.internal.component.impl.log

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.util.Math
import org.hexworks.zircon.api.util.Maybe

data class LogElementRow(val logElements: ArrayList<LogElement>)
{
    fun size() = logElements.size
}

class LogElementBuffer {

    private val logElementRows = mutableListOf<LogElementRow>()

    init {
        addNewRow()
    }

    fun currentLogElementRow() = logElementRows.last()

    fun getBoundingBoxSize() = Size.create(logElementRows.flatMap { it.logElements }.asSequence().map { it.length() }.max() ?: 0, logElementRows.size)

    //fun getText() = currentLogElement.joinToString(SystemUtils.getLineSeparator()) { it.toString() }

    fun addLogElement(logElement: LogElement) {
        currentLogElementRow().add(logElement)
    }

    fun getSize() = logElementRows.size

//    fun getRow(row: Int): Maybe<StringBuilder> =
//            if (row < logElementRows.size && row >= 0) {
//                Maybe.of(logElementRows[row])
//            } else {
//                Maybe.empty()
//            }

    fun getCharAt(position: Position) =
            if (position.y >= logElementRows.size || logElementRows[position.y].size() <= position.x) {
                Maybe.empty()
            } else {
                Maybe.of(logElementRows[position.y][position.x])
            }

//    fun getTextSection(position: Position, size: Size): List<String> {
//        val fromRow = position.y
//        val toRow = Math.min(logElementRows.size - 1, fromRow + size.yLength - 1)
//        val fromCol = position.x
//        return if (requestedRowsHaveNoIntersectionWithBuffer(fromRow, toRow)) {
//            listOf()
//        } else {
//            var rowIdx = fromRow
//            val list = mutableListOf<String>()
//            do {
//                val row = logElementRows[rowIdx]
//                val toCol = Math.min(fromCol + size.xLength, row.length)
//                list.add(if (requestedColsHaveNoIntersectionWithBuffer(fromCol, toCol, row)) {
//                    ""
//                } else {
//                    row.substring(fromCol, toCol)
//                })
//                rowIdx++
//            } while (rowIdx <= toRow)
//            list
//        }
//    }


    fun deleteRowAt(rowIdx: Int) {
        logElementRows.removeAt(rowIdx)
    }

    fun addNewRow() {
        logElementRows.add(logElementRows.size - 1, ArrayList())
    }

    private fun requestedRowsHaveNoIntersectionWithBuffer(fromRow: Int, toRow: Int) =
            toRow < fromRow || fromRow >= logElementRows.size

    private fun requestedColsHaveNoIntersectionWithBuffer(fromCol: Int, toCol: Int, row: StringBuilder) =
            toCol <= fromCol || fromCol >= row.length

}



