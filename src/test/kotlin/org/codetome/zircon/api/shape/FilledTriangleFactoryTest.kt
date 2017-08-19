package org.codetome.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.api.TextCharacterBuilder
import org.junit.Ignore
import org.junit.Test

class FilledTriangleFactoryTest {

    @Ignore
    @Test
    fun shouldProperlyFillTriangle() {
        // TODO: fix algo!
        println(FilledTriangleFactory.buildFilledTriangle(
                p1 = Position.OFFSET_1x1,
                p2 = Position(5, 1),
                p3 = Position(5, 5))
                .toTextImage(TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter('x')))
        assertThat(FilledTriangleFactory.buildFilledTriangle(
                p1 = Position.OFFSET_1x1,
                p2 = Position(5, 1),
                p3 = Position(5, 5)))
                .containsExactly(
                        Position(column=0, row=0),
                        Position(column=4, row=0),
                        Position(column=3, row=0),
                        Position(column=2, row=0),
                        Position(column=1, row=0),
                        Position(column=4, row=1),
                        Position(column=3, row=1),
                        Position(column=2, row=1),
                        Position(column=4, row=2),
                        Position(column=3, row=2),
                        Position(column=4, row=3),
                        Position(column=4, row=4),
                        Position(column=5, row=4))
    }

    @Test
    fun shouldProperlyFillIrregularTriangle() {
        // TODO: fix triangle fill algo
        println(FilledTriangleFactory.buildFilledTriangle(
                p1 = Position.OFFSET_1x1,
                p2 = Position(4, 2),
                p3 = Position(3, 6))
                .toTextImage(TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter('x')))
        assertThat(FilledTriangleFactory.buildFilledTriangle(
                p1 = Position.OFFSET_1x1,
                p2 = Position(4, 2),
                p3 = Position(3, 6)))
                .containsExactly(
                        Position(column = 0, row = 0),
                        Position(column = 0, row = 1),
                        Position(column = 1, row = 1),
                        Position(column = 2, row = 1),
                        Position(column = 3, row = 1),
                        Position(column = 0, row = 2),
                        Position(column = 1, row = 2),
                        Position(column = 2, row = 2),
                        Position(column = 3, row = 2),
                        Position(column = 1, row = 3),
                        Position(column = 2, row = 3),
                        Position(column = 1, row = 4),
                        Position(column = 2, row = 4),
                        Position(column = 2, row = 5))
    }
}