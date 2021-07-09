package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.junit.Before
import org.junit.Test

class DefaultMovableTest {

    lateinit var target: DefaultMovable

    @Before
    fun setUp() {
        target = DefaultMovable(
            size = TARGET_SIZE,
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
                    .withRelative(Position.create(TARGET_SIZE.height, TARGET_SIZE.width))
            )
        )
            .isFalse()
    }

    @Test
    fun shouldKnowItsSizeCorrectly() {
        assertThat(target.size)
            .isEqualTo(TARGET_SIZE)
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundable() {
        assertThat(target.intersects(DefaultMovable(TARGET_SIZE)))
            .isTrue()
    }

    @Test
    fun shouldNotIntersectWhenIntersectIsCalledWithNonIntersectingBoundable() {
        assertThat(
            target.intersects(
                LayerBuilder.newBuilder()
                    .withOffset(NON_INTERSECTING_OFFSET)
                    .build()
            )
        )
            .isFalse()
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundableWithOffset() {
        assertThat(
            target.intersects(
                LayerBuilder.newBuilder()
                    .withOffset(INTERSECTION_OFFSET)
                    .withSize(Size.one())
                    .build()
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
        val DEFAULT_COLS = 10
        val DEFAULT_ROWS = 10
        val TARGET_SIZE = Size.create(DEFAULT_COLS, DEFAULT_ROWS)
        val INTERSECTION_OFFSET = Position.offset1x1()
        val NON_INTERSECTING_OFFSET = Position.create(20, 20)
    }
}
