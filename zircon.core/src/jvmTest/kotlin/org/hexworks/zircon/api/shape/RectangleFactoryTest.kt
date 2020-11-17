package org.hexworks.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.junit.Test

class RectangleFactoryTest {

    @Test
    fun shouldProperlyDrawRectangle() {
        assertThat(RectangleFactory.buildRectangle(
                topLeft = Position.offset1x1(),
                size = Size.create(3, 3)))
                .containsExactly(
                        Position.create(x = 1, y = 1),
                        Position.create(x = 2, y = 1),
                        Position.create(x = 3, y = 1),
                        Position.create(x = 3, y = 2),
                        Position.create(x = 3, y = 3),
                        Position.create(x = 2, y = 3),
                        Position.create(x = 1, y = 3),
                        Position.create(x = 1, y = 2))
    }

}
