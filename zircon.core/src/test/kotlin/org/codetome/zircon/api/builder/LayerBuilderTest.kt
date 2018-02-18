package org.codetome.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.junit.Before
import org.junit.Test

class LayerBuilderTest {

    lateinit var target: LayerBuilder

    @Before
    fun setUp() {
        target = LayerBuilder()
    }

    @Test
    fun shouldProperlyBuildLayer() {
        val result = target
                .size(SIZE)
                .filler(FILLER)
                .offset(OFFSET)
                .build()

        assertThat(result.getBoundableSize())
                .isEqualTo(SIZE)

        assertThat(result.getPosition())
                .isEqualTo(OFFSET)

        assertThat(result.getCharacterAt(OFFSET).get())
                .isEqualTo(FILLER)
    }

    companion object {
        val SIZE = Size.of(4, 5)
        val FILLER = TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter('x')
        val OFFSET = Position(3, 4)
    }
}
