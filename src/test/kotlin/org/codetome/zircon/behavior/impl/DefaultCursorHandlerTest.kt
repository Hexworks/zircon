package org.codetome.zircon.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.junit.Before
import org.junit.Test

class DefaultCursorHandlerTest {

    lateinit var target: DefaultCursorHandler

    @Before
    fun setUp() {
        target = DefaultCursorHandler(SIZE)
    }

    @Test
    fun defaultCursorPositionShouldBeDefaultPosition() {
        assertThat(target.getCursorPosition())
                .isEqualTo(Position.DEFAULT_POSITION)
    }

    @Test
    fun shouldSetPositionCorrectly() {
        target.putCursorAt(Position.OFFSET_1x1)

        assertThat(target.getCursorPosition())
                .isEqualTo(Position.OFFSET_1x1)
    }

    @Test
    fun defaultCursorVisibilityShouldBeFalse() {
        assertThat(target.isCursorVisible())
                .isFalse()
    }

    @Test
    fun shouldSetVisibilityCorrectly() {
        target.setCursorVisible(false)

        assertThat(target.isCursorVisible())
                .isFalse()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnNegativeCursorRow() {
        target.putCursorAt(Position.DEFAULT_POSITION.withRow(-1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnNegativeCursorColumn() {
        target.putCursorAt(Position.DEFAULT_POSITION.withColumn(-1))
    }

    companion object {
        val SIZE = Size.of(5, 5)
    }
}