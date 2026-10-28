package org.hexworks.zircon.internal.behavior.impl

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.behavior.extensions.isCursorAtTheEndOfTheLine
import org.hexworks.zircon.api.behavior.extensions.isCursorAtTheFirstRow
import org.hexworks.zircon.api.behavior.extensions.isCursorAtTheLastRow
import org.hexworks.zircon.api.behavior.extensions.isCursorAtTheStartOfTheLine
import org.hexworks.zircon.api.behavior.extensions.moveCursorBackward
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withX
import org.hexworks.zircon.api.data.extensions.withY
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultCursorHandlerTest {

    lateinit var target: DefaultCursorHandler

    @BeforeTest
    fun setUp() {
        target = DefaultCursorHandler(SIZE)
    }

    @Test
    fun defaultCursorPositionShouldBeDefaultPosition() {
        target.cursorPosition shouldBe Position.ZERO
    }

    @Test
    fun shouldSetPositionCorrectly() {
        target.cursorPosition = Position.OFFSET_1X1

        target.cursorPosition shouldBe Position.OFFSET_1X1
    }

    @Test
    fun defaultCursorVisibilityShouldBeFalse() {
        target.isCursorVisible shouldBe false
    }

    @Test
    fun shouldSetVisibilityCorrectly() {
        target.isCursorVisible = false

        target.isCursorVisible shouldBe false
    }

    @Test
    fun shouldClampToZeroWithNegativeY() {
        target.cursorPosition = Position.ZERO.withY(-1)

        target.cursorPosition.y shouldBe 0
    }

    @Test
    fun shouldClampZeroWithNegativeX() {
        target.cursorPosition = Position.ZERO.withX(-1)

        target.cursorPosition.x shouldBe 0
    }

    @Test
    fun shouldReportEndOfLineWhenAtEndOfLine() {
        target.cursorPosition = Position(SIZE.width, 0)

        target.isCursorAtTheEndOfTheLine shouldBe true
    }

    @Test
    fun shouldNotReportEndOfLineWhenNotAtEndOfLine() {
        target.cursorPosition = Position(SIZE.width - 2, 0)

        target.isCursorAtTheEndOfTheLine shouldBe false
    }

    @Test
    fun shouldReportStartOfLineWhenAtStartOfLine() {
        target.cursorPosition = Position(0, 0)

        target.isCursorAtTheStartOfTheLine shouldBe true
    }

    @Test
    fun shouldNotReportStartOfLineWhenNotAtStartOfLine() {
        target.cursorPosition = Position(1, 0)

        target.isCursorAtTheStartOfTheLine shouldBe false
    }

    @Test
    fun shouldReportAtFirstRowWhenAtFirstRow() {
        target.cursorPosition = Position(0, 0)

        target.isCursorAtTheFirstRow shouldBe true
    }

    @Test
    fun shouldNotReportAtFirstRowWhenNotAtFirstRow() {
        target.cursorPosition = Position(0, 1)

        target.isCursorAtTheFirstRow shouldBe false
    }

    @Test
    fun shouldReportAtLastRowWhenAtLastRow() {
        target.cursorPosition = Position(0, SIZE.height)

        target.isCursorAtTheLastRow shouldBe true
    }

    @Test
    fun shouldNotReportAtLastRowWhenNotAtLastRow() {
        target.cursorPosition = Position(0, 0)

        target.isCursorAtTheLastRow shouldBe false
    }

    @Test
    fun shouldProperlyReportCursorSpaceSize() {
        target.cursorSpaceSize shouldBe SIZE
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenNotAtTheStartOfTheLine() {
        val pos = Position(2, 2)
        target.cursorPosition = pos
        target.moveCursorBackward()

        target.cursorPosition shouldBe pos.withRelativeX(-1)
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenAtTheStartOfTheLineAndNotAtFirstRow() {
        val pos = Position(0, 1)
        target.cursorPosition = pos
        target.moveCursorBackward()

        target.cursorPosition shouldBe Position(SIZE.width - 1, 0)
    }

    @Test
    fun shouldMoveCursorBackwardProperlyWhenAtTheStartOfTheLineAndAtFirstRow() {
        val pos = Position(0, 0)
        target.cursorPosition = pos
        target.moveCursorBackward()

        target.cursorPosition shouldBe pos
    }

    @Test
    fun shouldNotBeAbleToResizeCursorSpaceToNegativeSize() {
        shouldThrow<IllegalArgumentException> {
            target.cursorSpaceSize = Size(-1, 1)
        }
    }

    companion object {
        val SIZE = Size(5, 5)
    }
}
