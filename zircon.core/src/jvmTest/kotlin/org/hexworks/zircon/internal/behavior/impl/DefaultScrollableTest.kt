package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.junit.Before
import org.junit.Test

class DefaultScrollableTest {

    lateinit var target: DefaultScrollable

    @Before
    fun setUp() {
        target = DefaultScrollable(
                visibleSize = VISIBLE_SPACE_SIZE,
                initialActualSize = VIRTUAL_SPACE_SIZE)
    }

    @Test
    fun shouldProperlyReportVirtualSpaceSize() {
        assertThat(target.actualSize)
                .isEqualTo(VIRTUAL_SPACE_SIZE)
    }

    @Test
    fun shouldProperlyScrollOneRightWhenCanScroll() {
        target.scrollOneRight()

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(1, 0))
    }

    @Test
    fun shouldProperlyScrollOneLeftWhenCanScroll() {
        target.scrollOneRight()
        target.scrollOneRight()
        target.scrollOneLeft()

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(1, 0))
    }

    @Test
    fun shouldProperlyScrollOneDownWhenCanScroll() {
        target.scrollOneDown()

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(0, 1))
    }

    @Test
    fun shouldProperlyScrollOneUpWhenCanScroll() {
        target.scrollOneDown()
        target.scrollOneDown()
        target.scrollOneUp()

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(0, 1))
    }

    @Test
    fun shouldProperlyScrollRightWhenCanScroll() {
        target.scrollRightBy(5)

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(5, 0))
    }

    @Test
    fun shouldProperlyScrollLeftWhenCanScroll() {
        target.scrollRightBy(5)
        target.scrollLeftBy(3)

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(2, 0))
    }

    @Test
    fun shouldProperlyScrollDownWhenCanScroll() {
        target.scrollDownBy(5)

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(0, 5))
    }

    @Test
    fun shouldProperlyScrollUpWhenCanScroll() {
        target.scrollDownBy(5)
        target.scrollUpBy(3)

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(0, 2))
    }

    @Test
    fun shouldProperlyScrollRightToMaxWhenScrollingTooMuch() {
        target.scrollRightBy(Int.MAX_VALUE)

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(5, 0))
    }

    @Test
    fun shouldProperlyScrollLeftToMaxWhenScrollingTooMuch() {
        target.scrollRightBy(5)
        target.scrollLeftBy(Int.MAX_VALUE)

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(0, 0))
    }

    @Test
    fun shouldProperlyScrollUpToMaxWhenScrollingTooMuch() {
        target.scrollDownBy(5)
        target.scrollUpBy(Int.MAX_VALUE)

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(0, 0))
    }

    @Test
    fun shouldProperlyScrollDownToMaxWhenScrollingTooMuch() {
        target.scrollDownBy(Int.MAX_VALUE)

        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(0, 5))
    }

    @Test
    fun shouldProperlyScrollToProvided3dPosition() {
        val newPosition = Position.create(5, 5)
        target.scrollTo(newPosition)

        assertThat(target.visibleOffset)
                .isEqualTo(newPosition)
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

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTryingToScrollOutsideOfTheActualSize() {
        target.scrollTo(Position.create(15, 0))
    }

    companion object {
        val VIRTUAL_SPACE_SIZE = Size.create(10, 10)
        val VISIBLE_SPACE_SIZE = Size.create(5, 5)
    }

}
