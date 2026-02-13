package org.hexworks.zircon.internal.data

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class DefaultRectTest {

    @Test
    fun shouldProperlyPlusTwoRects() {
        val result = Boundable.create(Position.create(1, 1), Size.create(1, 1))
            .plus(Boundable.create(Position.create(2, 2), Size.create(2, 2)))

        result shouldBe Boundable.create(Position.create(3, 3), Size.create(3, 3))
    }

    @Test
    fun shouldProperlyMinusTwoRects() {
        val result = Boundable.create(Position.create(3, 3), Size.create(3, 3))
            .minus(Boundable.create(Position.create(2, 2), Size.create(2, 2)))

        result shouldBe Boundable.create(Position.create(1, 1), Size.create(1, 1))
    }

    @Test
    fun shouldProperlyCopyWithPosition() {
        val pos = Position.create(2, 3)
        val target = Boundable.create(Position.OFFSET_1X1, Size.ONE)

        target.withPosition(pos) shouldBe Boundable.create(pos, Size.ONE)
    }

    @Test
    fun shouldProperlyCopyWithRelativePosition() {
        val pos = Position.create(2, 3)
        val target = Boundable.create(Position.OFFSET_1X1, Size.ONE)

        target.withRelativePosition(pos) shouldBe Boundable.create(pos + Position.OFFSET_1X1, Size.ONE)
    }

    @Test
    fun shouldProperlyCopyWithSize() {
        val size = Size.create(2, 3)
        val target = Boundable.create(Position.OFFSET_1X1, Size.ONE)

        target.withSize(size) shouldBe Boundable.create(Position.OFFSET_1X1, size)
    }

    @Test
    fun shouldProperlyCopyWithRelativeSize() {
        val size = Size.create(2, 3)
        val target = Boundable.create(Position.OFFSET_1X1, Size.ONE)

        target.withRelativeSize(size) shouldBe Boundable.create(Position.OFFSET_1X1, Size.ONE + size)
    }

}
