package org.codetome.zircon.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SizeTest {

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTerminalColumnsAreNegative() {
        Size(
                columns = -1,
                rows = 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTerminalRowsAreNegative() {
        Size(
                columns = 1,
                rows = -1)
    }

    @Test
    fun shouldCreateNewSizeWithProperColumnsWhenWithColumnsIsCalled() {
        assertThat(Size(
                columns = Int.MAX_VALUE,
                rows = EXPECTED_ROW).withColumns(EXPECTED_COL))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeColumnsWhenWithRelativeColumnsIsCalled() {
        assertThat(Size(
                columns = EXPECTED_COL - 1,
                rows = EXPECTED_ROW).withRelativeColumns(1))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperRowsWhenWithRowsIsCalled() {
        assertThat(Size(
                columns = EXPECTED_COL,
                rows = Int.MAX_VALUE).withRows(EXPECTED_ROW))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativeRowsWhenWithRelativeRowsIsCalled() {
        assertThat(Size(
                columns = EXPECTED_COL,
                rows = EXPECTED_ROW - 1).withRelativeRows(1))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldCreateNewSizeWithProperRelativesWhenWithRelativeIsCalled() {
        assertThat(Size(
                columns = EXPECTED_COL - 1,
                rows = EXPECTED_ROW - 1).withRelative(Size(1, 1)))
                .isEqualTo(EXPECTED_TERMINAL_SIZE)
    }

    @Test
    fun shouldFetchPositionsInCorrectIterationOrder() {
        assertThat(Size(2, 2).fetchPositions())
                .isEqualTo(listOf(
                        Position(column = 0, row = 0),
                        Position(column = 1, row = 0),
                        Position(column = 0, row = 1),
                        Position(column = 1, row = 1)))
    }

    @Test
    fun shouldReturnItselfWhenWithColumnsIsCalledAndColumnsAreTheSame() {
        val target = Size.DEFAULT
        val result = target.withColumns(target.columns)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithRowsIsCalledAndRowsAreTheSame() {
        val target = Size.DEFAULT
        val result = target.withRows(target.rows)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeColumnsIsCalledAndColumnsAreTheSame() {
        val target = Size.DEFAULT
        val result = target.withRelativeColumns(0)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithRelativeRowsIsCalledAndRowsAreTheSame() {
        val target = Size.DEFAULT
        val result = target.withRelativeRows(0)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnItselfWhenWithIsCalledAndRowsAndColumnsAreTheSame() {
        val target = Size.DEFAULT
        val result = target.with(target)
        assertThat(target).isSameAs(result)
    }

    @Test
    fun shouldReturnProperMin() {
        val wide = Size(5, 2)
        val tall = Size(2, 5)
        assertThat(wide.min(tall)).isEqualTo(Size(2, 2))
    }

    @Test
    fun shouldReturnProperMax() {
        val wide = Size(5, 2)
        val tall = Size(2, 5)
        assertThat(wide.max(tall)).isEqualTo(Size(5, 5))
    }

    @Test
    fun withShouldReturnProperResult() {
        assertThat(Size(1, 2).with(Size(2, 3)))
                .isEqualTo(Size(2, 3))
    }

    @Test
    fun zeroSizeShouldReturnTrueWhenSizeIsZero() {
        assertThat(Size(5, 0).withColumns(0)).isSameAs(Size.ZERO)
    }

    companion object {
        val EXPECTED_COL = 5
        val EXPECTED_ROW = 5
        val EXPECTED_TERMINAL_SIZE = Size(
                columns = EXPECTED_COL,
                rows = EXPECTED_ROW)
    }
}