package org.hexworks.zircon.internal.component.impl.textedit.cursor

import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer

enum class MovementDirection(private val moveFn: (Cursor, EditableTextBuffer) -> Cursor) {

    UP({ cursor, document ->
        if (cursor.canMoveUp()) {
            val prevRowIdx = cursor.rowIdx - 1
            cursor.copy(
                rowIdx = prevRowIdx,
                colIdx = kotlin.math.min(
                    document.getColumnCount(prevRowIdx),
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
            cursor.canMoveLeft() ->
                cursor.copy(
                    colIdx = cursor.colIdx - 1
                )
            cursor.canMoveUp() -> {
                val prevRowIdx = cursor.rowIdx - 1
                cursor.copy(
                    rowIdx = prevRowIdx,
                    colIdx = document.getColumnCount(prevRowIdx)
                )
            }
            else -> cursor
        }
    }),
    RIGHT({ cursor, document ->
        when {
            cursor.canMoveRight(document) ->
                cursor.copy(
                    colIdx = cursor.colIdx + 1
                )
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
