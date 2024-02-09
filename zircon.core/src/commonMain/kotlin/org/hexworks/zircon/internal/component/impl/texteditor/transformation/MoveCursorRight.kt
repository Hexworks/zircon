package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.internal.component.impl.texteditor.*

object MoveCursorRight : Transformation {

    override fun apply(editorState: EditorState): EditorState {
        val (lines, cursor) = editorState
        val (x, y) = cursor
        val line = editorState.lineAt(y)

        return when {
            // if we're at the end of the document we can't move
            editorState.cursorAtEndOfDocument -> editorState
            // we know we're not at the end of the document, just a line
            // so there must be a next line
            editorState.cursorAtEndOfLine -> editorState.copy(
                cursor = cursor.withRelativeY(1).withX(0)
            )
            // we're not at the end of any line so we can move right
            else -> editorState.copy(
                cursor = cursor.withRelativeX(1)
            )
        }
    }
}
