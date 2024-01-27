package org.hexworks.zircon.internal.component.impl.textedit.cursor

import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CursorTest {

    // ROWS
    @Test
    fun given_a_cursor_when_it_is_at_the_start_of_the_first_row_then_it_should_not_be_at_the_start_of_a_non_first_row() {
        val target = Cursor(0, 0)

        assertFalse ("Cursor should report not being at the start of the first row") {
            target.isAtTheStartOfNotTheFirstRow()
        }
    }

    @Test
    fun given_a_cursor_when_it_is_at_the_start_of_the_second_row_then_it_should_report_being_at_the_start_of_not_the_first_row() {
        val target = Cursor(1, 0)

        assertTrue("Cursor should report being at the start of not the first row") {
            target.isAtTheStartOfNotTheFirstRow()
        }
    }

    // UP
    @Test
    fun given_a_cursor_when_it_is_at_the_first_row_then_it_cannot_move_up() {
        val target = Cursor(0, 5)

        assertTrue("Cursor shouldn't be able to move up") {
            !target.canMoveUp()
        }
    }

    @Test
    fun given_a_cursor_when_it_is_at_the_second_row_then_it_can_move_up() {
        val target = Cursor(1, 5)

        assertTrue("Cursor should be able to move up") {
            target.canMoveUp()
        }
    }

    // DOWN
    @Test
    fun given_a_cursor_when_it_is_not_at_the_last_row_of_the_buffer_then_it_can_move_down() {
        val target = Cursor(2, 0)

        assertTrue("Cursor should be able to move down") {
            target.canMoveDown(EditableTextBuffer.create("0\n1\n2\n3"))
        }
    }

    @Test
    fun given_a_cursor_when_it_is_at_the_last_row_of_the_buffer_then_it_cannot_move_down() {
        val target = Cursor(2, 0)

        assertFalse("Cursor should not be able to move down") {
            target.canMoveDown(EditableTextBuffer.create("0\n1\n2"))
        }
    }

    // LEFT
    @Test
    fun given_a_cursor_when_it_is_at_the_leftmost_position_then_it_cannot_move_left() {
        val target = Cursor(1, 0)

        assertTrue("Cursor shouldn't be able to move left") {
            !target.canMoveLeft()
        }
    }

    @Test
    fun given_a_cursor_when_it_is_not_at_the_leftmost_position_then_it_can_move_left() {
        val target = Cursor(1, 1)

        assertTrue("Cursor should be able to move left") {
            target.canMoveLeft()
        }
    }

    // RIGHT
    @Test
    fun given_a_cursor_when_it_is_not_at_the_last_column_of_the_buffer_then_it_can_move_right() {
        val target = Cursor(0, 2)

        assertTrue("Cursor should be able to move right") {
            target.canMoveRight(EditableTextBuffer.create("foob"))
        }
    }

    @Test
    fun given_a_cursor_when_it_is_at_the_last_column_of_the_buffer_then_it_cannot_move_right() {
        val target = Cursor(0, 3)

        assertFalse("Cursor should be able to move right") {
            target.canMoveRight(EditableTextBuffer.create("foob"))
        }
    }

}