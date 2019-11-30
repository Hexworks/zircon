package org.hexworks.zircon.api.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.internal.data.GridPosition
import org.hexworks.zircon.internal.data.PixelPosition
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Before
import org.junit.Test

class PositionTest {

    private lateinit var componentStub: Component

    @Before
    fun setUp() {
        componentStub = PanelBuilder.newBuilder()
                .withPosition(COMPONENT_POSITION)
                .withSize(COMPONENT_SIZE)
                .build()
    }

    @Test
    fun shouldBeUnknownWhenUnknown() {
        assertThat(Position.unknown().isUnknown).isTrue()
    }

    @Test
    fun shouldNotBeUnknownWhenNotUnknown() {
        assertThat(Position.unknown().isNotUnknown).isFalse()
    }

    @Test
    fun shouldBeGreaterThanWhenYIsGreater() {
        assertThat(Position.create(1, 2)).isGreaterThan(Position.create(1, 1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTryingToPlusDifferentTypes() {
        PixelPosition.create(1, 2) + GridPosition.create(1, 2)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTryingToMinusDifferentTypes() {
        PixelPosition.create(1, 2) - GridPosition.create(1, 2)
    }

    @Test
    fun shouldBeGreaterThanWhenYIsEqualAndXIsGreater() {
        assertThat(Position.create(2, 2)).isGreaterThan(Position.create(1, 2))
    }

    @Test
    fun shouldBeEqualThanWhenYIsEqualAndXIsEqual() {
        assertThat(Position.create(2, 2)).isEqualByComparingTo(Position.create(2, 2))
    }

    @Test
    fun shouldBeLessThenWhenYIsLessThan() {
        assertThat(Position.create(1, 2)).isLessThan(Position.create(1, 3))
    }

    @Test
    fun shouldProperlyConvertToPixelPosition() {
        assertThat(Position.create(2, 2).toPixelPosition(BuiltInCP437TilesetResource.WANDERLUST_16X16))
                .isEqualTo(PixelPosition.create(32, 32))
    }

    @Test
    fun shouldProperlyPlusTwoPositionsWhenBothArePositive() {
        assertThat(Positions.create(1, 2).plus(Positions.create(2, 3)))
                .isEqualTo(Positions.create(3, 5))
    }

    @Test
    fun shouldProperlyPlusTwoPositionsWhenOneIsNegative() {
        assertThat(Positions.create(-1, -2).plus(Positions.create(2, 3)))
                .isEqualTo(Positions.create(1, 1))
    }

    @Test
    fun shouldProperlyPlusTwoPixelPositionsWhenBothArePositive() {
        assertThat(PixelPosition.create(1, 2).plus(PixelPosition.create(2, 3)))
                .isEqualTo(PixelPosition.create(3, 5))
    }

    @Test
    fun shouldProperlyPlusTwoPixelPositionsWhenOneIsNegative() {
        assertThat(PixelPosition.create(-1, -2).plus(PixelPosition.create(2, 3)))
                .isEqualTo(PixelPosition.create(1, 1))
    }

    @Test
    fun shouldProperlyMinusTwoPositionsWhenBothArePositive() {
        assertThat(Positions.create(5, 4).minus(Positions.create(1, 2)))
                .isEqualTo(Positions.create(4, 2))
    }

    @Test
    fun shouldProperlyMinusTwoPositionsWhenOneIsNegative() {
        assertThat(Positions.create(-1, -2).minus(Positions.create(2, 3)))
                .isEqualTo(Positions.create(-3, -5))
    }

    @Test
    fun shouldProperlyMinusTwoPixelPositionsWhenBothArePositive() {
        assertThat(PixelPosition.create(5, 4).minus(PixelPosition.create(1, 2)))
                .isEqualTo(PixelPosition.create(4, 2))
    }

    @Test
    fun shouldProperlyMinusTwoPixelPositionsWhenOneIsNegative() {
        assertThat(PixelPosition.create(-1, -2).minus(PixelPosition.create(2, 3)))
                .isEqualTo(PixelPosition.create(-3, -5))
    }

    @Test
    fun shouldProperlyCreateNewPositionWithYWhenWithYIsCalled() {
        assertThat(Position.create(
                x = THREE,
                y = Int.MAX_VALUE)
                .withY(TWO))
                .isEqualTo(TWO_THREE_POS)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeYWhenWithRelativeYIsCalled() {
        assertThat(Position.create(
                x = THREE,
                y = TWO - 1)
                .withRelativeY(1))
                .isEqualTo(TWO_THREE_POS)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithXWhenWithXIsCalled() {
        assertThat(Position.create(
                x = Int.MAX_VALUE,
                y = TWO)
                .withX(THREE))
                .isEqualTo(TWO_THREE_POS)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeXWhenWithRelativeXIsCalled() {
        assertThat(Position.create(
                x = THREE - 1,
                y = TWO)
                .withRelativeX(1))
                .isEqualTo(TWO_THREE_POS)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeValuesWhenWithRelativeIsCalled() {
        assertThat(Position.create(
                x = THREE - 1,
                y = TWO - 1)
                .withRelative(Position.create(1, 1)))
                .isEqualTo(TWO_THREE_POS)
    }

    @Test
    fun shouldCompareToEqualWhenYIsEqualAndXIsEqual() {
        assertThat(TWO_THREE_POS)
                .isEqualTo(TWO_THREE_POS)
    }

    @Test
    fun shouldProperlyAddTwoPositions() {
        assertThat(Position.create(5, 4) + Position.create(1, 3))
                .isEqualTo(Position.create(6, 7))
    }

    @Test
    fun shouldProperlySubtractTwoPositions() {
        assertThat(Position.create(5, 4) - Position.create(4, 3))
                .isEqualTo(Position.create(1, 1))
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithX() {
        assertThat(Position.create(1, 0).withX(0))
                .isSameAs(Position.zero())
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRelativeX() {
        assertThat(Position.create(1, 0).withRelativeX(-1))
                .isSameAs(Position.zero())
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithY() {
        assertThat(Position.create(0, 1).withY(0))
                .isSameAs(Position.zero())
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRelativeY() {
        assertThat(Position.create(0, 1).withRelativeY(-1))
                .isSameAs(Position.zero())
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToTopOfComponent() {
        val position = Position.create(1, 1)
        val expected = Position.create(3, 2)
        val result = position.relativeToTopOf(componentStub)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToTopOfComponentWhenNewPositionWouldBeNegative() {
        val position = Position.create(1, 9)
        val expected = Position.create(3, 0)
        val result = position.relativeToTopOf(componentStub)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToRightOfComponent() {
        val position = Position.create(1, 1)
        val expected = Position.create(7, 4)
        val result = position.relativeToRightOf(componentStub)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToBottomOfComponent() {
        val position = Position.create(1, 1)
        val expected = Position.create(3, 9)
        val result = position.relativeToBottomOf(componentStub)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToLeftOfComponent() {
        val position = Position.create(1, 1)
        val expected = Position.create(1, 4)
        val result = position.relativeToLeftOf(componentStub)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToLeftOfComponentWhenNewPositionWouldBeNegative() {
        val position = Position.create(9, 1)
        val expected = Position.create(0, 4)
        val result = position.relativeToLeftOf(componentStub)
        assertThat(result).isEqualTo(expected)
    }

    companion object {
        val COMPONENT_POSITION = Position.create(2, 3)
        val COMPONENT_SIZE = Size.create(4, 5)
        const val TWO = 2
        const val THREE = 3
        val TWO_THREE_POS = Position.create(
                x = THREE,
                y = TWO)
    }
}
