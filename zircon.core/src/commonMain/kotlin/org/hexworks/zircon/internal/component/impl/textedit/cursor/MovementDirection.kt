package org.hexworks.zircon.internal.component.impl.textedit.cursor

import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer

enum class MovementDirection(private val moveFn: (Cursor, EditableTextBuffer) -> Cursor) {

    UP({ cursor, document ->
        if (cursor.canMoveUp()) {
            val upRowIdx = cursor.rowIdx - 1
            cursor.copy(
                rowIdx = upRowIdx,
                colIdx = kotlin.math.min(
                    document.getColumnCount(upRowIdx),
                    cursor.colIdx
                )
            )
        } else {
            cursor
        }
    }),
    DOWN({ cursor, document ->
        if (cursor.canMoveDown(document)) {
            val nextRowIdx = cursor.rowIdx + 1
            cursor.copy(
                rowIdx = nextRowIdx,
                colIdx = kotlin.math.min(
                    document.getColumnCount(nextRowIdx),
                    cursor.colIdx
                )
            )
        } else {
            cursor
        }
    }),
    LEFT({ cursor, document ->
        when {
            cursor.canMoveLeft() -> {
                cursor.withRelativeColumn(-1)
            }

            cursor.canMoveUp() -> {
                val prevRowIdx = cursor.rowIdx - 1
                cursor
                    .withRelativeRow(-1)
                    .withColumn(document.getColumnCount(prevRowIdx))
            }

            else -> cursor
        }
    }),
    RIGHT({ cursor, document ->
        when {
            cursor.canMoveRight(document) ->
                cursor.withRelativeColumn(1)

            cursor.canMoveDown(document) -> {
                cursor.copy(
                    rowIdx = cursor.rowIdx + 1,
                    colIdx = 0
                )
            }

            else -> cursor
        }
    });

    fun moveCursor(cursor: Cursor, textBuffer: EditableTextBuffer): Cursor {
        return moveFn.invoke(cursor, textBuffer)
    }
}
