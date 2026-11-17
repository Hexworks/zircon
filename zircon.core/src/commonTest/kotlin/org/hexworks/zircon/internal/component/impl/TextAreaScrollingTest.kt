package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.textArea
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_PRESSED
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_RELEASED
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import kotlin.test.Test

/**
 * The text area scrolls so the cursor stays inside its *content* area (the part inside the
 * decorations), and editing across lines keeps the text.
 */
class TextAreaScrollingTest {

    private fun TextArea.press(code: KeyCode, key: String = "") =
        (this as DefaultTextArea).keyPressed(KeyboardEvent(type = KEY_PRESSED, key = key, code = code), TARGET)

    private fun TextArea.type(text: String) = text.forEach { press(KeyCode.UNKNOWN, it.toString()) }

    private val TextArea.cursor: Position get() = (this as DefaultTextArea).editorState.cursor

    @Test
    fun typing_past_the_right_edge_of_a_boxed_text_area_scrolls_so_the_cursor_stays_visible() {
        val target = textArea {
            decorations { +box() }
            preferredSize = Size(12, 3)
        }
        target.contentSize shouldBe Size(10, 1)

        target.type("0123456789")

        target.cursor shouldBe Position(10, 0)
        target.visibleOffset shouldBe Position(1, 0)
        target.visibleSize shouldBe Size(10, 1)
    }

    @Test
    fun a_line_break_past_the_bottom_scrolls_down_and_a_new_text_scrolls_back_to_the_cursor() {
        val target = textArea { preferredSize = Size(10, 2) }

        target.press(KeyCode.ENTER)
        target.press(KeyCode.ENTER)
        target.cursor shouldBe Position(0, 2)
        target.visibleOffset shouldBe Position(0, 1)

        target.text = "a"
        target.cursor shouldBe Position.ZERO
        target.visibleOffset shouldBe Position.ZERO
    }

    @Test
    fun backspace_at_the_start_of_a_line_joins_it_with_the_previous_one() {
        val target = textArea { preferredSize = Size(10, 3); text = "abc\ndef" }

        target.press(KeyCode.DOWN)
        target.cursor shouldBe Position(0, 1)
        target.press(KeyCode.BACKSPACE)

        target.text shouldBe "abcdef"
        target.cursor shouldBe Position(3, 0)
    }

    @Test
    fun joining_a_line_that_ends_beyond_the_right_edge_scrolls_the_cursor_into_view() {
        val target = textArea { preferredSize = Size(5, 2); text = "abcdefgh\nxy" }

        target.press(KeyCode.DOWN)
        target.press(KeyCode.BACKSPACE)

        target.text shouldBe "abcdefghxy"
        target.cursor shouldBe Position(8, 0)
        target.visibleOffset shouldBe Position(4, 0)
    }

    @Test
    fun deleting_while_scrolled_right_keeps_the_cursor_at_the_right_edge() {
        val target = textArea { preferredSize = Size(5, 1) }
        target.type("abcdefg")
        target.visibleOffset shouldBe Position(3, 0)

        target.press(KeyCode.BACKSPACE)

        target.cursor shouldBe Position(6, 0)
        target.visibleOffset shouldBe Position(2, 0)
    }

    @Test
    fun a_click_puts_the_cursor_under_the_mouse_inside_the_decorations() {
        val target = textArea {
            decorations { +box() }
            preferredSize = Size(12, 3)
            position = Position(2, 2)
            text = "hello"
        }

        (target as DefaultTextArea).mouseReleased(MouseEvent(MOUSE_RELEASED, 1, Position(2 + 1 + 3, 2 + 1)), TARGET)

        target.cursor shouldBe Position(3, 0)
    }
}
