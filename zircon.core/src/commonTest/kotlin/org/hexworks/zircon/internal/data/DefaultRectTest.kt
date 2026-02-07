package org.hexworks.zircon.internal.data

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class DefaultRectTest {

    @Test
    fun shouldProperlyPlusTwoRects() {
        val result = Rect.create(Position.create(1, 1), Size.create(1, 1))
            .plus(Rect.create(Position.create(2, 2), Size.create(2, 2)))

        result shouldBe Rect.create(Position.create(3, 3), Size.create(3, 3))
    }

    @Test
    fun shouldProperlyMinusTwoRects() {
        val result = Rect.create(Position.create(3, 3), Size.create(3, 3))
            .minus(Rect.create(Position.create(2, 2), Size.create(2, 2)))

        result shouldBe Rect.create(Position.create(1, 1), Size.create(1, 1))
    }

    @Test
    fun shouldProperlyCopyWithPosition() {
        val pos = Position.create(2, 3)
        val target = Rect.create(Position.offset1x1(), Size.one())

        target.withPosition(pos) shouldBe Rect.create(pos, Size.one())
    }

    @Test
    fun shouldProperlyCopyWithRelativePosition() {
        val pos = Position.create(2, 3)
        val target = Rect.create(Position.offset1x1(), Size.one())

        target.withRelativePosition(pos) shouldBe Rect.create(pos + Position.offset1x1(), Size.one())
    }

    @Test
    fun shouldProperlyCopyWithSize() {
        val size = Size.create(2, 3)
        val target = Rect.create(Position.offset1x1(), Size.one())

        target.withSize(size) shouldBe Rect.create(Position.offset1x1(), size)
    }

    @Test
    fun shouldProperlyCopyWithRelativeSize() {
        val size = Size.create(2, 3)
        val target = Rect.create(Position.offset1x1(), Size.one())

        target.withRelativeSize(size) shouldBe Rect.create(Position.offset1x1(), Size.one() + size)
    }

}
