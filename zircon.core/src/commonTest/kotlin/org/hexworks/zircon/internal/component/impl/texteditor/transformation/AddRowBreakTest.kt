package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.internal.component.impl.texteditor.TextEditor
import kotlin.test.Test
import kotlin.test.expect


class AddRowBreakTest {

    @Test
    fun given_an_editor_state_when_adding_a_row_break_at_the_end_of_the_line_then_a_new_empty_line_is_created() {
        val input = TextEditor.fromText(
            """
            foobar
            barbaz
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 6
                y = 0
            }
        )

        val result = AddRowBreak.apply(input)

        val expected = TextEditor.fromText(
            """
            foobar
            
            barbaz
        """.trimIndent()
        ).state.copy(cursor = position {
            x = 0
            y = 1
        })

        expect(expected, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_adding_a_row_break_in_the_middle_of_the_line_then_a_new_empty_line_is_created_and_the_old_row_is_split() {
        val input = TextEditor.fromText(
            """
            foobar
            barbaz
        """.trimIndent()
        ).state.copy(
            cursor = position {
                x = 3
                y = 0
            }
        )

        val result = AddRowBreak.apply(input)

        val expected = TextEditor.fromText(
            """
            foo
            bar
            barbaz
        """.trimIndent()
        ).state.copy(cursor = position {
            x = 0
            y = 1
        })

        expect(expected, "State is not what's expected") {
            result
        }
    }
}