package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.internal.component.impl.texteditor.EditorState
import org.hexworks.zircon.internal.component.impl.texteditor.Transformation
import org.hexworks.zircon.internal.component.impl.texteditor.lastIndex
import org.hexworks.zircon.internal.component.impl.texteditor.lineAt

object MoveCursorToLineStart : Transformation {

    override fun apply(editorState: EditorState): EditorState {
        val (lines, cursor) = editorState
        val (x, y) = cursor
        val line = editorState.lineAt(y)

        // if we're at the start we can't move
        return if (cursor.x == 0) {
            editorState
        } else {
            // otherwise we can
            editorState.copy(
                cursor = cursor.withX(0)
            )
        }
    }
}
