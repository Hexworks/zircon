package org.hexworks.zircon.internal.behavior.impl

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test

// TODO: fixme
@Ignore
class DefaultScrollableTest {

    lateinit var target: DefaultScrollable

    @BeforeTest
    fun setUp() {
        target = DefaultScrollable(
            initialVisibleSize = VISIBLE_SPACE_SIZE,
            initialActualSize = VIRTUAL_SPACE_SIZE
        )
    }

    @Test
    fun shouldProperlyReportVirtualSpaceSize() {
        target.actualSize shouldBe VIRTUAL_SPACE_SIZE
    }

    @Test
    fun shouldProperlyScrollOneRightWhenCanScroll() {
        target.scrollOneRight()

        target.visibleOffset shouldBe Position.create(1, 0)
    }

    @Test
    fun shouldProperlyScrollOneLeftWhenCanScroll() {
        target.scrollOneRight()
        target.scrollOneRight()
        target.scrollOneLeft()

        target.visibleOffset shouldBe Position.create(1, 0)
    }

    @Test
    fun shouldProperlyScrollOneDownWhenCanScroll() {
        target.scrollOneDown()

        target.visibleOffset shouldBe Position.create(0, 1)
    }

    @Test
    fun shouldProperlyScrollOneUpWhenCanScroll() {
        target.scrollOneDown()
        target.scrollOneDown()
        target.scrollOneUp()

        target.visibleOffset shouldBe Position.create(0, 1)
    }

    @Test
    fun shouldProperlyScrollRightWhenCanScroll() {
        target.scrollRightBy(5)

        target.visibleOffset shouldBe Position.create(5, 0)
    }

    @Test
    fun shouldProperlyScrollLeftWhenCanScroll() {
        target.scrollRightBy(5)
        target.scrollLeftBy(3)

        target.visibleOffset shouldBe Position.create(2, 0)
    }

    @Test
    fun shouldProperlyScrollDownWhenCanScroll() {
        target.scrollDownBy(5)

        target.visibleOffset shouldBe Position.create(0, 5)
    }

    @Test
    fun shouldProperlyScrollUpWhenCanScroll() {
        target.scrollDownBy(5)
        target.scrollUpBy(3)

        target.visibleOffset shouldBe Position.create(0, 2)
    }

    @Test
    fun shouldProperlyScrollRightToMaxWhenScrollingTooMuch() {
        target.scrollRightBy(Int.MAX_VALUE)

        target.visibleOffset shouldBe Position.create(5, 0)
    }

    @Test
    fun shouldProperlyScrollLeftToMaxWhenScrollingTooMuch() {
        target.scrollRightBy(5)
        target.scrollLeftBy(Int.MAX_VALUE)

        target.visibleOffset shouldBe Position.create(0, 0)
    }

    @Test
    fun shouldProperlyScrollUpToMaxWhenScrollingTooMuch() {
        target.scrollDownBy(5)
        target.scrollUpBy(Int.MAX_VALUE)

        target.visibleOffset shouldBe Position.create(0, 0)
    }

    @Test
    fun shouldProperlyScrollDownToMaxWhenScrollingTooMuch() {
        target.scrollDownBy(Int.MAX_VALUE)

        target.visibleOffset shouldBe Position.create(0, 5)
    }

    @Test
    fun shouldProperlyScrollToProvided3dPosition() {
        val newPosition = Position.create(5, 5)
        target.scrollTo(newPosition)

        target.visibleOffset shouldBe newPosition
    }

    @Test
    fun shouldThrowExceptionWhenTryingToScrollRightByNegativeAmount() {
        shouldThrow<IllegalArgumentException> {
            target.scrollRightBy(-1)
        }
    }

    @Test
    fun shouldThrowExceptionWhenTryingToScrollLeftByNegativeAmount() {
        shouldThrow<IllegalArgumentException> {
            target.scrollLeftBy(-1)
        }
    }

    @Test
    fun shouldThrowExceptionWhenTryingToScrollUpByNegativeAmount() {
        shouldThrow<IllegalArgumentException> {
            target.scrollUpBy(-1)
        }
    }

    @Test
    fun shouldThrowExceptionWhenTryingToScrollDownByNegativeAmount() {
        shouldThrow<IllegalArgumentException> {
            target.scrollDownBy(-1)
        }
    }

    @Test
    fun shouldThrowExceptionWhenTryingToScrollOutsideOfTheActualSize() {
        shouldThrow<IllegalArgumentException> {
            target.scrollTo(Position.create(15, 0))
        }
    }

    companion object {
        val VIRTUAL_SPACE_SIZE = Size.create(10, 10)
        val VISIBLE_SPACE_SIZE = Size.create(5, 5)
    }

}
