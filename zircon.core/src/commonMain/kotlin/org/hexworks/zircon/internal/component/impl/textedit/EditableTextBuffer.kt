package org.hexworks.zircon.internal.component.impl.textedit

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.textedit.cursor.Cursor

interface EditableTextBuffer {

    var cursor: Cursor
    val textBuffer: MutableList<MutableList<Char>>
    val lastRowIdx: Int
        get() = textBuffer.size - 1
    val text: String
        get() = textBuffer.joinToString("\n") { it.joinToString("") }
    val rowCount: Int
        get() = textBuffer.size
    val boundingBoxSize: Size
        get() = Size.create(
            width = textBuffer.asSequence()
                .map { it.size }
                .maxOrNull() ?: 0,
            height = textBuffer.size
        )


    fun applyTransformation(transformation: TextBufferTransformation): EditableTextBuffer

    fun getLastColumnIdxForRow(rowIdx: Int): Int = textBuffer[rowIdx].size - 1

    fun getColumnCount(rowIdx: Int): Int = textBuffer[rowIdx].size

    fun getRow(rowIdx: Int): MutableList<Char> = textBuffer[rowIdx]

    fun deleteRow(rowIdx: Int): MutableList<Char> = textBuffer.removeAt(rowIdx)

    fun getCharAtOrNull(position: Position): Char? =
        if (position.y >= textBuffer.size || textBuffer[position.y].size <= position.x) {
            null
        } else {
            textBuffer[position.y][position.x]
        }

    companion object {

        fun create(text: String = "", cursor: Cursor = Cursor()): EditableTextBuffer =
            DefaultEditableTextBuffer(text, cursor)
    }
}
