package org.hexworks.zircon.internal.component.impl.textedit.transformation

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.internal.component.impl.textedit.DefaultEditableTextBuffer
import org.hexworks.zircon.internal.component.impl.textedit.cursor.Cursor
import org.hexworks.zircon.platform.util.SystemUtils
import org.junit.Test

class AddRowBreakTest {

    @Test
    fun shouldBreakFirstLineProperlyWhenLineBreakIsAdded() {

        val cursor = Cursor(0, 2)
        val buffer = generateBufferFor(cursor)

        val target = AddRowBreak()

        target.applyTo(buffer)

        assertThat(buffer.textBuffer)
                .containsExactly(
                        mutableListOf(OTHER_CHAR, CHAR_BEFORE_LINE_BREAK),
                        mutableListOf(OTHER_CHAR),
                        mutableListOf(OTHER_CHAR, OTHER_CHAR, OTHER_CHAR))
        assertThat(buffer.cursor)
                .isEqualTo(cursor.withRelativeRow(1).withRelativeColumn(-2))

    }

    @Test
    fun shouldLeaveFirstLineEmptyWhenCursorIsAtBufferStart() {

        val cursor = Cursor(0, 0)
        val buffer = generateBufferFor(cursor)

        val target = AddRowBreak()

        target.applyTo(buffer)

        assertThat(buffer.textBuffer)
                .containsExactly(
                        mutableListOf(),
                        mutableListOf(OTHER_CHAR, CHAR_BEFORE_LINE_BREAK, OTHER_CHAR),
                        mutableListOf(OTHER_CHAR, OTHER_CHAR, OTHER_CHAR))

        assertThat(buffer.cursor).isEqualTo(cursor.withRelativeRow(1))

    }

    @Test
    fun shouldInsertEmptyLineAfterCurrentLineWhenCursorIsAtTheEndOfTheRow() {

        val cursor = Cursor(0, 3)
        val buffer = generateBufferFor(cursor)

        val target = AddRowBreak()

        target.applyTo(buffer)

        assertThat(buffer.textBuffer)
                .containsExactly(
                        mutableListOf(OTHER_CHAR, CHAR_BEFORE_LINE_BREAK, OTHER_CHAR),
                        mutableListOf(),
                        mutableListOf(OTHER_CHAR, OTHER_CHAR, OTHER_CHAR))

        assertThat(buffer.cursor).isEqualTo(
                cursor.withRelativeRow(1).withRelativeColumn(-cursor.colIdx))

    }

    companion object {
        const val OTHER_CHAR = 'a'
        const val CHAR_BEFORE_LINE_BREAK = 'x'

        fun generateBufferFor(cursor: Cursor) = DefaultEditableTextBuffer(
                source = "$OTHER_CHAR$CHAR_BEFORE_LINE_BREAK$OTHER_CHAR" + SystemUtils.getLineSeparator() +
                        "$OTHER_CHAR$OTHER_CHAR$OTHER_CHAR",
                cursor = cursor)
    }
}
