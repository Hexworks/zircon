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
                p2 = Position.of(5, 1),
                p3 = Position.of(5, 5)))
                .containsExactly(
                        Position.of(x = 0, y = 0),
                        Position.of(x = 4, y = 0),
                        Position.of(x = 3, y = 0),
                        Position.of(x = 2, y = 0),
                        Position.of(x = 1, y = 0),
                        Position.of(x = 4, y = 1),
                        Position.of(x = 3, y = 1),
                        Position.of(x = 2, y = 1),
                        Position.of(x = 4, y = 2),
                        Position.of(x = 3, y = 2),
                        Position.of(x = 4, y = 3),
                        Position.of(x = 4, y = 4),
                        Position.of(x = 5, y = 4))
    }

    @Test
    fun shouldProperlyFillIrregularTriangle() {
        // TODO: fix triangle fill algo
        assertThat(FilledTriangleFactory.buildFilledTriangle(
                p1 = Position.OFFSET_1x1,
                p2 = Position.of(4, 2),
                p3 = Position.of(3, 6)))
                .containsExactly(
                        Position.of(x = 0, y = 0),
                        Position.of(x = 0, y = 1),
                        Position.of(x = 1, y = 1),
                        Position.of(x = 2, y = 1),
                        Position.of(x = 3, y = 1),
                        Position.of(x = 0, y = 2),
                        Position.of(x = 1, y = 2),
                        Position.of(x = 2, y = 2),
                        Position.of(x = 3, y = 2),
                        Position.of(x = 1, y = 3),
                        Position.of(x = 2, y = 3),
                        Position.of(x = 1, y = 4),
                        Position.of(x = 2, y = 4),
                        Position.of(x = 2, y = 5))
    }
}
