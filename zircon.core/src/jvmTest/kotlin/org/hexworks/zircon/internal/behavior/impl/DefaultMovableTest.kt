package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.junit.Before
import org.junit.Test

class DefaultMovableTest {

    lateinit var target: DefaultMovable

    @Before
    fun setUp() {
        target = DefaultMovable(
            size = TARGET_SIZE_10x10,
            position = Position.defaultPosition()
        )
    }

    @Test
    fun shouldContainPositionWhenThereIsNoOffsetAndSizeIsBiggerThanPos() {
        assertThat(target.containsPosition(Position.defaultPosition()))
            .isTrue()
    }

    @Test
    fun shouldNotContainPositionWhenPositionIsOutOfBounds() {
        assertThat(
            target.containsPosition(
                target.position
                    .withRelative(Position.create(TARGET_SIZE_10x10.height, TARGET_SIZE_10x10.width))
            )
        )
            .isFalse()
    }

    @Test
    fun shouldKnowItsSizeCorrectly() {
        assertThat(target.size)
            .isEqualTo(TARGET_SIZE_10x10)
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundable() {
        assertThat(target.intersects(DefaultMovable(TARGET_SIZE_10x10)))
            .isTrue()
    }

    @Test
    fun shouldNotIntersectWhenIntersectIsCalledWithNonIntersectingBoundable() {
        assertThat(
            target.intersects(
                layer {
                    size = TARGET_SIZE_10x10
                    offset = NON_INTERSECTING_OFFSET_20x20
                }
            )
        )
            .isFalse()
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundableWithOffset() {
        assertThat(
            target.intersects(
                layer {
                    offset = INTERSECTION_OFFSET_1x1
                    size = Size.one()
                }
            )
        )
            .isTrue()
    }

    @Test
    fun shouldContainBoundableWhenCalledWithContainedBoundable() {
        assertThat(target.containsBoundable(DefaultMovable(Size.one())))
            .isTrue()
    }

    @Test
    fun shouldNotContainBoundableWhenCalledWithNonContainedBoundable() {
        assertThat(target.containsBoundable(DefaultMovable(Size.create(100, 100))))
            .isFalse()
    }

    companion object {
        const val DEFAULT_COLS_10 = 10
        const val DEFAULT_ROWS_10 = 10
        val TARGET_SIZE_10x10 = Size.create(DEFAULT_COLS_10, DEFAULT_ROWS_10)
        val INTERSECTION_OFFSET_1x1 = Position.offset1x1()
        val NON_INTERSECTING_OFFSET_20x20 = Position.create(20, 20)
    }
}
