package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.internal.component.impl.texteditor.*

object AddRowBreak : Transformation {
    override fun apply(editorState: EditorState): EditorState {
        val (lines, cursor) = editorState
        val (x, y) = cursor
        val line = editorState.lineAt(cursor)
        val cell = line.cellAt(x)

        val newCursor = cursor.withRelativeY(1).withX(0)

        val newLines = when (cell) {
            is EOLCell -> {
                lines.toMutableList().apply {
                    add(y + 1, Line.create())
                }.toList()
            }

            is TextCell -> {
                lines.toMutableList().apply {
                    set(y, Line.create(line.cells.subList(0, x)))
                    add(y + 1, Line.create(line.cells.drop(x)))
                }.toList()
            }
        }


        return EditorState(newLines, newCursor)
    }

}