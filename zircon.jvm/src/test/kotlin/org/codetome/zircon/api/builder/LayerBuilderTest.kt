package org.codetome.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.graphics.LayerBuilder
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
        val SIZE = Size.create(4, 5)
        val FILLER = TextCharacter.defaultCharacter().withCharacter('x')
        val OFFSET = Position.create(3, 4)
    }
}
