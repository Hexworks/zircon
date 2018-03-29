package org.codetome.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.junit.Test

class RectangleFactoryTest {

    @Test
    fun shouldProperlyDrawRectangle() {
        assertThat(RectangleFactory.buildRectangle(
                topLeft = Position.OFFSET_1x1,
                size = Size.of(3, 3)))
                .containsExactly(
                        Position.of(x = 0, y = 0),
                        Position.of(x = 1, y = 0),
                        Position.of(x = 2, y = 0),
                        Position.of(x = 2, y = 1),
                        Position.of(x = 2, y = 2),
                        Position.of(x = 1, y = 2),
                        Position.of(x = 0, y = 2),
                        Position.of(x = 0, y = 1))
    }

}
