package org.hexworks.zircon.api.data

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import kotlin.test.Test

class SizeTest {

    @Test
    fun shouldNotAllowToCreateSizeWithNegativeWidth() {
        shouldThrow<IllegalArgumentException> {
            Size.create(-1, 0)
        }
    }

    @Test
    fun shouldNotAllowToCreateSizeWithNegativeHeight() {
        shouldThrow<IllegalArgumentException> {
            Size.create(0, -1)
        }
    }

    @Test
    fun shouldProperlyDestructureSize() {
        val (width, height) = Size.create(2, 3)

        width shouldBe 2
        height shouldBe 3
    }

    @Test
    fun shouldBeUnknownWhenUnknown() {
        Size.unknown().isUnknown shouldBe true
    }

    @Test
    fun shouldNotBeUnknownWhenNotUnknown() {
        Size.create(1, 2).isNotUnknown shouldBe true
    }

    @Test
    fun shouldProperlyCalculateGreaterThan() {
        Size.create(1, 2) shouldBeGreaterThan Size.create(1, 1)
    }

    @Test
    fun shouldProperlyCalculateLessThan() {
        Size.create(1, 1) shouldBeLessThan Size.create(1, 2)
    }

    @Test
    fun shouldProperlyCalculateEqualByComparingTo() {
        Size.create(1, 2) shouldBeEqualComparingTo Size.create(2, 1)
    }

    @Test
    fun shouldCreateNewSizeWithProperXLengthWhenWithXLengthIsCalled() {
        Size.create(
            width = Int.MAX_VALUE,
            height = EXPECTED_ROW
        ).withWidth(EXPECTED_COL) shouldBe EXPECTED_TERMINAL_SIZE
    }

    @Test
    fun shouldProperlyAddTwoSizes() {
        (Size.create(1, 2) + Size.create(2, 1)) shouldBe Size.create(3, 3)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeXLengthWhenWithRelativeXLengthIsCalled() {
        Size.create(
            width = EXPECTED_COL - 1,
            height = EXPECTED_ROW
        ).withRelativeWidth(1) shouldBe EXPECTED_TERMINAL_SIZE
    }

    @Test
    fun shouldCreateNewSizeWithProperYLengthWhenWithYLengthIsCalled() {
        Size.create(
            width = EXPECTED_COL,
            height = Int.MAX_VALUE
        ).withHeight(EXPECTED_ROW) shouldBe EXPECTED_TERMINAL_SIZE
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeYLengthWhenWithRelativeYLengthIsCalled() {
        Size.create(
            width = EXPECTED_COL,
            height = EXPECTED_ROW - 1
        ).withRelativeHeight(1) shouldBe EXPECTED_TERMINAL_SIZE
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativesWhenWithRelativeIsCalled() {
        Size.create(
            width = EXPECTED_COL - 1,
            height = EXPECTED_ROW - 1
        ).withRelative(Size.create(1, 1)) shouldBe EXPECTED_TERMINAL_SIZE
    }

    @Test
    fun shouldFetchPositionsInCorrectIterationOrder() {
        Size.create(2, 2).fetchPositions().toList() shouldBe listOf(
            Position.create(x = 0, y = 0),
            Position.create(x = 1, y = 0),
            Position.create(x = 0, y = 1),
            Position.create(x = 1, y = 1)
        )
    }

    @Test
    fun shouldReturnItselfWhenWithXLengthIsCalledAndXLengthIsTheSame() {
        val target = Size.defaultGridSize()
        val result = target.withWidth(target.width)
        target shouldBeSameInstanceAs result
    }

    @Test
    fun shouldReturnItselfWhenWithYLengthIsCalledAndYLengthIsTheSame() {
        val target = Size.defaultGridSize()
        val result = target.withHeight(target.height)
        target shouldBeSameInstanceAs result
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeXLengthIsCalledAndXLengthIsTheSame() {
        val target = Size.defaultGridSize()
        val result = target.withRelativeWidth(0)
        target shouldBeSameInstanceAs result
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeYLengthIsCalledAndYLengthIsTheSame() {
        val target = Size.defaultGridSize()
        val result = target.withRelativeHeight(0)
        target shouldBeSameInstanceAs result
    }

    @Test
    fun shouldReturnItselfWhenWithIsCalledAndYLengthAndXLengthIsTheSame() {
        val target = Size.defaultGridSize()
        val result = target.with(target)
        target shouldBeSameInstanceAs result
    }

    @Test
    fun shouldReturnProperMin() {
        val wide = Size.create(5, 2)
        val tall = Size.create(2, 5)
        wide.min(tall) shouldBe Size.create(2, 2)
    }

    @Test
    fun shouldReturnProperMax() {
        val wide = Size.create(5, 2)
        val tall = Size.create(2, 5)
        wide.max(tall) shouldBe Size.create(5, 5)
    }

    @Test
    fun shouldProperlyFetchBoundingBoxPositions() {
        val target = Size.create(3, 3)
        target.fetchBoundingBoxPositions() shouldContainExactlyInAnyOrder listOf(
            Position.create(0, 0),
            Position.create(1, 0),
            Position.create(2, 0),
            Position.create(0, 1),
            Position.create(2, 1),
            Position.create(0, 2),
            Position.create(1, 2),
            Position.create(2, 2)
        )
    }

    @Test
    fun shouldProperlyFetchTopLeftPosition() {
        Size.create(3, 3).fetchTopLeftPosition() shouldBe Position.create(0, 0)
    }

    @Test
    fun shouldProperlyFetchTopRightPosition() {
        Size.create(3, 3).fetchTopRightPosition() shouldBe Position.create(2, 0)
    }

    @Test
    fun shouldProperlyFetchBottomLeftPosition() {
        Size.create(3, 3).fetchBottomLeftPosition() shouldBe Position.create(0, 2)
    }

    @Test
    fun shouldProperlyFetchBottomRightPosition() {
        Size.create(3, 3).fetchBottomRightPosition() shouldBe Position.create(2, 2)
    }

    @Test
    fun withShouldReturnProperResult() {
        Size.create(1, 2).with(Size.create(2, 3)) shouldBe Size.create(2, 3)
    }

    companion object {
        const val EXPECTED_COL = 5
        const val EXPECTED_ROW = 5
        val EXPECTED_TERMINAL_SIZE = Size.create(
            width = EXPECTED_COL,
            height = EXPECTED_ROW
        )
    }
}
