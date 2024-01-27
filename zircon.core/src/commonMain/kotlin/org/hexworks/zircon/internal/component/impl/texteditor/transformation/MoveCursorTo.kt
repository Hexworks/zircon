package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.impl.texteditor.EditorState
import org.hexworks.zircon.internal.component.impl.texteditor.Transformation
import org.hexworks.zircon.internal.component.impl.texteditor.lastIndex
import org.hexworks.zircon.internal.component.impl.texteditor.lineAt

data class MoveCursorTo(
    val position: Position
) : Transformation {

    override fun apply(editorState: EditorState): EditorState {
        val (lines, cursor) = editorState
        val line = editorState.lineAt(cursor)
        return editorState.copy(
            cursor = position{
                x = minOf(position.x, line.lastIndex)
                y = minOf(position.y, lines.lastIndex)
            }
        )
    }
}
