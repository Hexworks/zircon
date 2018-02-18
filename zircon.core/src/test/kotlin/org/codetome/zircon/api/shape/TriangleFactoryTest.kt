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
                        Position(x = 0, y = 0),
                        Position(x = 1, y = 0),
                        Position(x = 2, y = 0),
                        Position(x = 3, y = 0),
                        Position(x = 3, y = 1),
                        Position(x = 3, y = 2),
                        Position(x = 3, y = 3),
                        Position(x = 1, y = 1),
                        Position(x = 2, y = 2))
    }

}
