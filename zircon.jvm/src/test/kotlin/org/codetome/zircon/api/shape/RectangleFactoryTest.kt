package org.codetome.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.junit.Test

class RectangleFactoryTest {

    @Test
    fun shouldProperlyDrawRectangle() {
        assertThat(RectangleFactory.buildRectangle(
                topLeft = Position.offset1x1(),
                size = Size.create(3, 3)))
                .containsExactly(
                        Position.create(x = 0, y = 0),
                        Position.create(x = 1, y = 0),
                        Position.create(x = 2, y = 0),
                        Position.create(x = 2, y = 1),
                        Position.create(x = 2, y = 2),
                        Position.create(x = 1, y = 2),
                        Position.create(x = 0, y = 2),
                        Position.create(x = 0, y = 1))
    }

}
