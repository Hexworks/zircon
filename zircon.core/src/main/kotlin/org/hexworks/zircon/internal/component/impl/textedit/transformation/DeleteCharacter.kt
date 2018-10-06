package org.hexworks.zircon.internal.component.impl.textedit.transformation

import org.hexworks.zircon.internal.component.impl.textedit.TextBufferTransformation
import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer

class DeleteCharacter : TextBufferTransformation {

    override fun applyTo(buffer: EditableTextBuffer) {
        val cursor = buffer.cursor
        val cursorRow = buffer.getRow(cursor.rowIdx)

        when {
            cursor.isAtTheStartOfTheFirstRow() -> {
                // we do nothing, we can't delete anything
                // later we might support the 'Del' button?
            }
            cursor.isAtTheStartOfARow() -> {
                // the cursor is at the start of **a** row (not the first)
                // so we delete the row and add it to the previous row
                val prevRow = buffer.getRow(cursor.rowIdx - 1)
                val prevRowLength = prevRow.size

                // we add the next row to the previous row
                prevRow.addAll(buffer.deleteRow(cursor.rowIdx))

                // we fix the cursor
                buffer.cursor = cursor.withRelativeRow(-1).withRelativeColumn(prevRowLength)
            }
            else -> {
                // in case of a simple delete
                val deleteIdx = cursor.colIdx - 1

                // we delete the character at the cursor position
                // note that the cursor is always **after** the character, thus the - 1 above
                cursorRow.removeAt(deleteIdx)

                // we move all cursors which are at **or** after our cursor to the left
                buffer.cursor = cursor.withRelativeColumn(-1)
            }
        }
    }

}
