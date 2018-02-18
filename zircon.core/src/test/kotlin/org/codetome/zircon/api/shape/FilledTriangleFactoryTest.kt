package org.codetome.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
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
                        Position(x = 0, y = 0),
                        Position(x = 4, y = 0),
                        Position(x = 3, y = 0),
                        Position(x = 2, y = 0),
                        Position(x = 1, y = 0),
                        Position(x = 4, y = 1),
                        Position(x = 3, y = 1),
                        Position(x = 2, y = 1),
                        Position(x = 4, y = 2),
                        Position(x = 3, y = 2),
                        Position(x = 4, y = 3),
                        Position(x = 4, y = 4),
                        Position(x = 5, y = 4))
    }

    @Test
    fun shouldProperlyFillIrregularTriangle() {
        // TODO: fix triangle fill algo
        assertThat(FilledTriangleFactory.buildFilledTriangle(
                p1 = Position.OFFSET_1x1,
                p2 = Position(4, 2),
                p3 = Position(3, 6)))
                .containsExactly(
                        Position(x = 0, y = 0),
                        Position(x = 0, y = 1),
                        Position(x = 1, y = 1),
                        Position(x = 2, y = 1),
                        Position(x = 3, y = 1),
                        Position(x = 0, y = 2),
                        Position(x = 1, y = 2),
                        Position(x = 2, y = 2),
                        Position(x = 3, y = 2),
                        Position(x = 1, y = 3),
                        Position(x = 2, y = 3),
                        Position(x = 1, y = 4),
                        Position(x = 2, y = 4),
                        Position(x = 2, y = 5))
    }
}
