package org.codetome.zircon.api.shape

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.junit.Test

class LineFactoryTest {

    @Test
    fun shouldProperlyDrawStraightHorizontalLine() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(2, 0)))
                .containsExactly(
                        Position.DEFAULT_POSITION,
                        Position.DEFAULT_POSITION.withRelativeX(1),
                        Position.DEFAULT_POSITION.withRelativeX(2))
    }

    @Test
    fun shouldProperlyDrawStraightVerticalLine() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(0, 2)))
                .containsExactly(
                        Position.DEFAULT_POSITION,
                        Position.DEFAULT_POSITION.withRelativeY(1),
                        Position.DEFAULT_POSITION.withRelativeY(2))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToX() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(5, 4)))
                .containsExactly(
                        Position(x = 0, y = 0),
                        Position(x = 1, y = 1),
                        Position(x = 2, y = 2),
                        Position(x = 3, y = 2),
                        Position(x = 4, y = 3),
                        Position(x = 5, y = 4))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToY() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(4, 5)))
                .containsExactly(
                        Position(x = 0, y = 0),
                        Position(x = 1, y = 1),
                        Position(x = 2, y = 2),
                        Position(x = 2, y = 3),
                        Position(x = 3, y = 4),
                        Position(x = 4, y = 5))
    }

}
