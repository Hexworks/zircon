package org.hexworks.zircon.internal.component.impl.textedit.transformation

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.internal.component.impl.textedit.DefaultEditableTextBuffer
import org.hexworks.zircon.internal.component.impl.textedit.cursor.Cursor
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter.DeleteKind.BACKSPACE
import org.junit.Test

class DeleteCharacterTest {

    @Test
    fun shouldProperlyDeleteMiddleCharacterInFirstRow() {

        val cursor = Cursor(0, 2)
        val buffer = generateBuffer(cursor)

        val target = DeleteCharacter(BACKSPACE)

        target.applyTo(buffer)

        assertThat(buffer.cursor).isEqualTo(
            cursor.withRelativeColumn(-1)
        )
        assertThat(buffer.textBuffer).isEqualTo(
            mutableListOf(
                mutableListOf(OTHER_CHAR, OTHER_CHAR),
                mutableListOf(OTHER_CHAR, OTHER_CHAR, OTHER_CHAR)
            )
        )
    }

    @Test
    fun shouldProperlyDeleteSecondRowWhenCursorIsAtTheStartOfTheSecondRow() {
        val cursor = Cursor(1, 0)
        val buffer = generateBuffer(cursor)

        val target = DeleteCharacter(BACKSPACE)

        target.applyTo(buffer)

        assertThat(buffer.cursor).isEqualTo(
            cursor.withRelativeRow(-1).withRelativeColumn(3)
        )
        assertThat(buffer.textBuffer).isEqualTo(
            mutableListOf(
                mutableListOf(
                    OTHER_CHAR, CHAR_TO_DELETE, OTHER_CHAR,
                    OTHER_CHAR, OTHER_CHAR, OTHER_CHAR
                )
            )
        )
    }

    @Test
    fun shouldDoNothingWhenDeletingFromTheStartOfTheBuffer() {

        val cursor = Cursor(0, 0)
        val buffer = generateBuffer(cursor)

        val target = DeleteCharacter(BACKSPACE)

        target.applyTo(buffer)

        assertThat(buffer.cursor).isEqualTo(
            cursor
        )
        assertThat(buffer.textBuffer).isEqualTo(
            mutableListOf(
                mutableListOf(OTHER_CHAR, CHAR_TO_DELETE, OTHER_CHAR),
                mutableListOf(OTHER_CHAR, OTHER_CHAR, OTHER_CHAR)
            )
        )
    }

    companion object {
        const val OTHER_CHAR = 'a'
        const val CHAR_TO_DELETE = 'x'
        fun generateBuffer(cursor: Cursor) = DefaultEditableTextBuffer(
            source = "$OTHER_CHAR$CHAR_TO_DELETE$OTHER_CHAR" + '\n' +
                    "$OTHER_CHAR$OTHER_CHAR$OTHER_CHAR",
            cursor = cursor
        )
    }
}
