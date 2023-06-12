package org.hexworks.zircon.internal.util

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.math.min

@Suppress("unused")
class TextBuffer(text: String) {

    private val currentText = mutableListOf<StringBuilder>()

    init {
        setText(text)
    }

    fun getBoundingBoxSize() = Size.create(currentText.maxOfOrNull { it.length } ?: 0, currentText.size)

    fun getText() = currentText.joinToString("\n") { it.toString() }

    fun getSize() = currentText.size

    fun getRowOrNull(row: Int): StringBuilder? =
        if (row < currentText.size && row >= 0) {
            currentText[row]
        } else {
            null
        }

    fun getCharAtOrNull(position: Position): Char? =
        if (position.y >= currentText.size || currentText[position.y].length <= position.x) {
            null
        } else {
            currentText[position.y][position.x]
        }

    fun getTextSection(position: Position, size: Size): List<String> {
        val fromRow = position.y
        val toRow = min(currentText.size - 1, fromRow + size.height - 1)
        val fromCol = position.x
        return if (requestedRowsHaveNoIntersectionWithBuffer(fromRow, toRow)) {
            listOf()
        } else {
            var rowIdx = fromRow
            val list = mutableListOf<String>()
            do {
                val row = currentText[rowIdx]
                val toCol = min(fromCol + size.width, row.length)
                list.add(
                    if (requestedColsHaveNoIntersectionWithBuffer(fromCol, toCol, row)) {
                        ""
                    } else {
                        row.substring(fromCol, toCol)
                    }
                )
                rowIdx++
            } while (rowIdx <= toRow)
            list
        }
    }

    fun setText(text: String) {
        currentText.clear()
        currentText.addAll(text.split('\n').map {
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
