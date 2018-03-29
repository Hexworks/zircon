package org.codetome.zircon.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SizeTest {

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTerminalXLengthIsNegative() {
        Size.of(
                xLength = -1,
                yLength = 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTerminalYLengthIsNegative() {
        Size.of(
                xLength = 1,
                yLength = -1)
    }

    @Test
    fun shouldCreateNewSizeWithProperXLengthWhenWithXLengthIsCalled() {
        assertThat(Size.of(
                xLength = Int.MAX_VALUE,
                yLength = EXPECTED_ROW).withXLength(EXPECTED_COL))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldProperlyAddTwoSizes() {
        assertThat(Size.of(1, 2) + Size.of(2, 1)).isEqualTo(Size.of(3, 3))
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeXLengthWhenWithRelativeXLengthIsCalled() {
        assertThat(Size.of(
                xLength = EXPECTED_COL - 1,
                yLength = EXPECTED_ROW).withRelativeXLength(1))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperYLengthWhenWithYLengthIsCalled() {
        assertThat(Size.of(
                xLength = EXPECTED_COL,
                yLength = Int.MAX_VALUE).withYLength(EXPECTED_ROW))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeYLengthWhenWithRelativeYLengthIsCalled() {
        assertThat(Size.of(
                xLength = EXPECTED_COL,
                yLength = EXPECTED_ROW - 1).withRelativeYLength(1))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativesWhenWithRelativeIsCalled() {
        assertThat(Size.of(
                xLength = EXPECTED_COL - 1,
                yLength = EXPECTED_ROW - 1).withRelative(Size.of(1, 1)))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldFetchPositionsInCorrectIterationOrder() {
        assertThat(Size.of(2, 2).fetchPositions().toList())
                .isEqualTo(listOf(
                        Position.of(x = 0, y = 0),
                        Position.of(x = 1, y = 0),
                        Position.of(x = 0, y = 1),
                        Position.of(x = 1, y = 1)))
    }

    @Test
    fun shouldReturnItselfWhenWithXLengthIsCalledAndXLengthIsTheSame() {
        val target = Size.DEFAULT_TERMINAL_SIZE
        val result = target.withXLength(target.xLength)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithYLengthIsCalledAndYLengthIsTheSame() {
        val target = Size.DEFAULT_TERMINAL_SIZE
        val result = target.withYLength(target.yLength)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeXLengthIsCalledAndXLengthIsTheSame() {
        val target = Size.DEFAULT_TERMINAL_SIZE
        val result = target.withRelativeXLength(0)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeYLengthIsCalledAndYLengthIsTheSame() {
        val target = Size.DEFAULT_TERMINAL_SIZE
        val result = target.withRelativeYLength(0)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithIsCalledAndYLengthAndXLengthIsTheSame() {
        val target = Size.DEFAULT_TERMINAL_SIZE
        val result = target.with(target)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnProperMin() {
        val wide = Size.of(5, 2)
        val tall = Size.of(2, 5)
        assertThat(wide.min(tall)).isEqualTo(Size.of(2, 2))
    }

    @Test
    fun shouldReturnProperMax() {
        val wide = Size.of(5, 2)
        val tall = Size.of(2, 5)
        assertThat(wide.max(tall)).isEqualTo(Size.of(5, 5))
    }

    @Test
    fun shouldProperlyFetchBoundingBoxPositions() {
        val target = Size.of(3, 3)
        assertThat(target.fetchBoundingBoxPositions())
                .containsExactlyInAnyOrder(Position.of(0, 0), Position.of(1, 0), Position.of(2, 0),
                        Position.of(0, 1), Position.of(2, 1), Position.of(0, 2), Position.of(1, 2), Position.of(2, 2))
    }

    @Test
    fun shouldProperlyFetchTopLeftPosition() {
        assertThat(Size.of(3, 3).fetchTopLeftPosition()).isEqualTo(Position.of(0, 0))
    }

    @Test
    fun shouldProperlyFetchTopRightPosition() {
        assertThat(Size.of(3, 3).fetchTopRightPosition()).isEqualTo(Position.of(2, 0))
    }

    @Test
    fun shouldProperlyFetchBottomLeftPosition() {
        assertThat(Size.of(3, 3).fetchBottomLeftPosition()).isEqualTo(Position.of(0, 2))
    }

    @Test
    fun shouldProperlyFetchBottomRightPosition() {
        assertThat(Size.of(3, 3).fetchBottomRightPosition()).isEqualTo(Position.of(2, 2))
    }

    @Test
    fun withShouldReturnProperResult() {
        assertThat(Size.of(1, 2).with(Size.of(2, 3)))
                .isEqualTo(Size.of(2, 3))
    }

    @Test
    fun zeroSizeShouldReturnTrueWhenSizeIsZero() {
        assertThat(Size.of(5, 0).withXLength(0)).isSameAs(Size.ZERO)
    }

    companion object {
        val EXPECTED_COL = 5
        val EXPECTED_ROW = 5
        val EXPECTED_TERMINAL_SIZE = Size.of(
                xLength = EXPECTED_COL,
                yLength = EXPECTED_ROW)
    }
}
