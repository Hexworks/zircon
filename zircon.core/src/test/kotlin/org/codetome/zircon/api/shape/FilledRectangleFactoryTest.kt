package org.codetome.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.junit.Test

class FilledRectangleFactoryTest {

    @Test
    fun shouldProperlyFillRectangle() {
        val result = FilledRectangleFactory.buildFilledRectangle(
                topLeft = Position.OFFSET_1x1,
                size = Size.of(3, 3))

        assertThat(result).containsExactly(
                Position(x = 0, y = 0),
                Position(x = 1, y = 0),
                Position(x = 2, y = 0),
                Position(x = 0, y = 1),
                Position(x = 1, y = 1),
                Position(x = 2, y = 1),
                Position(x = 0, y = 2),
                Position(x = 1, y = 2),
                Position(x = 2, y = 2))
    }


}
