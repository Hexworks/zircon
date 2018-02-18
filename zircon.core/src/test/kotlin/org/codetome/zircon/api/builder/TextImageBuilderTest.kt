package org.codetome.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
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

        assertThat(result.getCharacterAt(Position(SIZE.xLength - 1, SIZE.yLength - 1)).get())
                .isEqualTo(FILLER)
    }

    companion object {
        val FILLER = TextCharacterBuilder.newBuilder().character('a').build()
        val COPY_CHAR = TextCharacterBuilder.newBuilder().character('b').build()
        val SIZE = Size.of(5, 5)
    }
}
