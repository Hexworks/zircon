package org.codetome.zircon.graphics.shape.factory

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.junit.Test

class LineFactoryTest {

    @Test
    fun shouldProperlyDrawStraightHorizontalLine() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(2, 0)))
                .containsExactly(
                        Position.DEFAULT_POSITION,
                        Position.DEFAULT_POSITION.withRelativeColumn(1),
                        Position.DEFAULT_POSITION.withRelativeColumn(2))
    }

    @Test
    fun shouldProperlyDrawStraightVerticalLine() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(0, 2)))
                .containsExactly(
                        Position.DEFAULT_POSITION,
                        Position.DEFAULT_POSITION.withRelativeRow(1),
                        Position.DEFAULT_POSITION.withRelativeRow(2))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToX() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(5, 4)))
                .containsExactly(
                        Position(column = 0, row = 0),
                        Position(column = 1, row = 1),
                        Position(column = 2, row = 2),
                        Position(column = 3, row = 2),
                        Position(column = 4, row = 3),
                        Position(column = 5, row = 4))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToY() {
        assertThat(LineFactory.buildLine(
                fromPoint = Position(0, 0),
                toPoint = Position(4, 5)))
                .containsExactly(
                        Position(column = 0, row = 0),
                        Position(column = 1, row = 1),
                        Position(column = 2, row = 2),
                        Position(column = 2, row = 3),
                        Position(column = 3, row = 4),
                        Position(column = 4, row = 5))
    }

}