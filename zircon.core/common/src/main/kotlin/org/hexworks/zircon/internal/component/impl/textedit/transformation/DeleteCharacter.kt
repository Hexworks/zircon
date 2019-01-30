package org.hexworks.zircon.internal.component.impl.textedit.transformation

import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer
import org.hexworks.zircon.internal.component.impl.textedit.TextBufferTransformation
import org.hexworks.zircon.internal.component.impl.textedit.cursor.Cursor
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter.DeleteKind.BACKSPACE
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter.DeleteKind.DEL

class DeleteCharacter(private val deleteKind: DeleteKind) : TextBufferTransformation {

    override fun applyTo(buffer: EditableTextBuffer) {
        val cursor = buffer.cursor
        val cursorRow = buffer.getRow(cursor.rowIdx)

        when {
            cursor.isAtTheStartOfTheFirstRow() -> {
                if (deleteKind == DEL) {
                    deleteFirstCharOfRow(buffer, cursor)
                }
            }
            cursor.isAtTheStartOfARow() -> {
                when (deleteKind) {
                    DEL -> {
                        deleteFirstCharOfRow(buffer, cursor)
                    }
                    BACKSPACE -> {
                        // the cursor is at the start of **a** row (not the first)
                        // so we delete the row and add it to the previous row
                        val prevRow = buffer.getRow(cursor.rowIdx - 1)
                        val prevRowLength = prevRow.size

                        // we add the next row to the previous row
                        prevRow.addAll(buffer.deleteRow(cursor.rowIdx))

                        // we fix the cursor
                        buffer.cursor = cursor.withRelativeRow(-1).withRelativeColumn(prevRowLength)
                    }
                }
            }
            else -> {
                when (deleteKind) {
                    DEL -> {
                        val deleteIdx = cursor.colIdx
                        if (buffer.getLastColumnIdxForRow(cursor.rowIdx) >= deleteIdx) {
                            cursorRow.removeAt(deleteIdx)
                        }
                    }
                    BACKSPACE -> {
                        val deleteIdx = cursor.colIdx - 1
                        // we delete the character at the cursor position
                        // note that the cursor is always **after** the character, thus the - 1 above
                        cursorRow.removeAt(deleteIdx)
                        buffer.cursor = cursor.withRelativeColumn(-1)
                    }
                }
            }
        }
    }

    private fun deleteFirstCharOfRow(buffer: EditableTextBuffer, cursor: Cursor) {
        val cursorRow = buffer.getRow(cursor.rowIdx)
        if (cursorRow.isEmpty() && buffer.rowCount() > 1) {
            buffer.deleteRow(cursor.rowIdx)
        } else if (cursorRow.isNotEmpty()) {
            cursorRow.removeAt(0)
        }
    }

    enum class DeleteKind {
        DEL,
        BACKSPACE
    }
}
