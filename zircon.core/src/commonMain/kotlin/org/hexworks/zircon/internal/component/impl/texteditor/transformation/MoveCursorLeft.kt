package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.internal.component.impl.texteditor.*

object MoveCursorLeft : Transformation {

    override fun apply(editorState: EditorState): EditorState {
        val (_, cursor) = editorState
        val (_, y) = cursor

        return when {
            // when we're at the start of the document we can't move anywhere
            editorState.cursorAtStartOfDocument -> editorState
            // we know there is a previous line, so we move at the end of it
            editorState.cursorAtStartOfLine -> {
                val prevLine = editorState.lineAt(y - 1)
                editorState.copy(
                    cursor = cursor.withRelativeY(-1).withX(prevLine.lastIndex)
                )
            }
            // we know we're not at the start of any line so it is safe to move one left
            else -> editorState.copy(
                cursor = cursor.withRelativeX(-1)
            )
        }
    }
}
