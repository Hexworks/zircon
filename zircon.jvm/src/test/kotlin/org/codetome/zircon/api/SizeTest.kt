package org.codetome.zircon.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SizeTest {

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTerminalXLengthIsNegative() {
        Size.create(
                xLength = -1,
                yLength = 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTerminalYLengthIsNegative() {
        Size.create(
                xLength = 1,
                yLength = -1)
    }

    @Test
    fun shouldCreateNewSizeWithProperXLengthWhenWithXLengthIsCalled() {
        assertThat(Size.create(
                xLength = Int.MAX_VALUE,
                yLength = EXPECTED_ROW).withXLength(EXPECTED_COL))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldProperlyAddTwoSizes() {
        assertThat(Size.create(1, 2) + Size.create(2, 1)).isEqualTo(Size.create(3, 3))
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeXLengthWhenWithRelativeXLengthIsCalled() {
        assertThat(Size.create(
                xLength = EXPECTED_COL - 1,
                yLength = EXPECTED_ROW).withRelativeXLength(1))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperYLengthWhenWithYLengthIsCalled() {
        assertThat(Size.create(
                xLength = EXPECTED_COL,
                yLength = Int.MAX_VALUE).withYLength(EXPECTED_ROW))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeYLengthWhenWithRelativeYLengthIsCalled() {
        assertThat(Size.create(
                xLength = EXPECTED_COL,
                yLength = EXPECTED_ROW - 1).withRelativeYLength(1))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativesWhenWithRelativeIsCalled() {
        assertThat(Size.create(
                xLength = EXPECTED_COL - 1,
                yLength = EXPECTED_ROW - 1).withRelative(Size.create(1, 1)))
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
        val target = Size.defaultTerminalSize()
        val result = target.withXLength(target.xLength)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithYLengthIsCalledAndYLengthIsTheSame() {
        val target = Size.defaultTerminalSize()
        val result = target.withYLength(target.yLength)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeXLengthIsCalledAndXLengthIsTheSame() {
        val target = Size.defaultTerminalSize()
        val result = target.withRelativeXLength(0)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeYLengthIsCalledAndYLengthIsTheSame() {
        val target = Size.defaultTerminalSize()
        val result = target.withRelativeYLength(0)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithIsCalledAndYLengthAndXLengthIsTheSame() {
        val target = Size.defaultTerminalSize()
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

    // TODO: UNCOMMENT THIS WHEN FIXED
//    @Test
//    fun shouldProperlyFetchBoundingBoxPositions() {
//        val target = Size.create(3, 3)
//        assertThat(target.fetchBoundingBoxPositions())
//                .containsExactlyInAnyOrder(Position.create(0, 0), Position.create(1, 0), Position.create(2, 0),
//                        Position.create(0, 1), Position.create(2, 1), Position.create(0, 2), Position.create(1, 2), Position.create(2, 2))
//    }

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
        val EXPECTED_COL = 5
        val EXPECTED_ROW = 5
        val EXPECTED_TERMINAL_SIZE = Size.create(
                xLength = EXPECTED_COL,
                yLength = EXPECTED_ROW)
    }
}
