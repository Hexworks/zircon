package org.hexworks.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Position
import org.junit.Ignore
import org.junit.Test

class FilledTriangleFactoryTest {

    @Ignore
    @Test
    fun shouldProperlyFillTriangle() {
        // TODO: fix algo!
        assertThat(FilledTriangleFactory.buildFilledTriangle(
                p1 = Position.offset1x1(),
                p2 = Position.create(5, 1),
                p3 = Position.create(5, 5)))
                .containsExactly(
                        Position.create(x = 0, y = 0),
                        Position.create(x = 4, y = 0),
                        Position.create(x = 3, y = 0),
                        Position.create(x = 2, y = 0),
                        Position.create(x = 1, y = 0),
                        Position.create(x = 4, y = 1),
                        Position.create(x = 3, y = 1),
                        Position.create(x = 2, y = 1),
                        Position.create(x = 4, y = 2),
                        Position.create(x = 3, y = 2),
                        Position.create(x = 4, y = 3),
                        Position.create(x = 4, y = 4),
                        Position.create(x = 5, y = 4))
    }

    @Test
    fun shouldProperlyFillIrregularTriangle() {
        // TODO: fix triangle fill algo
        assertThat(FilledTriangleFactory.buildFilledTriangle(
                p1 = Position.offset1x1(),
                p2 = Position.create(4, 2),
                p3 = Position.create(3, 6)))
                .containsExactly(
                        Position.create(x = 0, y = 0),
                        Position.create(x = 0, y = 1),
                        Position.create(x = 1, y = 1),
                        Position.create(x = 2, y = 1),
                        Position.create(x = 3, y = 1),
                        Position.create(x = 0, y = 2),
                        Position.create(x = 1, y = 2),
                        Position.create(x = 2, y = 2),
                        Position.create(x = 3, y = 2),
                        Position.create(x = 1, y = 3),
                        Position.create(x = 2, y = 3),
                        Position.create(x = 1, y = 4),
                        Position.create(x = 2, y = 4),
                        Position.create(x = 2, y = 5))
    }
}
