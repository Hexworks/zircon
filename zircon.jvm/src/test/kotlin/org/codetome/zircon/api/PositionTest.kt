package org.codetome.zircon.api

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.component.*
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
    fun shouldProperlyCreateNewPositionWithYWhenWithYIsCalled() {
        assertThat(Position.of(
                x = EXPECTED_COL,
                y = Int.MAX_VALUE)
                .withY(EXPECTED_ROW))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenXIsLessThanZero() {
        Position.of(-1, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenYIsLessThanZero() {
        Position.of(0, -1)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeYWhenWithRelativeYIsCalled() {
        assertThat(Position.of(
                x = EXPECTED_COL,
                y = EXPECTED_ROW - 1)
                .withRelativeY(1))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithXWhenWithXIsCalled() {
        assertThat(Position.of(
                x = Int.MAX_VALUE,
                y = EXPECTED_ROW)
                .withX(EXPECTED_COL))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeXWhenWithRelativeXIsCalled() {
        assertThat(Position.of(
                x = EXPECTED_COL - 1,
                y = EXPECTED_ROW)
                .withRelativeX(1))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeValuesWhenWithRelativeIsCalled() {
        assertThat(Position.of(
                x = EXPECTED_COL - 1,
                y = EXPECTED_ROW - 1)
                .withRelative(Position.of(1, 1)))
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test
    fun shouldCompareToEqualWhenYIsEqualAndXIsEqual() {
        assertThat(EXPECTED_TERMINAL_POSITION)
                .isEqualTo(EXPECTED_TERMINAL_POSITION)
    }

    @Test
    fun shouldProperlyAddTwoPositions() {
        assertThat(Position.of(5, 4) + Position.of(1, 3))
                .isEqualTo(Position.of(6, 7))
    }

    @Test
    fun shouldProperlySubtractTwoPositions() {
        assertThat(Position.of(5, 4) - Position.of(4, 3))
                .isEqualTo(Position.of(1, 1))
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithX() {
        assertThat(Position.of(1, 0).withX(0))
                .isSameAs(Position.TOP_LEFT_CORNER)
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRelativeX() {
        assertThat(Position.of(1, 0).withRelativeX(-1))
                .isSameAs(Position.TOP_LEFT_CORNER)
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithY() {
        assertThat(Position.of(0, 1).withY(0))
                .isSameAs(Position.TOP_LEFT_CORNER)
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRelativeY() {
        assertThat(Position.of(0, 1).withRelativeY(-1))
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
        val EXPECTED_TERMINAL_POSITION = Position.of(
                x = EXPECTED_COL,
                y = EXPECTED_ROW)
    }
}
