package org.hexworks.zircon.api.data

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.zircon.api.data.extensions.fetchBottomLeftPosition
import org.hexworks.zircon.api.data.extensions.fetchBottomRightPosition
import org.hexworks.zircon.api.data.extensions.fetchBoundingBoxPositions
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.data.extensions.fetchTopLeftPosition
import org.hexworks.zircon.api.data.extensions.fetchTopRightPosition
import org.hexworks.zircon.api.data.extensions.isSizeNotUnknown
import org.hexworks.zircon.api.data.extensions.isSizeUnknown
import org.hexworks.zircon.api.data.extensions.max
import org.hexworks.zircon.api.data.extensions.min
import org.hexworks.zircon.api.data.extensions.with
import org.hexworks.zircon.api.data.extensions.withHeight
import org.hexworks.zircon.api.data.extensions.withRelative
import org.hexworks.zircon.api.data.extensions.withRelativeHeight
import org.hexworks.zircon.api.data.extensions.withRelativeWidth
import org.hexworks.zircon.api.data.extensions.withWidth
import kotlin.test.Test

class SizeTest {

    @Test
    fun shouldNotAllowToCreateSizeWithNegativeWidth() {
        shouldThrow<IllegalArgumentException> {
            Size(-1, 0)
        }
    }

    @Test
    fun shouldNotAllowToCreateSizeWithNegativeHeight() {
        shouldThrow<IllegalArgumentException> {
            Size(0, -1)
        }
    }

    @Test
    fun shouldProperlyDestructureSize() {
        val (width, height) = Size(2, 3)

        width shouldBe 2
        height shouldBe 3
    }

    @Test
    fun shouldBeUnknownWhenUnknown() {
        Size.UNKNOWN.isSizeUnknown shouldBe true
    }

    @Test
    fun shouldNotBeUnknownWhenNotUnknown() {
        Size(1, 2).isSizeNotUnknown shouldBe true
    }

    @Test
    fun shouldProperlyCalculateGreaterThan() {
        Size(1, 2) shouldBeGreaterThan Size(1, 1)
    }

    @Test
    fun shouldProperlyCalculateLessThan() {
        Size(1, 1) shouldBeLessThan Size(1, 2)
    }

    @Test
    fun shouldProperlyCalculateEqualByComparingTo() {
        Size(1, 2) shouldBeEqualComparingTo Size(2, 1)
    }

    @Test
    fun shouldCreateNewSizeWithProperXLengthWhenWithXLengthIsCalled() {
        Size(
            width = Int.MAX_VALUE,
            height = EXPECTED_ROW
        ).withWidth(EXPECTED_COL) shouldBe EXPECTED_TERMINAL_SIZE
    }

    @Test
    fun shouldProperlyAddTwoSizes() {
        (Size(1, 2) + Size(2, 1)) shouldBe Size(3, 3)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeXLengthWhenWithRelativeXLengthIsCalled() {
        Size(
            width = EXPECTED_COL - 1,
            height = EXPECTED_ROW
        ).withRelativeWidth(1) shouldBe EXPECTED_TERMINAL_SIZE
    }

    @Test
    fun shouldCreateNewSizeWithProperYLengthWhenWithYLengthIsCalled() {
        Size(
            width = EXPECTED_COL,
            height = Int.MAX_VALUE
        ).withHeight(EXPECTED_ROW) shouldBe EXPECTED_TERMINAL_SIZE
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeYLengthWhenWithRelativeYLengthIsCalled() {
        Size(
            width = EXPECTED_COL,
            height = EXPECTED_ROW - 1
        ).withRelativeHeight(1) shouldBe EXPECTED_TERMINAL_SIZE
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativesWhenWithRelativeIsCalled() {
        Size(
            width = EXPECTED_COL - 1,
            height = EXPECTED_ROW - 1
        ).withRelative(Size(1, 1)) shouldBe EXPECTED_TERMINAL_SIZE
    }

    @Test
    fun shouldFetchPositionsInCorrectIterationOrder() {
        Size(2, 2).fetchPositions().toList() shouldBe listOf(
            Position(x = 0, y = 0),
            Position(x = 1, y = 0),
            Position(x = 0, y = 1),
            Position(x = 1, y = 1)
        )
    }

    @Test
    fun shouldReturnItselfWhenWithXLengthIsCalledAndXLengthIsTheSame() {
        val target = Size(60, 30)
        val result = target.withWidth(target.width)
        target shouldBeSameInstanceAs result
    }

    @Test
    fun shouldReturnItselfWhenWithYLengthIsCalledAndYLengthIsTheSame() {
        val target = Size(60, 30)
        val result = target.withHeight(target.height)
        target shouldBeSameInstanceAs result
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeXLengthIsCalledAndXLengthIsTheSame() {
        val target = Size(60, 30)
        val result = target.withRelativeWidth(0)
        target shouldBeSameInstanceAs result
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeYLengthIsCalledAndYLengthIsTheSame() {
        val target = Size(60, 30)
        val result = target.withRelativeHeight(0)
        target shouldBeSameInstanceAs result
    }

    @Test
    fun shouldReturnItselfWhenWithIsCalledAndYLengthAndXLengthIsTheSame() {
        val target = Size(60, 30)
        val result = target.with(target)
        target shouldBeSameInstanceAs result
    }

    @Test
    fun shouldReturnProperMin() {
        val wide = Size(5, 2)
        val tall = Size(2, 5)
        wide.min(tall) shouldBe Size(2, 2)
    }

    @Test
    fun shouldReturnProperMax() {
        val wide = Size(5, 2)
        val tall = Size(2, 5)
        wide.max(tall) shouldBe Size(5, 5)
    }

    @Test
    fun shouldProperlyFetchBoundingBoxPositions() {
        val target = Size(3, 3)
        target.fetchBoundingBoxPositions() shouldContainExactlyInAnyOrder listOf(
            Position(0, 0),
            Position(1, 0),
            Position(2, 0),
            Position(0, 1),
            Position(2, 1),
            Position(0, 2),
            Position(1, 2),
            Position(2, 2)
        )
    }

    @Test
    fun shouldProperlyFetchTopLeftPosition() {
        Size(3, 3).fetchTopLeftPosition() shouldBe Position(0, 0)
    }

    @Test
    fun shouldProperlyFetchTopRightPosition() {
        Size(3, 3).fetchTopRightPosition() shouldBe Position(2, 0)
    }

    @Test
    fun shouldProperlyFetchBottomLeftPosition() {
        Size(3, 3).fetchBottomLeftPosition() shouldBe Position(0, 2)
    }

    @Test
    fun shouldProperlyFetchBottomRightPosition() {
        Size(3, 3).fetchBottomRightPosition() shouldBe Position(2, 2)
    }

    @Test
    fun withShouldReturnProperResult() {
        Size(1, 2).with(Size(2, 3)) shouldBe Size(2, 3)
    }

    companion object {
        const val EXPECTED_COL = 5
        const val EXPECTED_ROW = 5
        val EXPECTED_TERMINAL_SIZE = Size(
            width = EXPECTED_COL,
            height = EXPECTED_ROW
        )
    }
}
