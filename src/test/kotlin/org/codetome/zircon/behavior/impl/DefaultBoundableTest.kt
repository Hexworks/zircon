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

    companion object {
        val DEFAULT_COLS = 10
        val DEFAULT_ROWS = 10
    }
}