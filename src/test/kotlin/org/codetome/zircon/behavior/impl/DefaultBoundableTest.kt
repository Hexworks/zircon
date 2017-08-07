package org.codetome.zircon.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.terminal.Size
import org.junit.Before
import org.junit.Test

class DefaultBoundableTest {

    lateinit var target: DefaultBoundable

    @Before
    fun setUp() {
        target = DefaultBoundable(
                offset = Position.DEFAULT_POSITION,
                size = Size(DEFAULT_COLS, DEFAULT_ROWS))
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
    fun shouldNotContainPositionWhenOutOfBoundsWithOffset() {
        target = DefaultBoundable(OFFSET_2X2, Size(DEFAULT_ROWS, DEFAULT_COLS))

        assertThat(target.containsPosition(Position.DEFAULT_POSITION))
                .isFalse()
    }

    @Test
    fun shouldContainPositionWhenTopLeftOfOffset() {
        target = DefaultBoundable(OFFSET_2X2, Size(DEFAULT_ROWS, DEFAULT_COLS))

        assertThat(target.containsPosition(OFFSET_2X2))
                .isTrue()
    }

    companion object {
        val OFFSET_2X2 = Position(2, 2)

        val DEFAULT_COLS = 10
        val DEFAULT_ROWS = 10
    }
}