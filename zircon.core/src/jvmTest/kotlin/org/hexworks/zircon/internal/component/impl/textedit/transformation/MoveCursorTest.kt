package org.hexworks.zircon.internal.component.impl.textedit.transformation

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.internal.component.impl.textedit.DefaultEditableTextBuffer
import org.hexworks.zircon.internal.component.impl.textedit.cursor.Cursor
import org.hexworks.zircon.internal.component.impl.textedit.cursor.MovementDirection.*
import org.hexworks.zircon.platform.util.SystemUtils
import org.junit.Test

class MoveCursorTest {

    @Test
    fun shouldMoveStraightUpWhenPreviousLineIsLonger() {
        val cursor = Cursor(1, 6)
        val document = generateBufferFor(cursor)
        MoveCursor(UP).applyTo(document)

        assertThat(document.cursor).isEqualTo(Cursor(0, 6))
    }

    @Test
    fun shouldMoveToEndOfPreviousLineWhenPreviousLineIsShorter() {
        val cursor = Cursor(2, 9)
        val document = generateBufferFor(cursor)
        MoveCursor(UP).applyTo(document)

        assertThat(document.cursor).isEqualTo(Cursor(1, 8))
    }

    @Test
    fun shouldMoveStraightDownWhenNextLineIsLonger() {
        val cursor = Cursor(1, 4)
        val document = generateBufferFor(cursor)
        MoveCursor(DOWN).applyTo(document)

        assertThat(document.cursor).isEqualTo(Cursor(2, 4))
    }

    @Test
    fun shouldMoveToEndOfNextLineWhenNextLineIsShorter() {
        val cursor = Cursor(2, 6)
        val document = generateBufferFor(cursor)
        MoveCursor(DOWN)
            .applyTo(document)

        assertThat(document.cursor)
            .isEqualTo(Cursor(3, 3))
    }

    @Test
    fun shouldMoveLeftOneColumnWhenNotAtTheStartOfTheRow() {
        val cursor = Cursor(2, 1)
        val document = generateBufferFor(cursor)
        MoveCursor(LEFT).applyTo(document)

        assertThat(document.cursor).isEqualTo(Cursor(2, 0))
    }

    @Test
    fun shouldMoveToTheEndOfThePreviousLineWhenMovingLeftFromTheStartOfTheRow() {
        val cursor = Cursor(2, 0)
        val document = generateBufferFor(cursor)
        MoveCursor(LEFT).applyTo(document)

        assertThat(document.cursor).isEqualTo(Cursor(1, 8))
    }

    @Test
    fun shouldNotMoveLeftWhenAtTheStartOfTheBuffer() {
        val cursor = Cursor(0, 0)
        val document = generateBufferFor(cursor)
        MoveCursor(LEFT).applyTo(document)

        assertThat(document.cursor).isEqualTo(Cursor(0, 0))
    }

    @Test
    fun shouldMoveRightOneColumnWhenNotAtTheEndOfTheRow() {
        val cursor = Cursor(1, 1)
        val document = generateBufferFor(cursor)
        MoveCursor(RIGHT).applyTo(document)

        assertThat(document.cursor).isEqualTo(Cursor(1, 2))
    }

    @Test
    fun shouldMoveToTheStartOfTheNextRowWhenMovingRightAtTheEndOfTheRow() {
        val document = generateBufferFor(Cursor(2, 10))
        MoveCursor(RIGHT).applyTo(document)

        assertThat(document.cursor).isEqualTo(Cursor(3, 0))
    }

    @Test
    fun shouldNotMoveWhenAtTheEndOfTheDocument() {
        val document = generateBufferFor(Cursor(3, 3))
        MoveCursor(RIGHT).applyTo(document)

        assertThat(document.cursor).isEqualTo(Cursor(3, 3))
    }


    companion object {

        private const val ROW0 = "Very long row"
        private const val ROW1 = "Long row"
        private const val ROW2 = "Longer row"
        private const val ROW3 = "Row"
        private val ROWS = listOf(ROW0, ROW1, ROW2, ROW3)
            .joinToString(SystemUtils.getLineSeparator())

        fun generateBufferFor(cursor: Cursor) = DefaultEditableTextBuffer(
            source = ROWS,
            cursor = cursor
        )
    }
}
