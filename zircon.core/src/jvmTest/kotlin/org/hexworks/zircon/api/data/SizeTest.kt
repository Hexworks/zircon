package org.hexworks.zircon.api.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.impl.Size3D
import org.junit.Test

class SizeTest {

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotAllowToCreateSizeWithNegativeWidth() {
        Size.create(-1, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotAllowToCreateSizeWithNegativeHeight() {
        Size.create(0, -1)
    }

    @Test
    fun shouldProperlyDestructureSize() {
        val (width, height) = Size.create(2, 3)

        assertThat(width).isEqualTo(2)
        assertThat(height).isEqualTo(3)
    }

    @Test
    fun shouldBeUnknownWhenUnknown() {
        assertThat(Size.unknown().isUnknown).isTrue()
    }

    @Test
    fun shouldNotBeUnknownWhenNotUnknown() {
        assertThat(Size.create(1, 2).isNotUnknown).isTrue()
    }

    @Test
    fun shouldProperlyCalculateGreaterThan() {
        assertThat(Size.create(1, 2)).isGreaterThan(Size.create(1, 1))
    }

    @Test
    fun shouldProperlyCalculateLessThan() {
        assertThat(Size.create(1, 1)).isLessThan(Size.create(1, 2))
    }

    @Test
    fun shouldProperlyCalculateEqualByComparingTo() {
        assertThat(Size.create(1, 2)).isEqualByComparingTo(Size.create(2, 1))
    }

    @Test
    fun shouldCreateNewSizeWithProperXLengthWhenWithXLengthIsCalled() {
        assertThat(Size.create(
                width = Int.MAX_VALUE,
                height = EXPECTED_ROW).withWidth(EXPECTED_COL))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldProperlyAddTwoSizes() {
        assertThat(Size.create(1, 2) + Size.create(2, 1)).isEqualTo(Size.create(3, 3))
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeXLengthWhenWithRelativeXLengthIsCalled() {
        assertThat(Size.create(
                width = EXPECTED_COL - 1,
                height = EXPECTED_ROW).withRelativeWidth(1))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperYLengthWhenWithYLengthIsCalled() {
        assertThat(Size.create(
                width = EXPECTED_COL,
                height = Int.MAX_VALUE).withHeight(EXPECTED_ROW))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeYLengthWhenWithRelativeYLengthIsCalled() {
        assertThat(Size.create(
                width = EXPECTED_COL,
                height = EXPECTED_ROW - 1).withRelativeHeight(1))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativesWhenWithRelativeIsCalled() {
        assertThat(Size.create(
                width = EXPECTED_COL - 1,
                height = EXPECTED_ROW - 1).withRelative(Size.create(1, 1)))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldFetchPositionsInCorrectIterationOrder() {
        assertThat(Size.create(2, 2).fetchPositions().toList())
                .isEqualTo(listOf(
                        Position.create(x = 0, y = 0),
                        Position.create(x = 1, y = 0),
                        Position.create(x = 0, y = 1),
                        Position.create(x = 1, y = 1)))
    }

    @Test
    fun shouldReturnItselfWhenWithXLengthIsCalledAndXLengthIsTheSame() {
        val target = Size.defaultGridSize()
        val result = target.withWidth(target.width)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithYLengthIsCalledAndYLengthIsTheSame() {
        val target = Size.defaultGridSize()
        val result = target.withHeight(target.height)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeXLengthIsCalledAndXLengthIsTheSame() {
        val target = Size.defaultGridSize()
        val result = target.withRelativeWidth(0)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeYLengthIsCalledAndYLengthIsTheSame() {
        val target = Size.defaultGridSize()
        val result = target.withRelativeHeight(0)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithIsCalledAndYLengthAndXLengthIsTheSame() {
        val target = Size.defaultGridSize()
        val result = target.with(target)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnProperMin() {
        val wide = Size.create(5, 2)
        val tall = Size.create(2, 5)
        assertThat(wide.min(tall)).isEqualTo(Size.create(2, 2))
    }

    @Test
    fun shouldReturnProperMax() {
        val wide = Size.create(5, 2)
        val tall = Size.create(2, 5)
        assertThat(wide.max(tall)).isEqualTo(Size.create(5, 5))
    }

    @Test
    fun shouldProperlyFetchBoundingBoxPositions() {
        val target = Size.create(3, 3)
        assertThat(target.fetchBoundingBoxPositions())
                .containsExactlyInAnyOrder(Position.create(0, 0), Position.create(1, 0), Position.create(2, 0),
                        Position.create(0, 1), Position.create(2, 1), Position.create(0, 2), Position.create(1, 2), Position.create(2, 2))
    }

    @Test
    fun shouldProperlyFetchTopLeftPosition() {
        assertThat(Size.create(3, 3).fetchTopLeftPosition()).isEqualTo(Position.create(0, 0))
    }

    @Test
    fun shouldProperlyFetchTopRightPosition() {
        assertThat(Size.create(3, 3).fetchTopRightPosition()).isEqualTo(Position.create(2, 0))
    }

    @Test
    fun shouldProperlyFetchBottomLeftPosition() {
        assertThat(Size.create(3, 3).fetchBottomLeftPosition()).isEqualTo(Position.create(0, 2))
    }

    @Test
    fun shouldProperlyFetchBottomRightPosition() {
        assertThat(Size.create(3, 3).fetchBottomRightPosition()).isEqualTo(Position.create(2, 2))
    }

    @Test
    fun withShouldReturnProperResult() {
        assertThat(Size.create(1, 2).with(Size.create(2, 3)))
                .isEqualTo(Size.create(2, 3))
    }

    companion object {
        const val EXPECTED_COL = 5
        const val EXPECTED_ROW = 5
        val EXPECTED_TERMINAL_SIZE = Size.create(
                width = EXPECTED_COL,
                height = EXPECTED_ROW)
    }
}
