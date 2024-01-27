package org.hexworks.zircon.internal.component.impl.textedit.cursor

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer

/**
 * Represents the position where a user edits a document.
 */
data class Cursor(
    val rowIdx: Int = 0,
    val colIdx: Int = 0
) {

    val position: Position
        get() = Position.create(colIdx, rowIdx)

    /**
     * Moves this [Cursor] in a direction.
     * @return the new state for this [Cursor].
     */
    fun move(direction: MovementDirection, textBuffer: EditableTextBuffer): Cursor {
        return direction.moveCursor(this, textBuffer)
    }

    fun isAtTheStartOfTheFirstRow() = colIdx == 0 && rowIdx == 0

    fun isAtTheStartOfNotTheFirstRow() = colIdx == 0 && rowIdx > 0

    fun canMoveUp() = rowIdx > 0

    fun canMoveDown(textBuffer: EditableTextBuffer) = textBuffer.lastRowIdx > rowIdx

    fun canMoveLeft() = colIdx > 0

    fun canMoveRight(textBuffer: EditableTextBuffer) =
        textBuffer.getLastColumnIdxForRow(rowIdx) >= colIdx

    fun withRow(rowIdx: Int) = copy(rowIdx = rowIdx)

    fun withColumn(colIdx: Int) = copy(colIdx = colIdx)

    fun withRelativeRow(delta: Int) = copy(rowIdx = rowIdx + delta)

    fun withRelativeColumn(delta: Int) = copy(colIdx = colIdx + delta)
}
