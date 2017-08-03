package org.codetome.zircon.builder

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.terminal.TerminalSize
import org.junit.Test

class TextImageBuilderTest {

    @Test
    fun shouldBuildProperTextImage() {
        val result = TextImageBuilder.newBuilder()
                .filler(FILLER)
                .toCopy(arrayOf(arrayOf(COPY_CHAR)))
                .size(SIZE)
                .build()

        assertThat(result.getSize()).isEqualTo(SIZE)
        assertThat(result.getCharacterAt(TerminalPosition.DEFAULT_POSITION))
                .isEqualTo(COPY_CHAR)

        assertThat(result.getCharacterAt(TerminalPosition(SIZE.columns - 1, SIZE.rows - 1)))
                .isEqualTo(FILLER)
    }

    companion object {
        val FILLER = TextCharacterBuilder.newBuilder().character('a').build()
        val COPY_CHAR = TextCharacterBuilder.newBuilder().character('b').build()
        val SIZE = TerminalSize(5, 5)
    }
}