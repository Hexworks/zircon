package org.hexworks.zircon.terminal

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_PRESSED
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_RELEASED
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_TYPED
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_CLICKED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_DRAGGED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_MOVED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_PRESSED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_RELEASED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_WHEEL_ROTATED_DOWN
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_WHEEL_ROTATED_UP
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.terminal.input.InputParser
import kotlin.test.Test

class InputParserTest {

    private val parser = InputParser { column, row -> Position(column, row) }

    private fun parse(text: String): List<UIEvent> = parser.feed(bytes(text))

    @Test
    fun printableKeyIsPressedAndTyped() {
        parse("\u001b[97;1u") shouldContainExactly listOf(
            KeyboardEvent(KEY_PRESSED, KeyCode.KEY_A, "a"),
            KeyboardEvent(KEY_TYPED, KeyCode.KEY_A, "a"),
        )
    }

    @Test
    fun associatedTextWins() {
        parse("\u001b[97;2;65u") shouldContainExactly listOf(
            KeyboardEvent(KEY_PRESSED, KeyCode.KEY_A, "A", shiftDown = true),
            KeyboardEvent(KEY_TYPED, KeyCode.KEY_A, "A", shiftDown = true),
        )
    }

    @Test
    fun ctrlKeyHasNoTypedEvent() {
        parse("\u001b[99;5u") shouldContainExactly listOf(
            KeyboardEvent(KEY_PRESSED, KeyCode.KEY_C, "", ctrlDown = true),
        )
    }

    @Test
    fun releaseAndRepeatEventTypes() {
        parse("\u001b[97;1:3u") shouldContainExactly listOf(KeyboardEvent(KEY_RELEASED, KeyCode.KEY_A, "a"))
        parse("\u001b[97;1:2u").first().let { (it as KeyboardEvent).type shouldBe KEY_PRESSED }
    }

    @Test
    fun functionalKeys() {
        parse("\u001b[1;2A") shouldContainExactly listOf(KeyboardEvent(KEY_PRESSED, KeyCode.UP, "", shiftDown = true))
        parse("\u001b[D") shouldContainExactly listOf(KeyboardEvent(KEY_PRESSED, KeyCode.LEFT, ""))
        parse("\u001b[3~") shouldContainExactly listOf(KeyboardEvent(KEY_PRESSED, KeyCode.DELETE, ""))
        parse("\u001b[13;1:3u") shouldContainExactly listOf(KeyboardEvent(KEY_RELEASED, KeyCode.ENTER, "\n"))
        parse("\u001b[27u") shouldContainExactly listOf(KeyboardEvent(KEY_PRESSED, KeyCode.ESCAPE, ""))
        parse("\u001b[57441u") shouldContainExactly listOf(KeyboardEvent(KEY_PRESSED, KeyCode.SHIFT, ""))
        parse("\u001b[57399;1u") shouldContainExactly listOf(KeyboardEvent(KEY_PRESSED, KeyCode.NUMPAD_0, ""))
    }

    @Test
    fun shiftedPunctuationUsesTheShiftedKey() {
        parse("\u001b[49:33;2;33u") shouldContainExactly listOf(
            KeyboardEvent(KEY_PRESSED, KeyCode.DIGIT_1, "!", shiftDown = true),
            KeyboardEvent(KEY_TYPED, KeyCode.DIGIT_1, "!", shiftDown = true),
        )
    }

    @Test
    fun mousePressReleaseOnTheSameCellIsAClick() {
        parse("\u001b[<0;5;3M") shouldContainExactly listOf(MouseEvent(MOUSE_PRESSED, 1, Position(4, 2)))
        parse("\u001b[<0;5;3m") shouldContainExactly listOf(
            MouseEvent(MOUSE_RELEASED, 1, Position(4, 2)),
            MouseEvent(MOUSE_CLICKED, 1, Position(4, 2)),
        )
    }

    @Test
    fun motionIsMovedOrDraggedAndDeduplicated() {
        parse("\u001b[<35;6;3M") shouldContainExactly listOf(MouseEvent(MOUSE_MOVED, 0, Position(5, 2)))
        parse("\u001b[<35;6;3M") shouldContainExactly emptyList()
        parse("\u001b[<2;6;3M") shouldContainExactly listOf(MouseEvent(MOUSE_PRESSED, 3, Position(5, 2)))
        parse("\u001b[<34;7;3M") shouldContainExactly listOf(MouseEvent(MOUSE_DRAGGED, 3, Position(6, 2)))
        parse("\u001b[<2;7;3m") shouldContainExactly listOf(MouseEvent(MOUSE_RELEASED, 3, Position(6, 2)))
    }

    @Test
    fun wheel() {
        parse("\u001b[<64;1;1M") shouldContainExactly listOf(MouseEvent(MOUSE_WHEEL_ROTATED_UP, 0, Position(0, 0)))
        parse("\u001b[<65;1;1M") shouldContainExactly listOf(MouseEvent(MOUSE_WHEEL_ROTATED_DOWN, 0, Position(0, 0)))
    }

    @Test
    fun sequenceSplitAcrossReads() {
        parse("\u001b[<0;") shouldContainExactly emptyList()
        parse("5;3M") shouldContainExactly listOf(MouseEvent(MOUSE_PRESSED, 1, Position(4, 2)))
    }

    @Test
    fun repliesGoToTheListener() {
        var flags = -1
        var attributes = false
        parser.replyListener = object : InputParser.ReplyListener {
            override fun onKittyKeyboardFlags(flags0: Int) {
                flags = flags0
            }

            override fun onDeviceAttributes() {
                attributes = true
            }
        }
        parse("\u001b[?31u\u001b[?62;22c") shouldContainExactly emptyList()
        flags shouldBe 31
        attributes shouldBe true
    }

    @Test
    fun legacyAndPastedBytes() {
        parse("\r") shouldContainExactly listOf(
            KeyboardEvent(KEY_PRESSED, KeyCode.ENTER, "\n"),
            KeyboardEvent(KEY_TYPED, KeyCode.ENTER, "\n"),
        )
        parse("\u0003") shouldContainExactly listOf(KeyboardEvent(KEY_PRESSED, KeyCode.KEY_C, "", ctrlDown = true))
        parse("é") shouldContainExactly listOf(
            KeyboardEvent(KEY_PRESSED, KeyCode.UNKNOWN, "é"),
            KeyboardEvent(KEY_TYPED, KeyCode.UNKNOWN, "é"),
        )
    }
}
