package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.internal.component.impl.texteditor.*

class InsertCharacter(private val tile: CharacterTile) : Transformation {
    override fun apply(editorState: EditorState): EditorState {
        val (lines, cursor) = editorState
        val (x, y) = cursor
        val line = editorState.lineAt(cursor)

        val newLine = Line.create(
            line.cells.toMutableList().apply {
                add(x, TextCell(tile))
            }.toList()
        )

        val newCursor = cursor.withRelativeX(1)
        val newLines = lines.toMutableList().apply {
            set(y, newLine)
        }.toList()
        return EditorState(newLines, newCursor)
    }
}