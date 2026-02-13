package org.hexworks.zircon.api.shape

import io.kotest.matchers.collections.shouldContainExactly
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class FilledRectangleFactoryTest {

    @Test
    fun shouldProperlyFillRectangle() {
        val result = FilledRectangleFactory.buildFilledRectangle(
            topLeft = Position.OFFSET_1X1,
            size = Size.create(3, 3)
        )

        result shouldContainExactly listOf(
            Position.create(x = 1, y = 1),
            Position.create(x = 2, y = 1),
            Position.create(x = 3, y = 1),
            Position.create(x = 1, y = 2),
            Position.create(x = 2, y = 2),
            Position.create(x = 3, y = 2),
            Position.create(x = 1, y = 3),
            Position.create(x = 2, y = 3),
            Position.create(x = 3, y = 3)
        )
    }


}
