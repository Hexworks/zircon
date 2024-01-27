package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.internal.component.impl.texteditor.TextEditor
import kotlin.test.Test
import kotlin.test.expect


class MoveCursorRightTest {

    @Test
    fun given_an_editor_state_when_moving_the_cursor_right_at_the_end_of_the_document_then_it_doesnt_move() {
        val input = TextEditor.fromText(
            """
            foo
            
            bar
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 3
                y = 2
            }
        )

        val result = MoveCursorRight.apply(input)

        expect(input, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_moving_the_cursor_right_at_the_end_of_a_line_then_it_moves_to_the_start_of_the_next_line() {
        val input = TextEditor.fromText(
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

        val result = MoveCursorRight.apply(input)

        val expected = TextEditor.fromText(
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

        expect(expected, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_moving_the_cursor_right_somewhere_in_a_line_then_it_moves_one_right() {
        val input = TextEditor.fromText(
            """
            foo
            bar
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 2
                y = 1
            }
        )

        val result = MoveCursorRight.apply(input)

        val expected = TextEditor.fromText(
            """
            foo
            bar
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 3
                y = 1
            }
        )

        expect(expected, "State is not what's expected") {
            result
        }
    }

}