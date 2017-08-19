package org.codetome.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.junit.Test

class RectangleFactoryTest {

    @Test
    fun shouldProperlyDrawRectangle() {
        assertThat(RectangleFactory.buildRectangle(
                topLeft = Position.OFFSET_1x1,
                size = Size(3, 3)))
                .containsExactly(
                        Position(column=0, row=0),
                        Position(column=1, row=0),
                        Position(column=2, row=0),
                        Position(column=2, row=1),
                        Position(column=2, row=2),
                        Position(column=1, row=2),
                        Position(column=0, row=2),
                        Position(column=0, row=1))
    }

}