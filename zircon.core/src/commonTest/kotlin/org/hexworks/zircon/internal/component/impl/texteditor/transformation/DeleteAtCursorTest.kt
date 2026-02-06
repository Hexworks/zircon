package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.internal.component.impl.texteditor.TextEditor
import kotlin.test.Test
import kotlin.test.expect


class DeleteAtCursorTest {

    @Test
    fun given_an_editor_state_when_deleting_a_character_at_the_cursor_in_the_middle_of_the_line_then_it_is_properly_deleted() {
        val input = TextEditor.fromText(
            """
            wom
            baxt
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 2
                y = 1
            }
        )

        val result = DeleteAtCursor.apply(input)

        val expected = TextEditor.fromText(
            """
            wom
            bat
        """.trimIndent()
        ).state.copy(cursor = position {
            x = 2
            y = 1
        })

        expect(expected, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_deleting_a_character_at_the_cursor_at_the_end_of_the_line_then_the_lines_are_properly_merged() {
        val input = TextEditor.fromText(
            """
            wom
            bat
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 3
                y = 0
            }
        )

        val result = DeleteAtCursor.apply(input)

        val expected = TextEditor.fromText(
            """
            wombat
        """.trimIndent()
        ).state.copy(cursor = position {
            x = 3
            y = 0
        })

        expect(expected, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_deleting_a_character_at_the_cursor_at_the_end_of_the_last_line_then_the_same_state_is_returned() {
        val input = TextEditor.fromText(
            """
            wom
            bat
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 3
                y = 1
            }
        )

        val result = DeleteAtCursor.apply(input)


        expect(input, "State is not what's expected") {
            result
        }
    }
}