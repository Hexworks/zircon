package org.codetome.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.junit.Test

class TriangleFactoryTest {

    @Test
    fun shouldProperlyDrawTriangle() {
        assertThat(TriangleFactory.buildTriangle(
                p1 = Position.offset1x1(),
                p2 = Position.create(4, 1),
                p3 = Position.create(4, 4)))
                .containsExactly(
                        Position.create(x = 0, y = 0),
                        Position.create(x = 1, y = 0),
                        Position.create(x = 2, y = 0),
                        Position.create(x = 3, y = 0),
                        Position.create(x = 3, y = 1),
                        Position.create(x = 3, y = 2),
                        Position.create(x = 3, y = 3),
                        Position.create(x = 1, y = 1),
                        Position.create(x = 2, y = 2))
    }

}
