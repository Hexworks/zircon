package org.codetome.zircon.graphics.shape.factory

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.junit.Ignore
import org.junit.Test

class FilledTriangleFactoryTest {

    @Ignore
    @Test
    fun shouldProperlyFillTriangle() {
        // TODO: fix algo!
        assertThat(FilledTriangleFactory.buildFilledTriangle(
                p1 = Position.OFFSET_1x1,
                p2 = Position(5, 1),
                p3 = Position(5, 5)))
                .containsExactly(
                        Position(column = 0, row = 0),
                        Position(column = 3, row = 0),
                        Position(column = 2, row = 0),
                        Position(column = 1, row = 0),
                        Position(column = 0, row = 0),
                        Position(column = 2, row = 1),
                        Position(column = 1, row = 1),
                        Position(column = 0, row = 1),
                        Position(column = 1, row = 2),
                        Position(column = 0, row = 2),
                        Position(column = 0, row = 3))
    }

    @Test
    fun shouldProperlyFillIrregularTriangle() {
        // TODO: fix triangle fill algo
        assertThat(FilledTriangleFactory.buildFilledTriangle(
                p1 = Position.DEFAULT_POSITION,
                p2 = Position(3, 1),
                p3 = Position(2, 5)))
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