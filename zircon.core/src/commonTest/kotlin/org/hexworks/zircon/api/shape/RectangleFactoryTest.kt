package org.hexworks.zircon.api.shape

import io.kotest.matchers.collections.shouldContainExactly
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class RectangleFactoryTest {

    @Test
    fun shouldProperlyDrawRectangle() {
        RectangleFactory.buildRectangle(
            topLeft = Position.offset1x1(),
            size = Size.create(3, 3)
        ) shouldContainExactly listOf(
            Position.create(x = 1, y = 1),
            Position.create(x = 2, y = 1),
            Position.create(x = 3, y = 1),
            Position.create(x = 3, y = 2),
            Position.create(x = 3, y = 3),
            Position.create(x = 2, y = 3),
            Position.create(x = 1, y = 3),
            Position.create(x = 1, y = 2)
        )
    }

}
