package org.hexworks.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Position
import org.junit.Test

class LineFactoryTest {

    @Test
    fun shouldProperlyDrawStraightHorizontalLine() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position.create(0, 0),
                toPoint = Position.create(2, 0)))
                .containsExactly(
                        Position.defaultPosition(),
                        Position.defaultPosition().withRelativeX(1),
                        Position.defaultPosition().withRelativeX(2))
    }

    @Test
    fun shouldProperlyDrawStraightHorizontalLineBackwards() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position.create(2, 0),
                toPoint = Position.create(0, 0)))
                .containsExactly(
                        Position.defaultPosition().withRelativeX(2),
                        Position.defaultPosition().withRelativeX(1),
                        Position.defaultPosition())
    }

    @Test
    fun shouldProperlyDrawStraightVerticalLine() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position.create(0, 0),
                toPoint = Position.create(0, 2)))
                .containsExactly(
                        Position.defaultPosition(),
                        Position.defaultPosition().withRelativeY(1),
                        Position.defaultPosition().withRelativeY(2))
    }

    @Test
    fun shouldProperlyDrawStraightVerticalLineBackwards() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position.create(0, 2),
                toPoint = Position.create(0, 0)))
                .containsExactly(
                        Position.defaultPosition().withRelativeY(2),
                        Position.defaultPosition().withRelativeY(1),
                        Position.defaultPosition())
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToX() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position.create(0, 0),
                toPoint = Position.create(5, 4)))
                .containsExactly(
                        Position.create(x = 0, y = 0),
                        Position.create(x = 1, y = 1),
                        Position.create(x = 2, y = 2),
                        Position.create(x = 3, y = 2),
                        Position.create(x = 4, y = 3),
                        Position.create(x = 5, y = 4))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToXBackwards() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position.create(5, 4),
                toPoint = Position.create(0, 0)))
                .containsExactly(
                        Position.create(x = 5, y = 4),
                        Position.create(x = 4, y = 3),
                        Position.create(x = 3, y = 2),
                        Position.create(x = 2, y = 2),
                        Position.create(x = 1, y = 1),
                        Position.create(x = 0, y = 0))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToY() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position.create(0, 0),
                toPoint = Position.create(4, 5)))
                .containsExactly(
                        Position.create(x = 0, y = 0),
                        Position.create(x = 1, y = 1),
                        Position.create(x = 2, y = 2),
                        Position.create(x = 2, y = 3),
                        Position.create(x = 3, y = 4),
                        Position.create(x = 4, y = 5))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToYBackwards() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position.create(4, 5),
                toPoint = Position.create(0, 0)))
                .containsExactly(
                        Position.create(x = 4, y = 5),
                        Position.create(x = 3, y = 4),
                        Position.create(x = 2, y = 3),
                        Position.create(x = 2, y = 2),
                        Position.create(x = 1, y = 1),
                        Position.create(x = 0, y = 0))
    }

}
