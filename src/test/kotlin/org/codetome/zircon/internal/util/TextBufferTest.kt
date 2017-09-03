package org.codetome.zircon.internal.util

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
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
        val textSection = target.getTextSection(Position.of(1, 0), Size.of(2, 3))
        assertThat(textSection[0]).isEqualTo(LINE_0.substring(1, 3))
        assertThat(textSection[1]).isEqualTo("")
        assertThat(textSection[2]).isEqualTo(LINE_0.substring(1, 3))
    }

    @Test
    fun shouldReturnEmptyListWhenRowIsOutOfBounds() {
        val target = TextBuffer("asdf")
        assertThat(target.getTextSection(Position.of(0, 1), Size.ONE)).isEmpty()
    }

    @Test
    fun shouldReturnEmptyStringsWhenColumnIsOutOfAllBounds() {
        val target = TextBuffer(VARIABLE_WIDTH_LINES)
        assertThat(target.getTextSection(Position.of(4, 0), Size.ONE)).containsExactly("")
    }


    companion object {
        val SEP = System.lineSeparator()
        val EXPECTED_MULTI_LINE_TEXT = "asdf${SEP}asdf"

        val LINE_0 = "asdf"
        val LINE_1 = "a"
        val LINE_2 = "asd"
        val VARIABLE_WIDTH_LINES = "$LINE_0$SEP$LINE_1$SEP$LINE_2"
    }
}