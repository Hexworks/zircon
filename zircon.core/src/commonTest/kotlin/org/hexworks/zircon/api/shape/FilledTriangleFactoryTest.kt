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
            p1 = Position.offset1x1(),
            p2 = Position.create(5, 1),
            p3 = Position.create(5, 5)
        ) shouldContainExactly listOf(
            Position.create(x = 0, y = 0),
            Position.create(x = 4, y = 0),
            Position.create(x = 3, y = 0),
            Position.create(x = 2, y = 0),
            Position.create(x = 1, y = 0),
            Position.create(x = 4, y = 1),
            Position.create(x = 3, y = 1),
            Position.create(x = 2, y = 1),
            Position.create(x = 4, y = 2),
            Position.create(x = 3, y = 2),
            Position.create(x = 4, y = 3),
            Position.create(x = 4, y = 4),
            Position.create(x = 5, y = 4)
        )
    }

    @Ignore
    @Test
    fun shouldProperlyFillIrregularTriangle() {
        // TODO: fix triangle fill algo
        FilledTriangleFactory.buildFilledTriangle(
            p1 = Position.offset1x1(),
            p2 = Position.create(4, 2),
            p3 = Position.create(3, 6)
        ) shouldContainExactly listOf(
            Position.create(x = 1, y = 1),
            Position.create(x = 1, y = 2),
            Position.create(x = 2, y = 2),
            Position.create(x = 3, y = 2),
            Position.create(x = 4, y = 2),
            Position.create(x = 1, y = 3),
            Position.create(x = 2, y = 3),
            Position.create(x = 3, y = 3),
            Position.create(x = 4, y = 3),
            Position.create(x = 2, y = 4),
            Position.create(x = 3, y = 4),
            Position.create(x = 2, y = 5),
            Position.create(x = 3, y = 5),
            Position.create(x = 3, y = 6)
        )
    }
}
