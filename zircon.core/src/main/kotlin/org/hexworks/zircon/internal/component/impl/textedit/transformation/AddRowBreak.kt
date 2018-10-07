package org.hexworks.zircon.internal.component.impl.textedit.transformation

import org.hexworks.zircon.internal.component.impl.textedit.TextBufferTransformation
import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer

class AddRowBreak : TextBufferTransformation {

    override fun applyTo(buffer: EditableTextBuffer) {
        val cursor = buffer.cursor
        val cursorRow = buffer.getRow(cursor.rowIdx)
        val charsToKeep = cursor.colIdx

        // we take only the chars before our cursor
        val remainingRow = cursorRow.asSequence()
                .take(charsToKeep)
                .toMutableList()

        // we add the rest to a new row
        val newRow = cursorRow
                .asSequence()
                .drop(charsToKeep)
                .toMutableList()

        // then we replace / add the rows
        buffer.textBuffer[cursor.rowIdx] = remainingRow
        buffer.textBuffer.add(cursor.rowIdx + 1, newRow)
        buffer.cursor = cursor
                .withRelativeRow(1)
                .withRelativeColumn(-cursor.colIdx)
    }
}
