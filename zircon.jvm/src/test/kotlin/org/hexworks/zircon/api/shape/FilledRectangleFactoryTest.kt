package org.hexworks.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.junit.Test

class FilledRectangleFactoryTest {

    @Test
    fun shouldProperlyFillRectangle() {
        val result = FilledRectangleFactory.buildFilledRectangle(
                topLeft = Position.offset1x1(),
                size = Size.create(3, 3))

        assertThat(result).containsExactly(
                Position.create(x = 0, y = 0),
                Position.create(x = 1, y = 0),
                Position.create(x = 2, y = 0),
                Position.create(x = 0, y = 1),
                Position.create(x = 1, y = 1),
                Position.create(x = 2, y = 1),
                Position.create(x = 0, y = 2),
                Position.create(x = 1, y = 2),
                Position.create(x = 2, y = 2))
    }


}
