package org.hexworks.zircon.internal.component.impl.textedit.transformation

import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer
import org.hexworks.zircon.internal.component.impl.textedit.TextBufferTransformation

class InsertCharacter(private val character: Char) : TextBufferTransformation {

    override fun applyTo(buffer: EditableTextBuffer) {
        val cursor = buffer.cursor
        // we insert the character at the cursor position
        // remember that the cursor always points **after** the current character
        buffer.getRow(cursor.rowIdx)
            .add(cursor.colIdx, character)

        buffer.cursor = cursor.withRelativeColumn(1)
    }
}
