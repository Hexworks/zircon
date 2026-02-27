package org.hexworks.zircon.api.data

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.internal.data.GridPosition
import org.hexworks.zircon.internal.data.PixelPosition
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import kotlin.test.BeforeTest
import kotlin.test.Test

class PositionTest {

    private lateinit var componentStub: Component

    @BeforeTest
    fun setUp() {
        componentStub = buildPanel {
            position = COMPONENT_POSITION
            preferredSize = COMPONENT_SIZE
        }
    }

    @Test
    fun shouldBeUnknownWhenUnknown() {
        Position.unknown().isUnknown shouldBe true
    }

    @Test
    fun shouldNotBeUnknownWhenNotUnknown() {
        Position.unknown().isNotUnknown shouldBe false
    }

    @Test
    fun shouldBeGreaterThanWhenYIsGreater() {
        Position.create(1, 2) shouldBeGreaterThan Position.create(1, 1)
    }

    @Test
    fun shouldThrowExceptionWhenTryingToPlusDifferentTypes() {
        shouldThrow<IllegalArgumentException> {
            PixelPosition.create(1, 2) + GridPosition.create(1, 2)
        }
    }

    @Test
    fun shouldThrowExceptionWhenTryingToMinusDifferentTypes() {
        shouldThrow<IllegalArgumentException> {
            PixelPosition.create(1, 2) - GridPosition.create(1, 2)
        }
    }

    @Test
    fun shouldBeGreaterThanWhenYIsEqualAndXIsGreater() {
        Position.create(2, 2) shouldBeGreaterThan Position.create(1, 2)
    }

    @Test
    fun shouldBeEqualThanWhenYIsEqualAndXIsEqual() {
        Position.create(2, 2) shouldBeEqualComparingTo Position.create(2, 2)
    }

    @Test
    fun shouldBeLessThenWhenYIsLessThan() {
        Position.create(1, 2) shouldBeLessThan Position.create(1, 3)
    }

    @Test
    fun shouldProperlyConvertToPixelPosition() {
        Position.create(2, 2).toPixelPosition(BuiltInCP437TilesetResource.WANDERLUST_16X16) shouldBe PixelPosition.create(32, 32)
    }

    @Test
    fun shouldProperlyPlusTwoPositionsWhenBothArePositive() {
        Position.create(1, 2).plus(Position.create(2, 3)) shouldBe Position.create(3, 5)
    }

    @Test
    fun shouldProperlyPlusTwoPositionsWhenOneIsNegative() {
        Position.create(-1, -2).plus(Position.create(2, 3)) shouldBe Position.create(1, 1)
    }

    @Test
    fun shouldProperlyPlusTwoPixelPositionsWhenBothArePositive() {
        PixelPosition.create(1, 2).plus(PixelPosition.create(2, 3)) shouldBe PixelPosition.create(3, 5)
    }

    @Test
    fun shouldProperlyPlusTwoPixelPositionsWhenOneIsNegative() {
        PixelPosition.create(-1, -2).plus(PixelPosition.create(2, 3)) shouldBe PixelPosition.create(1, 1)
    }

    @Test
    fun shouldProperlyMinusTwoPositionsWhenBothArePositive() {
        Position.create(5, 4).minus(Position.create(1, 2)) shouldBe Position.create(4, 2)
    }

    @Test
    fun shouldProperlyMinusTwoPositionsWhenOneIsNegative() {
        Position.create(-1, -2).minus(Position.create(2, 3)) shouldBe Position.create(-3, -5)
    }

    @Test
    fun shouldProperlyMinusTwoPixelPositionsWhenBothArePositive() {
        PixelPosition.create(5, 4).minus(PixelPosition.create(1, 2)) shouldBe PixelPosition.create(4, 2)
    }

    @Test
    fun shouldProperlyMinusTwoPixelPositionsWhenOneIsNegative() {
        PixelPosition.create(-1, -2).minus(PixelPosition.create(2, 3)) shouldBe PixelPosition.create(-3, -5)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithYWhenWithYIsCalled() {
        Position.create(
            x = THREE,
            y = Int.MAX_VALUE
        ).withY(TWO) shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeYWhenWithRelativeYIsCalled() {
        Position.create(
            x = THREE,
            y = TWO - 1
        ).withRelativeY(1) shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldProperlyCreateNewPositionWithXWhenWithXIsCalled() {
        Position.create(
            x = Int.MAX_VALUE,
            y = TWO
        ).withX(THREE) shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeXWhenWithRelativeXIsCalled() {
        Position.create(
            x = THREE - 1,
            y = TWO
        ).withRelativeX(1) shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeValuesWhenWithRelativeIsCalled() {
        Position.create(
            x = THREE - 1,
            y = TWO - 1
        ).withRelative(Position.create(1, 1)) shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldCompareToEqualWhenYIsEqualAndXIsEqual() {
        TWO_THREE_POS shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldProperlyAddTwoPositions() {
        (Position.create(5, 4) + Position.create(1, 3)) shouldBe Position.create(6, 7)
    }

    @Test
    fun shouldProperlySubtractTwoPositions() {
        (Position.create(5, 4) - Position.create(4, 3)) shouldBe Position.create(1, 1)
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithX() {
        Position.create(1, 0).withX(0) shouldBeSameInstanceAs 
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRelativeX() {
        Position.create(1, 0).withRelativeX(-1) shouldBeSameInstanceAs 
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithY() {
        Position.create(0, 1).withY(0) shouldBeSameInstanceAs 
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRelativeY() {
        Position.create(0, 1).withRelativeY(-1) shouldBeSameInstanceAs 
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToTopOfComponent() {
        val position = Position.create(1, 1)
        val expected = Position.create(3, 2)
        val result = position.relativeToTopOf(componentStub)
        result shouldBe expected
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToTopOfComponentWhenNewPositionWouldBeNegative() {
        val position = Position.create(1, 9)
        val expected = Position.create(3, 0)
        val result = position.relativeToTopOf(componentStub)
        result shouldBe expected
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToRightOfComponent() {
        val position = Position.create(1, 1)
        val expected = Position.create(7, 4)
        val result = position.relativeToRightOf(componentStub)
        result shouldBe expected
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToBottomOfComponent() {
        val position = Position.create(1, 1)
        val expected = Position.create(3, 9)
        val result = position.relativeToBottomOf(componentStub)
        result shouldBe expected
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToLeftOfComponent() {
        val position = Position.create(1, 1)
        val expected = Position.create(1, 4)
        val result = position.relativeToLeftOf(componentStub)
        result shouldBe expected
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToLeftOfComponentWhenNewPositionWouldBeNegative() {
        val position = Position.create(9, 1)
        val expected = Position.create(0, 4)
        val result = position.relativeToLeftOf(componentStub)
        result shouldBe expected
    }

    companion object {
        val COMPONENT_POSITION = Position.create(2, 3)
        val COMPONENT_SIZE = Size.create(4, 5)
        const val TWO = 2
        const val THREE = 3
        val TWO_THREE_POS = Position.create(
            x = THREE,
            y = TWO
        )
    }
}
