package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
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
        assertThat(target.cursorPosition())
                .isEqualTo(Position.defaultPosition())
    }

    @Test
    fun shouldSetPositionCorrectly() {
        target.putCursorAt(Position.offset1x1())

        assertThat(target.cursorPosition())
                .isEqualTo(Position.offset1x1())
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
        target.putCursorAt(Position.defaultPosition().withY(-1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnNegativeCursorColumn() {
        target.putCursorAt(Position.defaultPosition().withX(-1))
    }

    @Test
    fun shouldReportEndOfLineWhenAtEndOfLine() {
        target.putCursorAt(Position.create(SIZE.width, 0))

        assertThat(target.isCursorAtTheEndOfTheLine()).isTrue()
    }

    @Test
    fun shouldNotReportEndOfLineWhenNotAtEndOfLine() {
        target.putCursorAt(Position.create(SIZE.width - 2, 0))

        assertThat(target.isCursorAtTheEndOfTheLine()).isFalse()
    }

    @Test
    fun shouldReportStartOfLineWhenAtStartOfLine() {
        target.putCursorAt(Position.create(0, 0))

        assertThat(target.isCursorAtTheStartOfTheLine()).isTrue()
    }

    @Test
    fun shouldNotReportStartOfLineWhenNotAtStartOfLine() {
        target.putCursorAt(Position.create(1, 0))

        assertThat(target.isCursorAtTheStartOfTheLine()).isFalse()
    }

    @Test
    fun shouldReportAtFirstRowWhenAtFirstRow() {
        target.putCursorAt(Position.create(0, 0))

        assertThat(target.isCursorAtTheFirstRow()).isTrue()
    }

    @Test
    fun shouldNotReportAtFirstRowWhenNotAtFirstRow() {
        target.putCursorAt(Position.create(0, 1))

        assertThat(target.isCursorAtTheFirstRow()).isFalse()
    }

    @Test
    fun shouldReportAtLastRowWhenAtLastRow() {
        target.putCursorAt(Position.create(0, SIZE.height))

        assertThat(target.isCursorAtTheLastRow()).isTrue()
    }

    @Test
    fun shouldNotReportAtLastRowWhenNotAtLastRow() {
        target.putCursorAt(Position.create(0, 0))

        assertThat(target.isCursorAtTheLastRow()).isFalse()
    }

    @Test
    fun shouldProperlyReportCursorSpaceSize() {
        assertThat(target.getCursorSpaceSize()).isEqualTo(SIZE)
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenNotAtTheStartOfTheLine() {
        val pos = Position.create(2, 2)
        target.putCursorAt(pos)
        target.moveCursorBackward()

        assertThat(target.cursorPosition())
                .isEqualTo(pos.withRelativeX(-1))
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenAtTheStartOfTheLineAndNotAtFirstRow() {
        val pos = Position.create(0, 1)
        target.putCursorAt(pos)
        target.moveCursorBackward()

        assertThat(target.cursorPosition())
                .isEqualTo(Position.create(SIZE.width - 1, 0))
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenAtTheStartOfTheLineAndAtFirstRow() {
        val pos = Position.create(0, 0)
        target.putCursorAt(pos)
        target.moveCursorBackward()

        assertThat(target.cursorPosition())
                .isEqualTo(pos)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToResizeCursorSpaceToNegativeSize() {
        target.resizeCursorSpace(Size.create(-1, 1))
    }

    companion object {
        val SIZE = Size.create(5, 5)
    }
}
