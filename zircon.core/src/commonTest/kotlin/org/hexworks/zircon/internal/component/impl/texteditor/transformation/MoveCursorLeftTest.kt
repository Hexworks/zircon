package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.impl.texteditor.TextEditor
import kotlin.test.Test
import kotlin.test.expect


class MoveCursorLeftTest {

    @Test
    fun given_an_editor_state_when_moving_the_cursor_left_at_the_start_of_the_document_then_it_doesnt_move() {
        val input = TextEditor.fromText(
            """
            foo
        """.trimIndent()
        ).state.copy(
            cursor = Position.zero()
        )

        val result = MoveCursorLeft.apply(input)

        expect(input, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_moving_the_cursor_left_at_the_start_of_a_line_then_it_moves_to_the_start_of_the_previous_line() {
        val input = TextEditor.fromText(
            """
            foo
            bar
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 0
                y = 1
            }
        )

        val result = MoveCursorLeft.apply(input)

        val expected = TextEditor.fromText(
            """
            foo
            bar
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
    fun given_an_editor_state_when_moving_the_cursor_left_somewhere_in_a_line_then_it_moves_one_left() {
        val input = TextEditor.fromText(
            """
            foobar
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 2
                y = 0
            }
        )

        val result = MoveCursorLeft.apply(input)

        val expected = TextEditor.fromText(
            """
            foobar
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 1
                y = 0
            }
        )

        expect(expected, "State is not what's expected") {
            result
        }
    }

}