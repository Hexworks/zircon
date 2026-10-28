package org.hexworks.zircon.internal.component.impl.texteditor.transformation

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.internal.component.impl.texteditor.TextEditor
import kotlin.test.Test
import kotlin.test.expect


class InsertCharacterTest {

    @Test
    fun given_an_editor_state_when_inserting_a_character_at_the_middle_of_the_line_then_it_is_properly_inserted() {
        val input = TextEditor.fromText(
            """
            wom
            bt
        """.trimIndent()
        ).state.copy(
            cursor = Position(1, 1)
        )

        val result = InsertCharacter(characterTile { +'a' }).apply(input)

        val expected = TextEditor.fromText(
            """
            wom
            bat
        """.trimIndent()
        ).state.copy(cursor = Position(2, 1))

        expect(expected, "State is not what's expected") {
            result
        }
    }

    @Test
    fun given_an_editor_state_when_inserting_a_character_at_the_end_of_the_line_then_it_is_properly_inserted() {
        val input = TextEditor.fromText(
            """
            wom
            ba
        """.trimIndent()
        ).state.copy(
            cursor = Position(2, 1)
        )

        val result = InsertCharacter(characterTile { +'t' }).apply(input)

        val expected = TextEditor.fromText(
            """
            wom
            bat
        """.trimIndent()
        ).state.copy(cursor = Position(3, 1))

        println(expected)

        expect(expected, "State is not what's expected") {
            result
        }
    }
}