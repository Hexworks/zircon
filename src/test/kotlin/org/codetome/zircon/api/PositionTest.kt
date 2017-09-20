package org.codetome.zircon.api

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.component.Component
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PositionTest {

    @Mock
    lateinit var componentMock: Component

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(componentMock.getBoundableSize()).thenReturn(COMPONENT_SIZE)
        Mockito.`when`(componentMock.getPosition()).thenReturn(COMPONENT_POSITION)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRowWhenWithRowIsCalled() {
        assertThat(Position(
                column = EXPECTED_COL,
                row = Int.MAX_VALUE)
                .withRow(EXPECTED_ROW))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenColumnIsLessThanZero() {
        Position.of(-1, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenRowIsLessThanZero() {
        Position.of(0, -1)
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

    @Test
    fun shouldProperlyAddTwoPositions() {
        assertThat(Position(5, 4) + Position(1, 3))
                .isEqualTo(Position(6, 7))
    }

    @Test
    fun shouldProperlySubtractTwoPositions() {
        assertThat(Position(5, 4) - Position(4, 3))
                .isEqualTo(Position(1, 1))
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithColumn() {
        assertThat(Position(1, 0).withColumn(0))
                .isSameAs(Position.TOP_LEFT_CORNER)
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRelativeColumn() {
        assertThat(Position(1, 0).withRelativeColumn(-1))
                .isSameAs(Position.TOP_LEFT_CORNER)
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRow() {
        assertThat(Position(0, 1).withRow(0))
                .isSameAs(Position.TOP_LEFT_CORNER)
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRelativeRow() {
        assertThat(Position(0, 1).withRelativeRow(-1))
                .isSameAs(Position.TOP_LEFT_CORNER)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToTopOfComponent() {
        val position = Position.of(1, 1)
        val expected = Position.of(3, 2)
        val result = position.relativeToTopOf(componentMock)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToTopOfComponentWhenNewPositionWouldBeNegative() {
        val position = Position.of(1, 9)
        val expected = Position.of(3, 0)
        val result = position.relativeToTopOf(componentMock)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToRightOfComponent() {
        val position = Position.of(1, 1)
        val expected = Position.of(7, 4)
        val result = position.relativeToRightOf(componentMock)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToBottomOfComponent() {
        val position = Position.of(1, 1)
        val expected = Position.of(3, 9)
        val result = position.relativeToBottomOf(componentMock)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToLeftOfComponent() {
        val position = Position.of(1, 1)
        val expected = Position.of(1, 4)
        val result = position.relativeToLeftOf(componentMock)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToLeftOfComponentWhenNewPositionWouldBeNegative() {
        val position = Position.of(9, 1)
        val expected = Position.of(0, 4)
        val result = position.relativeToLeftOf(componentMock)
        assertThat(result).isEqualTo(expected)
    }

    companion object {
        val COMPONENT_POSITION = Position.of(2, 3)
        val COMPONENT_SIZE = Size.of(4, 5)
        val EXPECTED_ROW = 2
        val EXPECTED_COL = 3
        val EXPECTED_TERMINAL_POSITION = Position(
                column = EXPECTED_COL,
                row = EXPECTED_ROW)
    }
}