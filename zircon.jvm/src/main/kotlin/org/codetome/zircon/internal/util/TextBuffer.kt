package org.codetome.zircon.internal.util

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import java.util.*

class TextBuffer(text: String) {

    private val currentText = mutableListOf<StringBuilder>()

    init {
        setText(text)
    }

    fun getBoundingBoxSize() = Size.create(currentText.map { it.length }.max() ?: 0, currentText.size)

    fun getText() = currentText.joinToString(System.lineSeparator()) { it.toString() }

    fun getSize() = currentText.size

    fun getRow(row: Int) =
            if (row < currentText.size && row >= 0) {
                Optional.of(currentText[row])
            } else {
                Optional.empty()
            }

    fun getCharAt(position: Position) =
            if (position.y >= currentText.size || currentText[position.y].length <= position.x) {
                Optional.empty()
            } else {
                Optional.of(currentText[position.y][position.x])
            }

    fun getTextSection(position: Position, size: Size): List<String> {
        val fromRow = position.y
        val toRow = Math.min(currentText.size - 1, fromRow + size.yLength - 1)
        val fromCol = position.x
        return if (requestedRowsHaveNoIntersectionWithBuffer(fromRow, toRow)) {
            listOf()
        } else {
            var rowIdx = fromRow
            val list = mutableListOf<String>()
            do {
                val row = currentText[rowIdx]
                val toCol = Math.min(fromCol + size.xLength, row.length)
                list.add(if (requestedColsHaveNoIntersectionWithBuffer(fromCol, toCol, row)) {
                    ""
                } else {
                    row.substring(fromCol, toCol)
                })
                rowIdx++
            } while (rowIdx <= toRow)
            list
        }
    }

    fun setText(text: String) {
        currentText.clear()
        currentText.addAll(text.split(System.lineSeparator()).map {
            StringBuilder(it)
        })
    }

    fun deleteRowAt(rowIdx: Int) {
        currentText.removeAt(rowIdx)
    }

    fun addNewRowAt(rowIdx: Int) {
        currentText.add(rowIdx, StringBuilder())
    }

    private fun requestedRowsHaveNoIntersectionWithBuffer(fromRow: Int, toRow: Int) =
            toRow < fromRow || fromRow >= currentText.size

    private fun requestedColsHaveNoIntersectionWithBuffer(fromCol: Int, toCol: Int, row: StringBuilder) =
            toCol <= fromCol || fromCol >= row.length

}
