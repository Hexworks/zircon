package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.internal.component.impl.texteditor.EditorState
import org.hexworks.zircon.internal.component.impl.texteditor.Transformation
import org.hexworks.zircon.internal.component.impl.texteditor.lastIndex

object MoveCursorUp : Transformation {

    override fun apply(editorState: EditorState): EditorState {
        val (lines, cursor) = editorState
        val (x, y) = cursor

        // if we're at the first line we can't move up
        return if (cursor.y == 0) {
            editorState
        } else {
            // otherwise we can
            val prevLine = lines[y - 1]
            editorState.copy(
                cursor = cursor
                    .withRelativeY(-1)
                    .withX(minOf(prevLine.lastIndex, x))
            )
        }
    }
}
