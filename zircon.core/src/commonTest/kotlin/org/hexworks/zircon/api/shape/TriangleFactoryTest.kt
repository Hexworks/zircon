package org.hexworks.zircon.api.shape

import io.kotest.matchers.collections.shouldContainExactly
import org.hexworks.zircon.api.data.Position
import kotlin.test.Ignore
import kotlin.test.Test

class TriangleFactoryTest {

    @Ignore
    @Test
    fun shouldProperlyDrawTriangle() {
        TriangleFactory.buildTriangle(
            p1 = Position.OFFSET_1X1,
            p2 = Position(4, 1),
            p3 = Position(4, 4)
        ) shouldContainExactly listOf(
            Position(x = 0, y = 0),
            Position(x = 1, y = 0),
            Position(x = 2, y = 0),
            Position(x = 3, y = 0),
            Position(x = 3, y = 1),
            Position(x = 3, y = 2),
            Position(x = 3, y = 3),
            Position(x = 1, y = 1),
            Position(x = 2, y = 2)
        )
    }

}
