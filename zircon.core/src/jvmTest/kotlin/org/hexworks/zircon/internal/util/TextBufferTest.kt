package org.hexworks.zircon.internal.util

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.junit.Test

class TextBufferTest {


    @Test
    fun shouldCreateProperText() {
        val target = TextBuffer(EXPECTED_MULTI_LINE_TEXT)
        assertThat(target.getText()).isEqualTo(EXPECTED_MULTI_LINE_TEXT)
    }

    @Test
    fun shouldProperlyExtractSectionFromVariableWidthLines() {
        val target = TextBuffer(VARIABLE_WIDTH_LINES)
        val textSection = target.getTextSection(Position.create(1, 0), Size.create(2, 3))
        assertThat(textSection[0]).isEqualTo(LINE_0.substring(1, 3))
        assertThat(textSection[1]).isEqualTo("")
        assertThat(textSection[2]).isEqualTo(LINE_0.substring(1, 3))
    }

    @Test
    fun shouldReturnEmptyListWhenRowIsOutOfBounds() {
        val target = TextBuffer("asdf")
        assertThat(target.getTextSection(Position.create(0, 1), Size.one())).isEmpty()
    }

    @Test
    fun shouldReturnEmptyStringsWhenColumnIsOutOfAllBounds() {
        val target = TextBuffer(VARIABLE_WIDTH_LINES)
        assertThat(target.getTextSection(Position.create(4, 0), Size.one())).containsExactly("")
    }

    @Test
    fun shouldReturnEmptyOptionalWhenRowIndexIsOutOfBounds() {
        val target = TextBuffer(EXPECTED_MULTI_LINE_TEXT)
        assertThat(target.getRowOrNull(Int.MAX_VALUE) != null).isFalse()
    }

    @Test
    fun shouldReturnLineProperlyWhenLineExists() {
        val target = TextBuffer(VARIABLE_WIDTH_LINES)
        assertThat(target.getRowOrNull(1)!!.toString()).isEqualTo(LINE_1)
    }

    @Test
    fun shouldProperlyReturnSize() {
        val target = TextBuffer(VARIABLE_WIDTH_LINES)
        assertThat(target.getSize()).isEqualTo(VARIABLE_WIDTH_LINES.split(SEP).size)
    }

    @Test
    fun shouldProperlyAddRowAt() {
        val target = TextBuffer(EXPECTED_MULTI_LINE_TEXT)
        target.addNewRowAt(1)
        assertThat(target.getRowOrNull(1)!!.toString()).isEqualTo("")
    }

    @Test
    fun shouldProperlyDeleteAt() {
        val target = TextBuffer(VARIABLE_WIDTH_LINES)
        target.deleteRowAt(1)
        assertThat(target.getText()).isEqualTo("$LINE_0$SEP$LINE_2")
    }

    companion object {
        val SEP = System.lineSeparator()
        val TEXT = "asdf"
        val EXPECTED_MULTI_LINE_TEXT = "$TEXT$SEP$TEXT"

        val LINE_0 = "asdf"
        val LINE_1 = "a"
        val LINE_2 = "asd"
        val VARIABLE_WIDTH_LINES = "$LINE_0$SEP$LINE_1$SEP$LINE_2"
    }
}
