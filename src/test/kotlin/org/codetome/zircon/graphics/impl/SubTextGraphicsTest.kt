package org.codetome.zircon.graphics.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.graphics.SubTextGraphics
import org.codetome.zircon.Size
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class SubTextGraphicsTest {

    internal lateinit var target: SubTextGraphics

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = SubTextGraphics(
                underlyingTextGraphics = TEXT_GRAPHICS,
                topLeft = Position.OFFSET_1x1,
                size = SUB_SIZE)
    }

    @Test
    fun shouldProperlyGetCharWhenCharIsSet() {
        target.setCharacter(Position.OFFSET_1x1, CHAR)
        assertThat(target.getCharacter(Position.OFFSET_1x1).get())
                .isEqualTo(CHAR)
    }

    @Test
    fun shouldNotDrawCharWhenOutOfBounds() {
        target.setCharacter(Position(10, 10), CHAR)
    }

    @Test
    fun shouldProperlyReportSize() {
        assertThat(target.getSize()).isEqualTo(SUB_SIZE)
    }

    companion object {
        val CHAR = TextCharacterBuilder.newBuilder()
                .character('x')
                .build()
        val SIZE = Size(10, 10)
        val SUB_SIZE = Size(8, 8)
        val TEXT_GRAPHICS = TextImageBuilder.newBuilder()
                .size(SIZE)
                .build().newTextGraphics()
    }

}