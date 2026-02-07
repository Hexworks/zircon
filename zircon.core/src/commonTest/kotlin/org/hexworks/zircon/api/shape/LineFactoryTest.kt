package org.hexworks.zircon.api.shape

import io.kotest.matchers.collections.shouldContainExactly
import org.hexworks.zircon.api.data.Position
import kotlin.test.Test

class LineFactoryTest {

    @Test
    fun shouldProperlyDrawStraightHorizontalLine() {
        LineFactory.buildLine(
            fromPoint = Position.create(0, 0),
            toPoint = Position.create(2, 0)
        ) shouldContainExactly listOf(
            Position.defaultPosition(),
            Position.defaultPosition().withRelativeX(1),
            Position.defaultPosition().withRelativeX(2)
        )
    }

    @Test
    fun shouldProperlyDrawStraightHorizontalLineBackwards() {
        LineFactory.buildLine(
            fromPoint = Position.create(2, 0),
            toPoint = Position.create(0, 0)
        ) shouldContainExactly listOf(
            Position.defaultPosition().withRelativeX(2),
            Position.defaultPosition().withRelativeX(1),
            Position.defaultPosition()
        )
    }

    @Test
    fun shouldProperlyDrawStraightVerticalLine() {
        LineFactory.buildLine(
            fromPoint = Position.create(0, 0),
            toPoint = Position.create(0, 2)
        ) shouldContainExactly listOf(
            Position.defaultPosition(),
            Position.defaultPosition().withRelativeY(1),
            Position.defaultPosition().withRelativeY(2)
        )
    }

    @Test
    fun shouldProperlyDrawStraightVerticalLineBackwards() {
        LineFactory.buildLine(
            fromPoint = Position.create(0, 2),
            toPoint = Position.create(0, 0)
        ) shouldContainExactly listOf(
            Position.defaultPosition().withRelativeY(2),
            Position.defaultPosition().withRelativeY(1),
            Position.defaultPosition()
        )
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToX() {
        LineFactory.buildLine(
            fromPoint = Position.create(0, 0),
            toPoint = Position.create(5, 4)
        ) shouldContainExactly listOf(
            Position.create(x = 0, y = 0),
            Position.create(x = 1, y = 1),
            Position.create(x = 2, y = 2),
            Position.create(x = 3, y = 2),
            Position.create(x = 4, y = 3),
            Position.create(x = 5, y = 4)
        )
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToXBackwards() {
        LineFactory.buildLine(
            fromPoint = Position.create(5, 4),
            toPoint = Position.create(0, 0)
        ) shouldContainExactly listOf(
            Position.create(x = 5, y = 4),
            Position.create(x = 4, y = 3),
            Position.create(x = 3, y = 2),
            Position.create(x = 2, y = 2),
            Position.create(x = 1, y = 1),
            Position.create(x = 0, y = 0)
        )
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToY() {
        LineFactory.buildLine(
            fromPoint = Position.create(0, 0),
            toPoint = Position.create(4, 5)
        ) shouldContainExactly listOf(
            Position.create(x = 0, y = 0),
            Position.create(x = 1, y = 1),
            Position.create(x = 2, y = 2),
            Position.create(x = 2, y = 3),
            Position.create(x = 3, y = 4),
            Position.create(x = 4, y = 5)
        )
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToYBackwards() {
        LineFactory.buildLine(
            fromPoint = Position.create(4, 5),
            toPoint = Position.create(0, 0)
        ) shouldContainExactly listOf(
            Position.create(x = 4, y = 5),
            Position.create(x = 3, y = 4),
            Position.create(x = 2, y = 3),
            Position.create(x = 2, y = 2),
            Position.create(x = 1, y = 1),
            Position.create(x = 0, y = 0)
        )
    }

}
