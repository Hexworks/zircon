package org.hexworks.zircon.internal.component.impl.textedit

import org.hexworks.zircon.internal.component.impl.textedit.cursor.Cursor

interface EditableTextBuffer {

    var cursor: Cursor

    val textBuffer: MutableList<MutableList<Char>>

    fun applyTransformation(transformation: TextBufferTransformation): EditableTextBuffer

    fun getLastRowIdx() = textBuffer.size - 1

    fun getLastColumnIdxForRow(rowIdx: Int) = textBuffer[rowIdx].size - 1

    fun getRowCount() = textBuffer.size

    fun getColumnCount(rowIdx: Int) = textBuffer[rowIdx].size

    fun getRow(rowIdx: Int) = textBuffer[rowIdx]

    fun getRowsInRange(range: IntRange) = range.toList().map { textBuffer[it] }

    fun deleteRow(rowIdx: Int) = textBuffer.removeAt(rowIdx)

}
