package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.behavior.extensions.withPosition
import org.hexworks.zircon.api.behavior.extensions.withSize
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.extensions.withRelativePosition
import org.hexworks.zircon.api.behavior.extensions.withRelativeSize
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class DefaultRectTest {

    @Test
    fun shouldProperlyPlusTwoRects() {
        val result = Boundable.create(Position(1, 1), Size(1, 1))
            .plus(Boundable.create(Position(2, 2), Size(2, 2)))

        result shouldBe Boundable.create(Position(3, 3), Size(3, 3))
    }

    @Test
    fun shouldProperlyMinusTwoRects() {
        val result = Boundable.create(Position(3, 3), Size(3, 3))
            .minus(Boundable.create(Position(2, 2), Size(2, 2)))

        result shouldBe Boundable.create(Position(1, 1), Size(1, 1))
    }

    @Test
    fun shouldProperlyCopyWithPosition() {
        val pos = Position(2, 3)
        val target = Boundable.create(Position.OFFSET_1X1, Size.ONE)

        target.withPosition(pos) shouldBe Boundable.create(pos, Size.ONE)
    }

    @Test
    fun shouldProperlyCopyWithRelativePosition() {
        val pos = Position(2, 3)
        val target = Boundable.create(Position.OFFSET_1X1, Size.ONE)

        target.withRelativePosition(pos) shouldBe Boundable.create(pos + Position.OFFSET_1X1, Size.ONE)
    }

    @Test
    fun shouldProperlyCopyWithSize() {
        val size = Size(2, 3)
        val target = Boundable.create(Position.OFFSET_1X1, Size.ONE)

        target.withSize(size) shouldBe Boundable.create(Position.OFFSET_1X1, size)
    }

    @Test
    fun shouldProperlyCopyWithRelativeSize() {
        val size = Size(2, 3)
        val target = Boundable.create(Position.OFFSET_1X1, Size.ONE)

        target.withRelativeSize(size) shouldBe Boundable.create(Position.OFFSET_1X1, Size.ONE + size)
    }

}
