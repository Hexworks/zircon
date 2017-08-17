package org.codetome.zircon.api

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.junit.Test

class TextImageBuilderTest {

    @Test
    fun shouldBuildProperTextImage() {
        val result = TextImageBuilder.newBuilder()
                .filler(FILLER)
                .toCopy(arrayOf(arrayOf(COPY_CHAR)))
                .size(SIZE)
                .build()

        assertThat(result.getBoundableSize()).isEqualTo(SIZE)
        assertThat(result.getCharacterAt(Position.DEFAULT_POSITION).get())
                .isEqualTo(COPY_CHAR)

        assertThat(result.getCharacterAt(Position(SIZE.columns - 1, SIZE.rows - 1)).get())
                .isEqualTo(FILLER)
    }

    companion object {
        val FILLER = TextCharacterBuilder.newBuilder().character('a').build()
        val COPY_CHAR = TextCharacterBuilder.newBuilder().character('b').build()
        val SIZE = Size(5, 5)
    }
}