package org.hexworks.zircon.internal.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.junit.Test

class DefaultRectTest {

    @Test
    fun shouldProperlyPlusTwoRects() {
        assertThat(
                Rect.create(Position.create(1, 1), Size.create(1, 1))
                        .plus(Rect.create(Position.create(2, 2), Size.create(2, 2))))
                .isEqualTo(Rect.create(Position.create(3, 3), Size.create(3, 3)))
    }

    @Test
    fun shouldProperlyMinusTwoRects() {
        assertThat(
                Rect.create(Position.create(3, 3), Size.create(3, 3))
                        .minus(Rect.create(Position.create(2, 2), Size.create(2, 2))))
                .isEqualTo(Rect.create(Position.create(1, 1), Size.create(1, 1)))
    }

    @Test
    fun shouldProperlyCopyWithPosition() {
        val pos = Position.create(2, 3)
        val target = Rect.create(Position.offset1x1(), Size.one())

        assertThat(target.withPosition(pos))
                .isEqualTo(Rect.create(pos, Size.one()))
    }

    @Test
    fun shouldProperlyCopyWithRelativePosition() {
        val pos = Position.create(2, 3)
        val target = Rect.create(Position.offset1x1(), Size.one())

        assertThat(target.withRelativePosition(pos))
                .isEqualTo(Rect.create(pos + Position.offset1x1(), Size.one()))
    }

    @Test
    fun shouldProperlyCopyWithSize() {
        val size = Size.create(2, 3)
        val target = Rect.create(Position.offset1x1(), Size.one())

        assertThat(target.withSize(size))
                .isEqualTo(Rect.create(Position.offset1x1(), size))
    }

    @Test
    fun shouldProperlyCopyWithRelativeSize() {
        val size = Size.create(2, 3)
        val target = Rect.create(Position.offset1x1(), Size.one())

        assertThat(target.withRelativeSize(size))
                .isEqualTo(Rect.create(Position.offset1x1(), Size.one() + size))
    }
}
