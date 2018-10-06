package org.hexworks.zircon.internal.component.impl.textedit.transformation

import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer
import org.hexworks.zircon.internal.component.impl.textedit.TextBufferTransformation
import org.hexworks.zircon.internal.component.impl.textedit.cursor.MovementDirection

class MoveCursor(private val direction: MovementDirection) : TextBufferTransformation {

    override fun applyTo(buffer: EditableTextBuffer) {
        buffer.cursor = buffer.cursor.move(direction, buffer)
    }
}
