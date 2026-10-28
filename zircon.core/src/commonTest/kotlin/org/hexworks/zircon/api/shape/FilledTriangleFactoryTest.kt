package org.hexworks.zircon.api.shape

import io.kotest.matchers.collections.shouldContainExactly
import org.hexworks.zircon.api.data.Position
import kotlin.test.Ignore
import kotlin.test.Test

class FilledTriangleFactoryTest {

    @Ignore
    @Test
    fun shouldProperlyFillTriangle() {
        // TODO: fix algo!
        FilledTriangleFactory.buildFilledTriangle(
            p1 = Position.OFFSET_1X1,
            p2 = Position(5, 1),
            p3 = Position(5, 5)
        ) shouldContainExactly listOf(
            Position(x = 0, y = 0),
            Position(x = 4, y = 0),
            Position(x = 3, y = 0),
            Position(x = 2, y = 0),
            Position(x = 1, y = 0),
            Position(x = 4, y = 1),
            Position(x = 3, y = 1),
            Position(x = 2, y = 1),
            Position(x = 4, y = 2),
            Position(x = 3, y = 2),
            Position(x = 4, y = 3),
            Position(x = 4, y = 4),
            Position(x = 5, y = 4)
        )
    }

    @Ignore
    @Test
    fun shouldProperlyFillIrregularTriangle() {
        // TODO: fix triangle fill algo
        FilledTriangleFactory.buildFilledTriangle(
            p1 = Position.OFFSET_1X1,
            p2 = Position(4, 2),
            p3 = Position(3, 6)
        ) shouldContainExactly listOf(
            Position(x = 1, y = 1),
            Position(x = 1, y = 2),
            Position(x = 2, y = 2),
            Position(x = 3, y = 2),
            Position(x = 4, y = 2),
            Position(x = 1, y = 3),
            Position(x = 2, y = 3),
            Position(x = 3, y = 3),
            Position(x = 4, y = 3),
            Position(x = 2, y = 4),
            Position(x = 3, y = 4),
            Position(x = 2, y = 5),
            Position(x = 3, y = 5),
            Position(x = 3, y = 6)
        )
    }
}
