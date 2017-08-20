package org.codetome.zircon.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.api.LayerBuilder
import org.junit.Before
import org.junit.Test

class DefaultBoundableTest {

    lateinit var target: DefaultBoundable

    @Before
    fun setUp() {
        target = DefaultBoundable(
                size = TARGET_SIZE,
                position = Position.DEFAULT_POSITION)
    }

    @Test
    fun shouldContainPositionWhenThereIsNoOffsetAndSizeIsBiggerThanPos() {
        assertThat(target.containsPosition(Position.DEFAULT_POSITION))
                .isTrue()
    }

    @Test
    fun shouldNotContainPositionWhenPositionIsOutOfBounds() {
        assertThat(target.containsPosition(target.getPosition()
                        .withRelative(Position.of(TARGET_SIZE.rows, TARGET_SIZE.columns))))
                .isFalse()
    }

    @Test
    fun shouldKnowItsSizeCorrectly() {
        assertThat(target.getBoundableSize())
                .isEqualTo(TARGET_SIZE)
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundable() {
        assertThat(target.intersects(DefaultBoundable(TARGET_SIZE)))
                .isTrue()
    }

    @Test
    fun shouldNotIntersectWhenIntersectIsCalledWithNonIntersectingBoundable() {
        assertThat(target.intersects(LayerBuilder.newBuilder()
                .offset(NON_INTERSECTING_OFFSET)
                .build()))
                .isFalse()
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundableWithOffset() {
        assertThat(target.intersects(LayerBuilder.newBuilder()
                .offset(INTERSECTION_OFFSET)
                .size(Size.ONE)
                .build()))
                .isTrue()
    }

    @Test
    fun shouldContainBoundableWhenCalledWithContainedBoundable() {
        assertThat(target.containsBoundable(DefaultBoundable(Size.ONE)))
                .isTrue()
    }

    @Test
    fun shouldNotContainBoundableWhenCalledWithNonContainedBoundable() {
        assertThat(target.containsBoundable(DefaultBoundable(Size(100, 100))))
                .isFalse()
    }

    companion object {
        val DEFAULT_COLS = 10
        val DEFAULT_ROWS = 10
        val TARGET_SIZE = Size(DEFAULT_COLS, DEFAULT_ROWS)
        val INTERSECTION_OFFSET = Position.OFFSET_1x1
        val NON_INTERSECTING_OFFSET = Position(20, 20)
    }
}