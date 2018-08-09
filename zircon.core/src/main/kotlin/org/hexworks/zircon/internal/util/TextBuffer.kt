package org.hexworks.zircon.internal.util

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.util.Math
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.platform.util.SystemUtils

class TextBuffer(text: String) {

    private val currentText = mutableListOf<StringBuilder>()

    init {
        setText(text)
    }

    fun getBoundingBoxSize() = Size.create(currentText.map { it.length }.max() ?: 0, currentText.size)

    fun getText() = currentText.joinToString(SystemUtils.getLineSeparator()) { it.toString() }

    fun getSize() = currentText.size

    fun getRow(row: Int): Maybe<StringBuilder> =
            if (row < currentText.size && row >= 0) {
                Maybe.of(currentText[row])
            } else {
                Maybe.empty()
            }

    fun getCharAt(position: Position) =
            if (position.y >= currentText.size || currentText[position.y].length <= position.x) {
                Maybe.empty()
            } else {
                Maybe.of(currentText[position.y][position.x])
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
        currentText.addAll(text.split(SystemUtils.getLineSeparator()).map {
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
