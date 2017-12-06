package org.codetome.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.beta.component.Position3D
import org.codetome.zircon.api.beta.component.Size3D
import org.junit.Before
import org.junit.Test

class DefaultScrollable3DTest {

    lateinit var target: DefaultScrollable3D

    @Before
    fun setUp() {
        target = DefaultScrollable3D(
                visibleSpaceSize = VISIBLE_SPACE_SIZE,
                virtualSpaceSize = VIRTUAL_SPACE_SIZE)
    }

    @Test
    fun shouldProperlyReportVirtualSpaceSize() {
        assertThat(target.getVirtualSpaceSize())
                .isEqualTo(VIRTUAL_SPACE_SIZE)
    }

    @Test
    fun shouldProperlyScrollOneRightWhenCanScroll() {
        target.scrollOneRight()

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(1, 0, 0))
    }

    @Test
    fun shouldProperlyScrollOneLeftWhenCanScroll() {
        target.scrollOneRight()
        target.scrollOneRight()
        target.scrollOneLeft()

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(1, 0, 0))
    }

    @Test
    fun shouldProperlyScrollOneForwardWhenCanScroll() {
        target.scrollOneForward()

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 1, 0))
    }

    @Test
    fun shouldProperlyScrollOneBackwardWhenCanScroll() {
        target.scrollOneForward()
        target.scrollOneForward()
        target.scrollOneBackward()

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 1, 0))
    }

    @Test
    fun shouldProperlyScrollOneUpWhenCanScroll() {
        target.scrollOneUp()

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 0, 1))
    }

    @Test
    fun shouldProperlyScrollOneDownWhenCanScroll() {
        target.scrollOneUp()
        target.scrollOneUp()
        target.scrollOneDown()

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 0, 1))
    }

    @Test
    fun shouldProperlyScrollRightWhenCanScroll() {
        target.scrollRightBy(5)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(5, 0, 0))
    }

    @Test
    fun shouldProperlyScrollLeftWhenCanScroll() {
        target.scrollRightBy(5)
        target.scrollLeftBy(3)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(2, 0, 0))
    }

    @Test
    fun shouldProperlyScrollForwardWhenCanScroll() {
        target.scrollForwardBy(5)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 5, 0))
    }

    @Test
    fun shouldProperlyScrollBackwardWhenCanScroll() {
        target.scrollForwardBy(5)
        target.scrollBackwardBy(3)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 2, 0))
    }

    @Test
    fun shouldProperlyScrollUpWhenCanScroll() {
        target.scrollUpBy(5)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 0, 5))
    }

    @Test
    fun shouldProperlyScrollDownWhenCanScroll() {
        target.scrollUpBy(5)
        target.scrollDownBy(3)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 0, 2))
    }

    @Test
    fun shouldProperlyScrollRightToMaxWhenScrollingTooMuch() {
        target.scrollRightBy(Int.MAX_VALUE)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(5, 0, 0))
    }

    @Test
    fun shouldProperlyScrollLeftToMaxWhenScrollingTooMuch() {
        target.scrollRightBy(5)
        target.scrollLeftBy(Int.MAX_VALUE)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 0, 0))
    }

    @Test
    fun shouldProperlyScrollForwardToMaxWhenScrollingTooMuch() {
        target.scrollForwardBy(Int.MAX_VALUE)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 5, 0))
    }

    @Test
    fun shouldProperlyScrollBackwardToMaxWhenScrollingTooMuch() {
        target.scrollForwardBy(5)
        target.scrollBackwardBy(Int.MAX_VALUE)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 0, 0))
    }

    @Test
    fun shouldProperlyScrollDownToMaxWhenScrollingTooMuch() {
        target.scrollUpBy(5)
        target.scrollDownBy(Int.MAX_VALUE)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 0, 0))
    }

    @Test
    fun shouldProperlyScrollUpToMaxWhenScrollingTooMuch() {
        target.scrollUpBy(Int.MAX_VALUE)

        assertThat(target.getVisibleOffset())
                .isEqualTo(Position3D.of(0, 0, 5))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTryingToScrollRightByNegativeAmount() {
        target.scrollRightBy(-1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTryingToScrollLeftByNegativeAmount() {
        target.scrollLeftBy(-1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTryingToScrollUpByNegativeAmount() {
        target.scrollUpBy(-1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTryingToScrollDownByNegativeAmount() {
        target.scrollDownBy(-1)
    }

    companion object {
        val VIRTUAL_SPACE_SIZE = Size3D.of(
                columns = 10,
                rows = 10,
                levels = 10)
        val VISIBLE_SPACE_SIZE = Size3D.of(
                columns = 5,
                rows = 5,
                levels = 5)
    }
}