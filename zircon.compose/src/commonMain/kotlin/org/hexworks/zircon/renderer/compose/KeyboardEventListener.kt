package org.hexworks.zircon.renderer.compose

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.utf16CodePoint
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.api.uievent.KeyboardEvent as ZirconKeyboardEvent

/**
 * Bridges Compose keyboard events to Zircon keyboard events.
 */
class KeyboardEventListener(
    private val tileGrid: InternalTileGrid
) {

    private val events = mutableListOf<Pair<ZirconKeyboardEvent, UIEventPhase>>()

    /**
     * Drains all accumulated keyboard events.
     */
    fun drainEvents(): Iterable<Pair<ZirconKeyboardEvent, UIEventPhase>> {
        return events.toList().also {
            events.clear()
        }
    }

    /**
     * Handles a Compose KeyEvent and converts it to Zircon format.
     * Returns true if the event was consumed.
     */
    fun handleKeyEvent(event: KeyEvent): Boolean {
        val type = when (event.type) {
            KeyEventType.KeyDown -> KeyboardEventType.KEY_PRESSED
            KeyEventType.KeyUp -> KeyboardEventType.KEY_RELEASED
            else -> return false
        }

        val keyChar = try {
            val codePoint = event.utf16CodePoint
            if (codePoint in 32..126) {
                codePoint.toChar().toString()
            } else {
                ""
            }
        } catch (_: Exception) {
            ""
        }

        val zirconEvent = ZirconKeyboardEvent(
            type = type,
            key = keyChar,
            code = event.key.toKeyCode(),
            ctrlDown = event.isCtrlPressed,
            altDown = event.isAltPressed,
            metaDown = event.isMetaPressed,
            shiftDown = event.isShiftPressed
        )

        events.add(zirconEvent to UIEventPhase.TARGET)

        // Also emit KEY_TYPED for printable characters on key down
        if (type == KeyboardEventType.KEY_PRESSED && keyChar.isNotEmpty()) {
            val typedEvent = ZirconKeyboardEvent(
                type = KeyboardEventType.KEY_TYPED,
                key = keyChar,
                code = event.key.toKeyCode(),
                ctrlDown = event.isCtrlPressed,
                altDown = event.isAltPressed,
                metaDown = event.isMetaPressed,
                shiftDown = event.isShiftPressed
            )
            events.add(typedEvent to UIEventPhase.TARGET)
        }

        return true
    }

    private fun Key.toKeyCode(): KeyCode = when (this) {
        Key.Enter -> KeyCode.ENTER
        Key.Backspace -> KeyCode.BACKSPACE
        Key.Tab -> KeyCode.TAB
        Key.ShiftLeft, Key.ShiftRight -> KeyCode.SHIFT
        Key.CtrlLeft, Key.CtrlRight -> KeyCode.CONTROL
        Key.AltLeft, Key.AltRight -> KeyCode.ALT
        Key.Escape -> KeyCode.ESCAPE
        Key.Spacebar -> KeyCode.SPACE
        Key.PageUp -> KeyCode.PAGE_UP
        Key.PageDown -> KeyCode.PAGE_DOWN
        Key.MoveEnd -> KeyCode.END
        Key.MoveHome -> KeyCode.HOME
        Key.DirectionLeft -> KeyCode.LEFT
        Key.DirectionUp -> KeyCode.UP
        Key.DirectionRight -> KeyCode.RIGHT
        Key.DirectionDown -> KeyCode.DOWN
        Key.Insert -> KeyCode.INSERT
        Key.Delete -> KeyCode.DELETE
        Key.Zero -> KeyCode.DIGIT_0
        Key.One -> KeyCode.DIGIT_1
        Key.Two -> KeyCode.DIGIT_2
        Key.Three -> KeyCode.DIGIT_3
        Key.Four -> KeyCode.DIGIT_4
        Key.Five -> KeyCode.DIGIT_5
        Key.Six -> KeyCode.DIGIT_6
        Key.Seven -> KeyCode.DIGIT_7
        Key.Eight -> KeyCode.DIGIT_8
        Key.Nine -> KeyCode.DIGIT_9
        Key.A -> KeyCode.KEY_A
        Key.B -> KeyCode.KEY_B
        Key.C -> KeyCode.KEY_C
        Key.D -> KeyCode.KEY_D
        Key.E -> KeyCode.KEY_E
        Key.F -> KeyCode.KEY_F
        Key.G -> KeyCode.KEY_G
        Key.H -> KeyCode.KEY_H
        Key.I -> KeyCode.KEY_I
        Key.J -> KeyCode.KEY_J
        Key.K -> KeyCode.KEY_K
        Key.L -> KeyCode.KEY_L
        Key.M -> KeyCode.KEY_M
        Key.N -> KeyCode.KEY_N
        Key.O -> KeyCode.KEY_O
        Key.P -> KeyCode.KEY_P
        Key.Q -> KeyCode.KEY_Q
        Key.R -> KeyCode.KEY_R
        Key.S -> KeyCode.KEY_S
        Key.T -> KeyCode.KEY_T
        Key.U -> KeyCode.KEY_U
        Key.V -> KeyCode.KEY_V
        Key.W -> KeyCode.KEY_W
        Key.X -> KeyCode.KEY_X
        Key.Y -> KeyCode.KEY_Y
        Key.Z -> KeyCode.KEY_Z
        Key.NumPad0 -> KeyCode.NUMPAD_0
        Key.NumPad1 -> KeyCode.NUMPAD_1
        Key.NumPad2 -> KeyCode.NUMPAD_2
        Key.NumPad3 -> KeyCode.NUMPAD_3
        Key.NumPad4 -> KeyCode.NUMPAD_4
        Key.NumPad5 -> KeyCode.NUMPAD_5
        Key.NumPad6 -> KeyCode.NUMPAD_6
        Key.NumPad7 -> KeyCode.NUMPAD_7
        Key.NumPad8 -> KeyCode.NUMPAD_8
        Key.NumPad9 -> KeyCode.NUMPAD_9
        Key.NumPadMultiply -> KeyCode.MULTIPLY
        Key.NumPadAdd -> KeyCode.ADD
        Key.NumPadSubtract -> KeyCode.SUBTRACT
        Key.NumPadDot -> KeyCode.DECIMAL
        Key.NumPadDivide -> KeyCode.DIVIDE
        Key.F1 -> KeyCode.F1
        Key.F2 -> KeyCode.F2
        Key.F3 -> KeyCode.F3
        Key.F4 -> KeyCode.F4
        Key.F5 -> KeyCode.F5
        Key.F6 -> KeyCode.F6
        Key.F7 -> KeyCode.F7
        Key.F8 -> KeyCode.F8
        Key.F9 -> KeyCode.F9
        Key.F10 -> KeyCode.F10
        Key.F11 -> KeyCode.F11
        Key.F12 -> KeyCode.F12
        Key.NumLock -> KeyCode.NUM_LOCK
        Key.ScrollLock -> KeyCode.SCROLL_LOCK
        Key.PrintScreen -> KeyCode.PRINT_SCREEN
        Key.CapsLock -> KeyCode.CAPS_LOCK
        Key.Comma -> KeyCode.COMMA
        Key.Period -> KeyCode.PERIOD
        Key.Slash -> KeyCode.SLASH
        Key.Backslash -> KeyCode.BACKSLASH
        Key.Semicolon -> KeyCode.SEMICOLON
        Key.Apostrophe -> KeyCode.APOSTROPHE
        Key.LeftBracket -> KeyCode.OPEN_BRACKET
        Key.RightBracket -> KeyCode.CLOSE_BRACKET
        Key.Minus -> KeyCode.MINUS
        Key.Equals -> KeyCode.EQUALS
        Key.Grave -> KeyCode.BACK_QUOTE
        Key.MetaLeft, Key.MetaRight -> KeyCode.META
        Key.Help -> KeyCode.HELP
        Key.Cut -> KeyCode.CUT
        Key.Copy -> KeyCode.COPY
        Key.Paste -> KeyCode.PASTE
        else -> KeyCode.UNKNOWN
    }
}
