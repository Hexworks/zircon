package org.hexworks.zircon.api.data

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.extensions.isPositionNotUnknown
import org.hexworks.zircon.api.data.extensions.isPositionUnknown
import org.hexworks.zircon.api.data.extensions.relativeToBottomOf
import org.hexworks.zircon.api.data.extensions.relativeToLeftOf
import org.hexworks.zircon.api.data.extensions.relativeToRightOf
import org.hexworks.zircon.api.data.extensions.relativeToTopOf
import org.hexworks.zircon.api.data.extensions.toPixelPosition
import org.hexworks.zircon.api.data.extensions.withRelative
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY
import org.hexworks.zircon.api.data.extensions.withX
import org.hexworks.zircon.api.data.extensions.withY
import org.hexworks.zircon.internal.data.GridPosition
import org.hexworks.zircon.internal.data.PixelPosition
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import kotlin.test.BeforeTest
import kotlin.test.Test

class PositionTest {

    private lateinit var componentStub: Component

    @BeforeTest
    fun setUp() {
        componentStub = panel {
            position = COMPONENT_POSITION
            preferredSize = COMPONENT_SIZE
        }
    }

    @Test
    fun shouldBeUnknownWhenUnknown() {
        Position.UNKNOWN.isPositionUnknown shouldBe true
    }

    @Test
    fun shouldNotBeUnknownWhenNotUnknown() {
        Position.UNKNOWN.isPositionNotUnknown shouldBe false
    }

    @Test
    fun shouldBeGreaterThanWhenYIsGreater() {
        Position(1, 2) shouldBeGreaterThan Position(1, 1)
    }

    @Test
    fun shouldThrowExceptionWhenTryingToPlusDifferentTypes() {
        shouldThrow<IllegalArgumentException> {
            PixelPosition(1, 2) + GridPosition(1, 2)
        }
    }

    @Test
    fun shouldThrowExceptionWhenTryingToMinusDifferentTypes() {
        shouldThrow<IllegalArgumentException> {
            PixelPosition(1, 2) - GridPosition(1, 2)
        }
    }

    @Test
    fun shouldBeGreaterThanWhenYIsEqualAndXIsGreater() {
        Position(2, 2) shouldBeGreaterThan Position(1, 2)
    }

    @Test
    fun shouldBeEqualThanWhenYIsEqualAndXIsEqual() {
        Position(2, 2) shouldBeEqualComparingTo Position(2, 2)
    }

    @Test
    fun shouldBeLessThenWhenYIsLessThan() {
        Position(1, 2) shouldBeLessThan Position(1, 3)
    }

    @Test
    fun shouldProperlyConvertToPixelPosition() {
        Position(2, 2).toPixelPosition(BuiltInCP437TilesetResource.WANDERLUST_16X16) shouldBe PixelPosition(32, 32)
    }

    @Test
    fun shouldProperlyPlusTwoPositionsWhenBothArePositive() {
        Position(1, 2).plus(Position(2, 3)) shouldBe Position(3, 5)
    }

    @Test
    fun shouldProperlyPlusTwoPositionsWhenOneIsNegative() {
        Position(-1, -2).plus(Position(2, 3)) shouldBe Position(1, 1)
    }

    @Test
    fun shouldProperlyPlusTwoPixelPositionsWhenBothArePositive() {
        PixelPosition(1, 2).plus(PixelPosition(2, 3)) shouldBe PixelPosition(3, 5)
    }

    @Test
    fun shouldProperlyPlusTwoPixelPositionsWhenOneIsNegative() {
        PixelPosition(-1, -2).plus(PixelPosition(2, 3)) shouldBe PixelPosition(1, 1)
    }

    @Test
    fun shouldProperlyMinusTwoPositionsWhenBothArePositive() {
        Position(5, 4).minus(Position(1, 2)) shouldBe Position(4, 2)
    }

    @Test
    fun shouldProperlyMinusTwoPositionsWhenOneIsNegative() {
        Position(-1, -2).minus(Position(2, 3)) shouldBe Position(-3, -5)
    }

    @Test
    fun shouldProperlyMinusTwoPixelPositionsWhenBothArePositive() {
        PixelPosition(5, 4).minus(PixelPosition(1, 2)) shouldBe PixelPosition(4, 2)
    }

    @Test
    fun shouldProperlyMinusTwoPixelPositionsWhenOneIsNegative() {
        PixelPosition(-1, -2).minus(PixelPosition(2, 3)) shouldBe PixelPosition(-3, -5)
    }

    @Test
    fun shouldProperlyCreateNewPositionWithYWhenWithYIsCalled() {
        Position(
            x = THREE,
            y = Int.MAX_VALUE
        ).withY(TWO) shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeYWhenWithRelativeYIsCalled() {
        Position(
            x = THREE,
            y = TWO - 1
        ).withRelativeY(1) shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldProperlyCreateNewPositionWithXWhenWithXIsCalled() {
        Position(
            x = Int.MAX_VALUE,
            y = TWO
        ).withX(THREE) shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeXWhenWithRelativeXIsCalled() {
        Position(
            x = THREE - 1,
            y = TWO
        ).withRelativeX(1) shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldProperlyCreateNewPositionWithRelativeValuesWhenWithRelativeIsCalled() {
        Position(
            x = THREE - 1,
            y = TWO - 1
        ).withRelative(Position(1, 1)) shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldCompareToEqualWhenYIsEqualAndXIsEqual() {
        TWO_THREE_POS shouldBe TWO_THREE_POS
    }

    @Test
    fun shouldProperlyAddTwoPositions() {
        (Position(5, 4) + Position(1, 3)) shouldBe Position(6, 7)
    }

    @Test
    fun shouldProperlySubtractTwoPositions() {
        (Position(5, 4) - Position(4, 3)) shouldBe Position(1, 1)
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithX() {
        Position(1, 0).withX(0) shouldBeSameInstanceAs Position.ZERO
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRelativeX() {
        Position(1, 0).withRelativeX(-1) shouldBeSameInstanceAs Position.ZERO
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithY() {
        Position(0, 1).withY(0) shouldBeSameInstanceAs Position.ZERO
    }

    @Test
    fun shouldReturnTopLeftWhenNewPosWouldBeTopLeftWithRelativeY() {
        Position(0, 1).withRelativeY(-1) shouldBeSameInstanceAs Position.ZERO
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToTopOfComponent() {
        val position = Position(1, 1)
        val expected = Position(3, 2)
        val result = position.relativeToTopOf(componentStub)
        result shouldBe expected
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToTopOfComponentWhenNewPositionWouldBeNegative() {
        val position = Position(1, 9)
        val expected = Position(3, 0)
        val result = position.relativeToTopOf(componentStub)
        result shouldBe expected
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToRightOfComponent() {
        val position = Position(1, 1)
        val expected = Position(7, 4)
        val result = position.relativeToRightOf(componentStub)
        result shouldBe expected
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToBottomOfComponent() {
        val position = Position(1, 1)
        val expected = Position(3, 9)
        val result = position.relativeToBottomOf(componentStub)
        result shouldBe expected
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToLeftOfComponent() {
        val position = Position(1, 1)
        val expected = Position(1, 4)
        val result = position.relativeToLeftOf(componentStub)
        result shouldBe expected
    }

    @Test
    fun shouldReturnProperPositionUsingRelativeToLeftOfComponentWhenNewPositionWouldBeNegative() {
        val position = Position(9, 1)
        val expected = Position(0, 4)
        val result = position.relativeToLeftOf(componentStub)
        result shouldBe expected
    }

    companion object {
        val COMPONENT_POSITION = Position(2, 3)
        val COMPONENT_SIZE = Size(4, 5)
        const val TWO = 2
        const val THREE = 3
        val TWO_THREE_POS = Position(
            x = THREE,
            y = TWO
        )
    }
}
