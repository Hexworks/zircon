package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.internal.component.impl.texteditor.*

object DeleteAtCursor : Transformation {

    override fun apply(editorState: EditorState): EditorState {
        val (lines, cursor) = editorState
        val (x, y) = cursor
        val line = editorState.lineAt(cursor)

        // if we're at the end of the line
        return if (editorState.cursorAtEndOfLine) {
            // then if there is a next line we need to merge the two
            if (editorState.hasNextLine) {
                editorState.copy(
                    lines = lines.toMutableList().apply {
                        val nextLine = removeAt(y + 1)
                        set(
                            y, Line.create(
                                line.cells.plus(nextLine.cells)
                            )
                        )
                    }.toList()
                )
            } else {
                // otherwise we're at the end of the last line so we can't do anything
                editorState
            }
        } else {
            // we're at some "regular" position where we can simply delete the current cell
            val newLine = Line.create(line.cells.toMutableList().apply {
                removeAt(x)
            }.toList())
            editorState.copy(
                lines = lines.toMutableList().apply {
                    set(y, newLine)
                }.toList()
            )
        }
    }
}
