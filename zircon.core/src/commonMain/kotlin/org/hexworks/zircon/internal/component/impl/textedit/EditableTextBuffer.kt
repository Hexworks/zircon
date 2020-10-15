package org.hexworks.zircon.internal.component.impl.textedit

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.textedit.cursor.Cursor
import org.hexworks.zircon.platform.util.SystemUtils

interface EditableTextBuffer {

    var cursor: Cursor

    val textBuffer: MutableList<MutableList<Char>>

    fun applyTransformation(transformation: TextBufferTransformation): EditableTextBuffer

    fun getLastRowIdx(): Int = textBuffer.size - 1

    fun getLastColumnIdxForRow(rowIdx: Int): Int = textBuffer[rowIdx].size - 1

    fun getColumnCount(rowIdx: Int): Int = textBuffer[rowIdx].size

    fun getRow(rowIdx: Int): MutableList<Char> = textBuffer[rowIdx]

    fun deleteRow(rowIdx: Int): MutableList<Char> = textBuffer.removeAt(rowIdx)

    fun getBoundingBoxSize(): Size = Size.create(
            width = textBuffer.asSequence()
                    .map { it.size }
                    .maxOrNull() ?: 0,
            height = textBuffer.size)

    fun getText(): String = textBuffer.joinToString(SystemUtils.getLineSeparator()) { it.joinToString("") }

    fun getSize() = textBuffer.size

    fun getCharAt(position: Position): Maybe<Char> =
            if (position.y >= textBuffer.size || textBuffer[position.y].size <= position.x) {
                Maybe.empty()
            } else {
                Maybe.of(textBuffer[position.y][position.x])
            }

    fun rowCount(): Int = textBuffer.size

    companion object {

        fun create(text: String = "", cursor: Cursor = Cursor()): EditableTextBuffer =
                DefaultEditableTextBuffer(text, cursor)
    }
}
