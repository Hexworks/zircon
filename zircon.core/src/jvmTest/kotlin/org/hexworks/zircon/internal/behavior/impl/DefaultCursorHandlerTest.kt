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
        assertThat(target.cursorPosition)
            .isEqualTo(Position.defaultPosition())
    }

    @Test
    fun shouldSetPositionCorrectly() {
        target.cursorPosition = Position.offset1x1()

        assertThat(target.cursorPosition)
            .isEqualTo(Position.offset1x1())
    }

    @Test
    fun defaultCursorVisibilityShouldBeFalse() {
        assertThat(target.isCursorVisible)
            .isFalse()
    }

    @Test
    fun shouldSetVisibilityCorrectly() {
        target.isCursorVisible = false

        assertThat(target.isCursorVisible)
            .isFalse()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnNegativeCursorRow() {
        target.cursorPosition = Position.defaultPosition().withY(-1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnNegativeCursorColumn() {
        target.cursorPosition = Position.defaultPosition().withX(-1)
    }

    @Test
    fun shouldReportEndOfLineWhenAtEndOfLine() {
        target.cursorPosition = Position.create(SIZE.width, 0)

        assertThat(target.isCursorAtTheEndOfTheLine).isTrue()
    }

    @Test
    fun shouldNotReportEndOfLineWhenNotAtEndOfLine() {
        target.cursorPosition = Position.create(SIZE.width - 2, 0)

        assertThat(target.isCursorAtTheEndOfTheLine).isFalse()
    }

    @Test
    fun shouldReportStartOfLineWhenAtStartOfLine() {
        target.cursorPosition = Position.create(0, 0)

        assertThat(target.isCursorAtTheStartOfTheLine).isTrue()
    }

    @Test
    fun shouldNotReportStartOfLineWhenNotAtStartOfLine() {
        target.cursorPosition = Position.create(1, 0)

        assertThat(target.isCursorAtTheStartOfTheLine).isFalse()
    }

    @Test
    fun shouldReportAtFirstRowWhenAtFirstRow() {
        target.cursorPosition = Position.create(0, 0)

        assertThat(target.isCursorAtTheFirstRow).isTrue()
    }

    @Test
    fun shouldNotReportAtFirstRowWhenNotAtFirstRow() {
        target.cursorPosition = Position.create(0, 1)

        assertThat(target.isCursorAtTheFirstRow).isFalse()
    }

    @Test
    fun shouldReportAtLastRowWhenAtLastRow() {
        target.cursorPosition = Position.create(0, SIZE.height)

        assertThat(target.isCursorAtTheLastRow).isTrue()
    }

    @Test
    fun shouldNotReportAtLastRowWhenNotAtLastRow() {
        target.cursorPosition = Position.create(0, 0)

        assertThat(target.isCursorAtTheLastRow).isFalse()
    }

    @Test
    fun shouldProperlyReportCursorSpaceSize() {
        assertThat(target.cursorSpaceSize).isEqualTo(SIZE)
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenNotAtTheStartOfTheLine() {
        val pos = Position.create(2, 2)
        target.cursorPosition = pos
        target.moveCursorBackward()

        assertThat(target.cursorPosition)
            .isEqualTo(pos.withRelativeX(-1))
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenAtTheStartOfTheLineAndNotAtFirstRow() {
        val pos = Position.create(0, 1)
        target.cursorPosition = pos
        target.moveCursorBackward()

        assertThat(target.cursorPosition)
            .isEqualTo(Position.create(SIZE.width - 1, 0))
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenAtTheStartOfTheLineAndAtFirstRow() {
        val pos = Position.create(0, 0)
        target.cursorPosition = pos
        target.moveCursorBackward()

        assertThat(target.cursorPosition)
            .isEqualTo(pos)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToResizeCursorSpaceToNegativeSize() {
        target.cursorSpaceSize = Size.create(-1, 1)
    }

    companion object {
        val SIZE = Size.create(5, 5)
    }
}
