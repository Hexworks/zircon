package org.codetome.zircon.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.graphics.layer.DefaultLayer
import org.codetome.zircon.terminal.Size
import org.junit.Before
import org.junit.Test

class DefaultBoundableTest {

    lateinit var target: DefaultBoundable

    @Before
    fun setUp() {
        target = DefaultBoundable(
                size = DEFAULT_SIZE)
    }

    @Test
    fun shouldContainPositionWhenThereIsNoOffsetAndSizeIsBiggerThanPos() {
        assertThat(target.containsPosition(Position.DEFAULT_POSITION))
                .isTrue()
    }

    @Test
    fun shouldNotContainPositionWhenThereIsNoOffsetAndSizeIsSmallerThanPos() {
        assertThat(target.containsPosition(Position(100, 100)))
                .isFalse()
    }

    @Test
    fun shouldKnowItsSizeCorrectly() {
        assertThat(target.getBoundableSize())
                .isEqualTo(DEFAULT_SIZE)
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundable() {
        assertThat(target.intersects(DefaultBoundable(DEFAULT_SIZE)))
                .isTrue()
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithNonIntersectingBoundable() {
        assertThat(target.intersects(DefaultLayer(
                offset = NON_INTERSECTING_OFFSET)))
                .isTrue()
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundableWithOffset() {
        assertThat(target.intersects(DefaultLayer(
                offset = INTERSECTION_OFFSET)))
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
        val DEFAULT_SIZE = Size(DEFAULT_COLS, DEFAULT_ROWS)
        val INTERSECTION_OFFSET = Position.DEFAULT_POSITION
        val NON_INTERSECTING_OFFSET = Position(20, 20)
    }
}