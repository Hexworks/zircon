package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.junit.Test

class DefaultShapeTest {

    @Test
    fun shouldProperlyOffsetShape() {
        assertThat(LINE_SHAPE.offsetToDefaultPosition())
                .containsExactlyInAnyOrder(TRANSFORMED_POS_0, TRANSFORMED_POS_1)
    }

    @Test
    fun shouldProperlyAddTwoShapes() {
        assertThat(LINE_SHAPE + OTHER_SHAPE).containsExactlyInAnyOrder(
                POS_0, POS_1, POS_2)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToOffsetEmptyShape() {
        DefaultShape().offsetToDefaultPosition()
    }

    @Test
    fun shouldProperlyCreateTextImage() {
        val result = LINE_SHAPE.toTextImage(CHAR)

        assertThat(result.getCharacterAt(Position(0, 0)).get())
                .isEqualTo(CHAR)
        assertThat(result.getCharacterAt(Position(1, 0)).get())
                .isEqualTo(TextCharacterBuilder.EMPTY)
        assertThat(result.getCharacterAt(Position(0, 1)).get())
                .isEqualTo(TextCharacterBuilder.EMPTY)
        assertThat(result.getCharacterAt(Position(1, 1)).get())
                .isEqualTo(CHAR)

    }

    companion object {
        val POS_0 = Position(2, 3)
        val POS_1 = Position(3, 4)
        val POS_2 = Position(1, 2)

        val TRANSFORMED_POS_0 = Position(0, 0)
        val TRANSFORMED_POS_1 = Position(1, 1)

        val CHAR = TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter('x')

        val LINE_SHAPE = DefaultShape(setOf(POS_0, POS_1))
        val OTHER_SHAPE = DefaultShape(setOf(POS_2))
    }
}