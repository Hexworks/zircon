package org.hexworks.zircon.internal.component.impl.textedit.cursor

import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer
import kotlin.test.Test
import kotlin.test.expect


class MovementDirectionTest {

    // UP
    @Test
    fun when_trying_to_move_the_cursor_up_then_it_shouldnt_change_if_it_cannot_move_up() {
        val cursor = Cursor(
            rowIdx = 0,
            colIdx = 1
        )
        val buffer = EditableTextBuffer.create("")

        expect(
            expected = cursor,
            message = "Shouldn't be able to move up if there is no row above"
        ) {
            MovementDirection.UP.moveCursor(cursor, buffer)
        }
    }

    @Test
    fun when_trying_to_move_the_cursor_up_then_should_move_up_in_the_same_column_when_it_is_possible() {
        val cursor = Cursor(
            rowIdx = 1,
            colIdx = 1
        )
        val buffer = EditableTextBuffer.create(
            """
            first
            second
        """.trimIndent()
        )

        expect(
            expected = Cursor(
                rowIdx = 0,
                colIdx = 1
            ),
            message = "Should be able to move up in the same column"
        ) {
            MovementDirection.UP.moveCursor(cursor, buffer)
        }
    }

    @Test
    fun when_trying_to_move_the_cursor_up_then_it_should_move_up_to_another_column_when_there_is_idx_above_but_no_column() {
        val cursor = Cursor(
            rowIdx = 1,
            colIdx = 3
        )
        val buffer = EditableTextBuffer.create(
            """
            fo
            second
        """.trimIndent()
        )

        expect(
            expected = Cursor(
                rowIdx = 0,
                colIdx = 2 // 📕 Note that this is AFTER the last char so we can delete immediately
            ),
            message = "Should be able to move up in the appropriate column"
        ) {
            MovementDirection.UP.moveCursor(cursor, buffer)
        }
    }

    // DOWN
    @Test
    fun when_trying_to_move_the_cursor_down_then_it_shouldnt_change_if_it_cannot_move_down() {
        val cursor = Cursor(
            rowIdx = 1,
            colIdx = 4
        )
        val buffer = EditableTextBuffer.create("""
            first
            second
        """.trimIndent())

        expect(
            expected = cursor,
            message = "Shouldn't be able to move down if there is no row below"
        ) {
            MovementDirection.DOWN.moveCursor(cursor, buffer)
        }
    }

    @Test
    fun when_trying_to_move_the_cursor_down_then_it_should_move_down_in_the_same_column_when_it_is_possible() {
        val cursor = Cursor(
            rowIdx = 1,
            colIdx = 1
        )
        val buffer = EditableTextBuffer.create(
            """
            first
            second
            third
        """.trimIndent()
        )

        expect(
            expected = Cursor(
                rowIdx = 2,
                colIdx = 1
            ),
            message = "Should be able to move down in the same column"
        ) {
            MovementDirection.DOWN.moveCursor(cursor, buffer)
        }
    }

    @Test
    fun when_trying_to_move_the_cursor_down_then_it_should_move_down_to_another_column_when_there_is_row_below_but_no_column() {
        val cursor = Cursor(
            rowIdx = 1,
            colIdx = 3
        )
        val buffer = EditableTextBuffer.create(
            """
            first
            second
            x
        """.trimIndent()
        )

        expect(
            expected = Cursor(
                rowIdx = 2,
                colIdx = 1
            ),
            message = "Should be able to move down in the appropriate column"
        ) {
            MovementDirection.DOWN.moveCursor(cursor, buffer)
        }
    }

    // LEFT
    @Test
    fun when_trying_to_move_the_cursor_left_then_it_should_change_if_it_can_move_left() {
        val cursor = Cursor(
            rowIdx = 1,
            colIdx = 4
        )
        val buffer = EditableTextBuffer.create("""
            first
            second
        """.trimIndent())

        expect(
            expected = cursor.withRelativeColumn(-1),
            message = "Should be able to move left if not at the first column"
        ) {
            MovementDirection.LEFT.moveCursor(cursor, buffer)
        }
    }

    @Test
    fun when_trying_to_move_the_cursor_left_then_it_should_move_up_if_it_is_at_the_first_column_but_not_the_first_row() {
        val cursor = Cursor(
            rowIdx = 1,
            colIdx = 0
        )
        val buffer = EditableTextBuffer.create("""
            first
            second
        """.trimIndent())

        expect(
            expected = Cursor(
                rowIdx = 0,
                colIdx = 5
            ),
            message = "Should move to the end of the row above"
        ) {
            MovementDirection.LEFT.moveCursor(cursor, buffer)
        }
    }

    @Test
    fun when_trying_to_move_the_cursor_left_then_it_should_not_move_up_if_it_is_at_the_first_column_and_the_first_row() {
        val cursor = Cursor(
            rowIdx = 0,
            colIdx = 0
        )
        val buffer = EditableTextBuffer.create("""
            first
            second
        """.trimIndent())

        expect(
            expected = cursor,
            message = "Should not move if it is already at the top left position"
        ) {
            MovementDirection.LEFT.moveCursor(cursor, buffer)
        }
    }

    // RIGHT
    fun when_trying_to_move_the_cursor_right_then_it_should_change_if_it_can_move_right() {
        val cursor = Cursor(
            rowIdx = 1,
            colIdx = 4
        )
        val buffer = EditableTextBuffer.create("""
            first
            second
        """.trimIndent())

        expect(
            expected = cursor.withRelativeColumn(1),
            message = "Should be able to move right if not at the last column"
        ) {
            MovementDirection.RIGHT.moveCursor(cursor, buffer)
        }
    }

    fun when_trying_to_move_the_cursor_right_then_it_should_change_if_it_can_move_right_at_the_last_column() {
        val cursor = Cursor(
            rowIdx = 1,
            colIdx = 5
        )
        val buffer = EditableTextBuffer.create("""
            first
            second
        """.trimIndent())

        expect(
            expected = cursor.withRelativeColumn(1),
            message = "Should be able to move right even if at the last column"
        ) {
            MovementDirection.RIGHT.moveCursor(cursor, buffer)
        }
    }

    fun when_trying_to_move_the_cursor_right_then_it_should_change_to_the_next_row_when_after_the_last_column() {
        val cursor = Cursor(
            rowIdx = 0,
            colIdx = 5
        )
        val buffer = EditableTextBuffer.create("""
            first
            second
        """.trimIndent())

        expect(
            expected = Cursor(
                rowIdx = 1,
                colIdx = 0
            ),
            message = "Should move down when moving right after the last column"
        ) {
            MovementDirection.RIGHT.moveCursor(cursor, buffer)
        }
    }

    fun when_trying_to_move_the_cursor_right_then_it_should_not_change_after_the_last_column_in_the_last_row() {
        val cursor = Cursor(
            rowIdx = 1,
            colIdx = 6
        )
        val buffer = EditableTextBuffer.create("""
            first
            second
        """.trimIndent())

        expect(
            expected = cursor,
            message = "Should not move when moving right after the last column in the last row"
        ) {
            MovementDirection.RIGHT.moveCursor(cursor, buffer)
        }
    }
}