package org.codetome.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.junit.Test

class TriangleFactoryTest {

    @Test
    fun shouldProperlyDrawTriangle() {
        assertThat(TriangleFactory.buildTriangle(
                p1 = Position.OFFSET_1x1,
                p2 = Position(4, 1),
                p3 = Position(4, 4)))
                .containsExactly(
                        Position(column = 0, row = 0),
                        Position(column = 1, row = 0),
                        Position(column = 2, row = 0),
                        Position(column = 3, row = 0),
                        Position(column = 3, row = 1),
                        Position(column = 3, row = 2),
                        Position(column = 3, row = 3),
                        Position(column = 1, row = 1),
                        Position(column = 2, row = 2))
    }

}