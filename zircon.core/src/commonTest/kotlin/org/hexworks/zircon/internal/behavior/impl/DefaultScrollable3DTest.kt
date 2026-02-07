package org.hexworks.zircon.internal.behavior.impl

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test

// TODO: fixme
@Ignore
class DefaultScrollable3DTest {

    lateinit var target: DefaultScrollable3D

    @BeforeTest
    fun setUp() {
        target = DefaultScrollable3D(
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

        target.visibleOffset shouldBe Position3D.create(1, 0, 0)
    }

    @Test
    fun shouldProperlyScrollOneLeftWhenCanScroll() {
        target.scrollOneRight()
        target.scrollOneRight()
        target.scrollOneLeft()

        target.visibleOffset shouldBe Position3D.create(1, 0, 0)
    }

    @Test
    fun shouldProperlyScrollOneForwardWhenCanScroll() {
        target.scrollOneForward()

        target.visibleOffset shouldBe Position3D.create(0, 1, 0)
    }

    @Test
    fun shouldProperlyScrollOneBackwardWhenCanScroll() {
        target.scrollOneForward()
        target.scrollOneForward()
        target.scrollOneBackward()

        target.visibleOffset shouldBe Position3D.create(0, 1, 0)
    }

    @Test
    fun shouldProperlyScrollOneUpWhenCanScroll() {
        target.scrollOneUp()

        target.visibleOffset shouldBe Position3D.create(0, 0, 1)
    }

    @Test
    fun shouldProperlyScrollOneDownWhenCanScroll() {
        target.scrollOneUp()
        target.scrollOneUp()
        target.scrollOneDown()

        target.visibleOffset shouldBe Position3D.create(0, 0, 1)
    }

    @Test
    fun shouldProperlyScrollRightWhenCanScroll() {
        target.scrollRightBy(5)

        target.visibleOffset shouldBe Position3D.create(5, 0, 0)
    }

    @Test
    fun shouldProperlyScrollLeftWhenCanScroll() {
        target.scrollRightBy(5)
        target.scrollLeftBy(3)

        target.visibleOffset shouldBe Position3D.create(2, 0, 0)
    }

    @Test
    fun shouldProperlyScrollForwardWhenCanScroll() {
        target.scrollForwardBy(5)

        target.visibleOffset shouldBe Position3D.create(0, 5, 0)
    }

    @Test
    fun shouldProperlyScrollBackwardWhenCanScroll() {
        target.scrollForwardBy(5)
        target.scrollBackwardBy(3)

        target.visibleOffset shouldBe Position3D.create(0, 2, 0)
    }

    @Test
    fun shouldProperlyScrollUpWhenCanScroll() {
        target.scrollUpBy(5)

        target.visibleOffset shouldBe Position3D.create(0, 0, 5)
    }

    @Test
    fun shouldProperlyScrollDownWhenCanScroll() {
        target.scrollUpBy(5)
        target.scrollDownBy(3)

        target.visibleOffset shouldBe Position3D.create(0, 0, 2)
    }

    @Test
    fun shouldProperlyScrollRightToMaxWhenScrollingTooMuch() {
        target.scrollRightBy(Int.MAX_VALUE)

        target.visibleOffset shouldBe Position3D.create(5, 0, 0)
    }

    @Test
    fun shouldProperlyScrollLeftToMaxWhenScrollingTooMuch() {
        target.scrollRightBy(5)
        target.scrollLeftBy(Int.MAX_VALUE)

        target.visibleOffset shouldBe Position3D.create(0, 0, 0)
    }

    @Test
    fun shouldProperlyScrollForwardToMaxWhenScrollingTooMuch() {
        target.scrollForwardBy(Int.MAX_VALUE)

        target.visibleOffset shouldBe Position3D.create(0, 5, 0)
    }

    @Test
    fun shouldProperlyScrollBackwardToMaxWhenScrollingTooMuch() {
        target.scrollForwardBy(5)
        target.scrollBackwardBy(Int.MAX_VALUE)

        target.visibleOffset shouldBe Position3D.create(0, 0, 0)
    }

    @Test
    fun shouldProperlyScrollDownToMaxWhenScrollingTooMuch() {
        target.scrollUpBy(5)
        target.scrollDownBy(Int.MAX_VALUE)

        target.visibleOffset shouldBe Position3D.create(0, 0, 0)
    }

    @Test
    fun shouldProperlyScrollUpToMaxWhenScrollingTooMuch() {
        target.scrollUpBy(Int.MAX_VALUE)

        target.visibleOffset shouldBe Position3D.create(0, 0, 5)
    }

    @Test
    fun shouldProperlyScrollToProvided3dPosition() {
        val newPosition = Position3D.create(5, 5, 0)
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
            target.scrollTo(Position3D.create(15, 0, 0))
        }
    }

    companion object {
        val VIRTUAL_SPACE_SIZE = Size3D.create(
            xLength = 10,
            yLength = 10,
            zLength = 10
        )
        val VISIBLE_SPACE_SIZE = Size3D.create(
            xLength = 5,
            yLength = 5,
            zLength = 5
        )
    }
}
