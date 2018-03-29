package org.codetome.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.junit.Test

class TriangleFactoryTest {

    @Test
    fun shouldProperlyDrawTriangle() {
        assertThat(TriangleFactory.buildTriangle(
                p1 = Position.OFFSET_1x1,
                p2 = Position.of(4, 1),
                p3 = Position.of(4, 4)))
                .containsExactly(
                        Position.of(x = 0, y = 0),
                        Position.of(x = 1, y = 0),
                        Position.of(x = 2, y = 0),
                        Position.of(x = 3, y = 0),
                        Position.of(x = 3, y = 1),
                        Position.of(x = 3, y = 2),
                        Position.of(x = 3, y = 3),
                        Position.of(x = 1, y = 1),
                        Position.of(x = 2, y = 2))
    }

}
