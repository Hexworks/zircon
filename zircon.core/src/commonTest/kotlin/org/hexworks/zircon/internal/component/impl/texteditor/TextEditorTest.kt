package org.hexworks.zircon.internal.component.impl.texteditor

import org.hexworks.zircon.api.builder.data.characterTile
import kotlin.test.Test
import kotlin.test.expect


class TextEditorTest {

    @Test
    fun when_a_text_editor_is_created_from_text_then_the_right_state_is_constructed() {
        val state = TextEditor.fromText(
            """
            foo
            bar
        """.trimIndent()
        ).state

        val expected = EditorState(
            lines = listOf(
                Line.create(
                    listOf(
                        TextCell(characterTile { character = 'f' }),
                        TextCell(characterTile { character = 'o' }),
                        TextCell(characterTile { character = 'o' }),
                    )
                ),
                Line.create(
                    listOf(
                        TextCell(characterTile { character = 'b' }),
                        TextCell(characterTile { character = 'a' }),
                        TextCell(characterTile { character = 'r' }),
                    )
                )
            )
        )

        expect(expected, "State is not what's expected") {
            state
        }
    }

    @Test
    fun when_a_text_editor_is_created_from_text_with_a_line_break_then_the_right_state_is_constructed() {
        val state = TextEditor.fromText(
            """
            foo
            
            bar
        """.trimIndent()
        ).state

        val expected = EditorState(
            lines = listOf(
                Line.create(
                    listOf(
                        TextCell(characterTile { character = 'f' }),
                        TextCell(characterTile { character = 'o' }),
                        TextCell(characterTile { character = 'o' }),
                    )
                ),
                Line.create(),
                Line.create(
                    listOf(
                        TextCell(characterTile { character = 'b' }),
                        TextCell(characterTile { character = 'a' }),
                        TextCell(characterTile { character = 'r' }),
                    )
                )
            )
        )

        expect(expected, "State is not what's expected") {
            state
        }
    }
}