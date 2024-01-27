package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.impl.texteditor.*

object DeleteBeforeCursor : Transformation {

    override fun apply(editorState: EditorState): EditorState {
        val (lines, cursor) = editorState
        val (x, y) = cursor
        val line = editorState.lineAt(cursor)

        val newState = when {
            // we can't delete back if we're at (0, 0)
            cursor.isAtTopLeft -> editorState
            // but if we're at the start of a row (it is empty), then we need to delete it
            cursor.isAtTheStartOfNotTheFirstRow -> {
                val newY = y - 1;
                editorState.copy(
                    lines = lines.toMutableList().apply {
                        removeAt(y)
                    }.toList(),
                    cursor = position {
                        this.x = editorState.lineAt(newY).lastIndex
                        this.y = newY
                    }
                )
            }
            // we know we're not at the start of any row, so we can just delete the previous char
            editorState.canDeleteCharInLine -> {
                val newLine = Line.create(
                    line.cells.toMutableList().apply {
                        removeAt(x - 1)
                    }.toList()
                )
                editorState.copy(
                    lines = lines.toMutableList().apply {
                        set(y, newLine)
                    }.toList(),
                    cursor = cursor.withRelativeX(-1)
                )
            }
            // just to make sure ...
            else -> {
                error("Unsupported text editor state, this is probably a bug: $editorState")
            }
        }
        return newState
    }
}

private val Position.isAtTopLeft: Boolean
    get() = this == Position.zero()

private val Position.isAtTheStartOfNotTheFirstRow: Boolean
    get() = this.x == 0 && this.y > 0

private val EditorState.canDeleteCharInLine: Boolean
    get() {
        val (_, cursor) = this
        val (x) = cursor
        return x > 0 && cellAt(cursor.withRelativeX(-1)) is TextCell
    }