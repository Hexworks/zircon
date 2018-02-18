package org.codetome.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.junit.Before
import org.junit.Test

class DefaultCursorHandlerTest {

    lateinit var target: DefaultCursorHandler

    @Before
    fun setUp() {
        target = DefaultCursorHandler(SIZE)
    }

    @Test
    fun defaultCursorPositionShouldBeDefaultPosition() {
        assertThat(target.getCursorPosition())
                .isEqualTo(Position.DEFAULT_POSITION)
    }

    @Test
    fun shouldSetPositionCorrectly() {
        target.putCursorAt(Position.OFFSET_1x1)

        assertThat(target.getCursorPosition())
                .isEqualTo(Position.OFFSET_1x1)
    }

    @Test
    fun defaultCursorVisibilityShouldBeFalse() {
        assertThat(target.isCursorVisible())
                .isFalse()
    }

    @Test
    fun shouldSetVisibilityCorrectly() {
        target.setCursorVisibility(false)

        assertThat(target.isCursorVisible())
                .isFalse()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnNegativeCursorRow() {
        target.putCursorAt(Position.DEFAULT_POSITION.withY(-1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnNegativeCursorColumn() {
        target.putCursorAt(Position.DEFAULT_POSITION.withX(-1))
    }

    @Test
    fun shouldReportEndOfLineWhenAtEndOfLine() {
        target.putCursorAt(Position(SIZE.xLength, 0))

        assertThat(target.isCursorAtTheEndOfTheLine()).isTrue()
    }

    @Test
    fun shouldNotReportEndOfLineWhenNotAtEndOfLine() {
        target.putCursorAt(Position(SIZE.xLength - 2, 0))

        assertThat(target.isCursorAtTheEndOfTheLine()).isFalse()
    }

    @Test
    fun shouldReportStartOfLineWhenAtStartOfLine() {
        target.putCursorAt(Position(0, 0))

        assertThat(target.isCursorAtTheStartOfTheLine()).isTrue()
    }

    @Test
    fun shouldNotReportStartOfLineWhenNotAtStartOfLine() {
        target.putCursorAt(Position(1, 0))

        assertThat(target.isCursorAtTheStartOfTheLine()).isFalse()
    }

    @Test
    fun shouldReportAtFirstRowWhenAtFirstRow() {
        target.putCursorAt(Position(0, 0))

        assertThat(target.isCursorAtTheFirstRow()).isTrue()
    }

    @Test
    fun shouldNotReportAtFirstRowWhenNotAtFirstRow() {
        target.putCursorAt(Position(0, 1))

        assertThat(target.isCursorAtTheFirstRow()).isFalse()
    }

    @Test
    fun shouldReportAtLastRowWhenAtLastRow() {
        target.putCursorAt(Position(0, SIZE.yLength))

        assertThat(target.isCursorAtTheLastRow()).isTrue()
    }

    @Test
    fun shouldNotReportAtLastRowWhenNotAtLastRow() {
        target.putCursorAt(Position(0, 0))

        assertThat(target.isCursorAtTheLastRow()).isFalse()
    }

    @Test
    fun shouldProperlyReportCursorSpaceSize() {
        assertThat(target.getCursorSpaceSize()).isEqualTo(SIZE)
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenNotAtTheStartOfTheLine() {
        val pos = Position.of(2, 2)
        target.putCursorAt(pos)
        target.moveCursorBackward()

        assertThat(target.getCursorPosition())
                .isEqualTo(pos.withRelativeX(-1))
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenAtTheStartOfTheLineAndNotAtFirstRow() {
        val pos = Position.of(0, 1)
        target.putCursorAt(pos)
        target.moveCursorBackward()

        assertThat(target.getCursorPosition())
                .isEqualTo(Position.of(SIZE.xLength - 1, 0))
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenAtTheStartOfTheLineAndAtFirstRow() {
        val pos = Position.of(0, 0)
        target.putCursorAt(pos)
        target.moveCursorBackward()

        assertThat(target.getCursorPosition())
                .isEqualTo(pos)
    }

    companion object {
        val SIZE = Size.of(5, 5)
    }
}
