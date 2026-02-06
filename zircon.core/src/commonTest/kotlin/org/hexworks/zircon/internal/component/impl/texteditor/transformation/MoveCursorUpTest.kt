package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.impl.texteditor.TextEditor
import kotlin.test.Test
import kotlin.test.expect


class MoveCursorUpTest {

    @Test
    fun given_an_editor_state_when_moving_the_cursor_up_at_the_first_line_then_it_doesnt_move() {
        val input = TextEditor.fromText(
            """
            foo
        """.trimIndent()
        ).state.copy(
            cursor = Position.zero()
        )

        val result = MoveCursorUp.apply(input)

        expect(input, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_moving_the_cursor_up_with_the_previous_line_being_shorter_than_the_current_x_then_it_moves_to_the_end_of_the_previous_line() {
        val input = TextEditor.fromText(
            """
            foo
            wombat
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 4
                y = 1
            }
        )

        val result = MoveCursorUp.apply(input)

        val expected = TextEditor.fromText(
            """
            foo
            wombat
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 3
                y = 0
            }
        )

        expect(expected, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_moving_the_cursor_up_with_the_previous_line_being_shorter_but_long_enough_than_the_current_x_then_it_moves_to_the_end_of_the_previous_line() {
        val input = TextEditor.fromText(
            """
            foo
            wombat
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 3
                y = 1
            }
        )

        val result = MoveCursorUp.apply(input)

        val expected = TextEditor.fromText(
            """
            foo
            wombat
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 3
                y = 0
            }
        )

        expect(expected, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_moving_the_cursor_up_with_the_previous_line_being_longer_than_the_current_x_then_it_moves_the_cursor_straight_up() {
        val input = TextEditor.fromText(
            """
            foobar
            wom
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 3
                y = 1
            }
        )

        val result = MoveCursorUp.apply(input)

        val expected = TextEditor.fromText(
            """
            foobar
            wom
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 3
                y = 0
            }
        )

        expect(expected, "State is not what's expected") {
            result
        }
    }

}