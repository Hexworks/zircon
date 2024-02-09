package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.internal.component.impl.texteditor.EditorState
import org.hexworks.zircon.internal.component.impl.texteditor.Transformation
import org.hexworks.zircon.internal.component.impl.texteditor.lastIndex

object MoveCursorDown : Transformation {

    override fun apply(editorState: EditorState): EditorState {
        val (lines, cursor) = editorState
        val (x, y) = cursor

        // if we're at the last line we can't move down
        return if (cursor.y == lines.lastIndex) {
            editorState
        } else {
            // otherwise we can
            val nextLine = lines[y + 1]
            editorState.copy(
                cursor = cursor
                    .withRelativeY(1)
                    .withX(minOf(nextLine.lastIndex, x))
            )
        }
    }
}
