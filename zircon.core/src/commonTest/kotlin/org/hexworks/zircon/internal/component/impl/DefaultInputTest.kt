package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.component.input
import org.hexworks.zircon.api.component.Input
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_PRESSED
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import kotlin.test.Test

class DefaultInputTest {

    private fun key(code: KeyCode, key: String = "") = KeyboardEvent(type = KEY_PRESSED, key = key, code = code)

    private fun Input.keyPressed(event: KeyboardEvent, phase: org.hexworks.zircon.api.uievent.UIEventPhase) =
        (this as DefaultInput).keyPressed(event, phase)

    @Test
    fun the_default_width_is_ten_columns() {
        input { }.size shouldBe Size(10, 1)
    }

    @Test
    fun long_text_scrolls_so_the_cursor_stays_visible() {
        val target = input {
            width = 5
            text = "hello world"
        }

        target.cursorIndex shouldBe 11
        target.scrollOffset shouldBe 7
    }

    @Test
    fun typing_inserts_at_the_cursor_and_backspace_deletes_before_it() {
        val target = input { width = 20 }

        target.keyPressed(key(KeyCode.KEY_A, "a"), TARGET) shouldBe Processed
        target.keyPressed(key(KeyCode.KEY_B, "b"), TARGET)
        target.keyPressed(key(KeyCode.LEFT), TARGET)
        target.keyPressed(key(KeyCode.KEY_X, "x"), TARGET)
        target.text shouldBe "axb"
        target.cursorIndex shouldBe 2

        target.keyPressed(key(KeyCode.BACKSPACE), TARGET)
        target.text shouldBe "ab"
        target.cursorIndex shouldBe 1

        target.keyPressed(key(KeyCode.DELETE), TARGET)
        target.text shouldBe "a"
    }

    @Test
    fun home_and_end_move_the_cursor_and_scroll() {
        val target = input {
            width = 5
            text = "hello world"
        }

        target.keyPressed(key(KeyCode.HOME), TARGET)
        target.cursorIndex shouldBe 0
        target.scrollOffset shouldBe 0

        target.keyPressed(key(KeyCode.END), TARGET)
        target.cursorIndex shouldBe 11
        target.scrollOffset shouldBe 7
    }

    @Test
    fun enter_is_passed_on_and_max_length_is_respected() {
        val target = input {
            width = 10
            maxLength = 3
            text = "ab"
        }

        target.keyPressed(key(KeyCode.ENTER, "\n"), TARGET) shouldBe Pass
        target.keyPressed(key(KeyCode.KEY_C, "c"), TARGET)
        target.keyPressed(key(KeyCode.KEY_D, "d"), TARGET)
        target.text shouldBe "abc"
    }

    @Test
    fun setting_the_text_from_outside_moves_the_cursor_back_into_range() {
        val target = input {
            width = 10
            text = "hello"
        }

        target.text = "hi"

        target.cursorIndex shouldBe 2
    }
}
