package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.internal.component.impl.texteditor.TextEditor
import kotlin.test.Test
import kotlin.test.expect


class MoveCursorToLineStartTest {

    @Test
    fun given_an_editor_state_when_moving_the_cursor_to_the_line_start_then_it_doesnt_do_anything_if_it_is_already_there() {
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

        val result = MoveCursorToLineStart.apply(input)

        expect(input, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_moving_the_cursor_to_the_line_start_then_it_moves_the_cursor_if_it_is_not_already_there() {
        val input = TextEditor.fromText(
            """
            foo
            bar
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 2
                y = 0
            }
        )

        val result = MoveCursorToLineStart.apply(input)

        val expected = TextEditor.fromText(
            """
            foo
            bar
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 0
                y = 0
            }
        )

        expect(expected, "State is not what's expected") {
            result
        }
    }

}