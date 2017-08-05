package org.codetome.zircon

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PositionTest {

    @Test
    fun shouldProperlyCreateNewPositionWithRowWhenWithRowIsCalled() {
        assertThat(Position(
                column = EXPECTED_COL,
                row = Int.MAX_VALUE)
                .withRow(EXPECTED_ROW))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeRowWhenWithRelativeRowIsCalled() {
        assertThat(Position(
                column = EXPECTED_COL,
                row = EXPECTED_ROW - 1)
                .withRelativeRow(1))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithColWhenWithColumnIsCalled() {
        assertThat(Position(
                column = Int.MAX_VALUE,
                row = EXPECTED_ROW)
                .withColumn(EXPECTED_COL))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeColWhenWithRelativeColumnIsCalled() {
        assertThat(Position(
                column = EXPECTED_COL - 1,
                row = EXPECTED_ROW)
                .withRelativeColumn(1))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeValuesWhenWithRelativeIsCalled() {
        assertThat(Position(
                column = EXPECTED_COL - 1,
                row = EXPECTED_ROW - 1)
                .withRelative(Position(1, 1)))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test
    fun shouldCompareToLessWhenRowIsLess() {
        assertThat(EXPECTED_TERMINAL_POSITION)
                .isGreaterThan(EXPECTED_TERMINAL_POSITION.withRelativeRow(-1))
    }

    @Test
    fun shouldCompareToLessWhenRowIsEqualButColIsLess() {
        assertThat(EXPECTED_TERMINAL_POSITION)
                .isGreaterThan(EXPECTED_TERMINAL_POSITION.withRelativeColumn(-1))
    }

    @Test
    fun shouldCompareToEqualWhenRowIsEqualAndColIsEqual() {
        assertThat(EXPECTED_TERMINAL_POSITION)
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    companion object {
        val EXPECTED_ROW = 2
        val EXPECTED_COL = 3
        val EXPECTED_TERMINAL_POSITION = Position(
                column = EXPECTED_COL,
                row = EXPECTED_ROW)
    }
}