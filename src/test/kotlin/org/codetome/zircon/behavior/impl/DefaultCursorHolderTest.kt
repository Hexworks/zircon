package org.codetome.zircon.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.junit.Before
import org.junit.Test

class DefaultCursorHolderTest {

    lateinit var target: DefaultCursorHolder

    @Before
    fun setUp() {
        target = DefaultCursorHolder()
    }

    @Test
    fun defaultCursorPositionShouldBeDefaultPosition() {
        assertThat(target.getCursorPosition())
                .isEqualTo(Position.DEFAULT_POSITION)
    }

    @Test
    fun shouldSetPositionCorrectly() {
        target.setCursorPosition(Position.OFFSET_1x1)

        assertThat(target.getCursorPosition())
                .isEqualTo(Position.OFFSET_1x1)
    }

    @Test
    fun defaultCursorVisibilityShouldBeTrue() {
        assertThat(target.isCursorVisible())
                .isTrue()
    }

    @Test
    fun shouldSetVisibilityCorrectly() {
        target.setCursorVisible(false)

        assertThat(target.isCursorVisible())
                .isFalse()
    }


}