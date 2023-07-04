package org.hexworks.zircon.renderer.korge

import korlibs.event.Key
import korlibs.event.KeyEvent
import korlibs.korge.input.KeysEvents
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.grid.InternalTileGrid

class KeyboardEventListener(
    private val tileGrid: InternalTileGrid
) {

    private val events = mutableListOf<Pair<KeyboardEvent, UIEventPhase>>()

    fun drainEvents(): Iterable<Pair<KeyboardEvent, UIEventPhase>> {
        return events.toList().also {
            events.clear()
        }
    }

    fun handleEvents(events: KeysEvents) {
        events.apply {
            fun kevent(e: KeyEvent) {
                val type = when (e.type) {
                    KeyEvent.Type.TYPE -> KeyboardEventType.KEY_TYPED
                    KeyEvent.Type.UP -> KeyboardEventType.KEY_RELEASED
                    KeyEvent.Type.DOWN -> KeyboardEventType.KEY_PRESSED
                }
                tileGrid.process(
                    KeyboardEvent(type, e.characters(), e.key.toKeyCode(), e.ctrl, e.alt, e.meta, e.shift),
                    UIEventPhase.TARGET
                )
            }
            down { kevent(it) }
            up { kevent(it) }
            typed { kevent(it) }
        }
    }
}

fun Key.toKeyCode(): KeyCode = when (this) {
    Key.ENTER -> KeyCode.ENTER
    Key.BACKSPACE -> KeyCode.BACKSPACE
    Key.TAB -> KeyCode.TAB
    Key.CANCEL -> KeyCode.CANCEL
    Key.CLEAR -> KeyCode.CLEAR
    Key.SHIFT -> KeyCode.SHIFT
    Key.CONTROL -> KeyCode.CONTROL
    Key.ALT -> KeyCode.ALT
    Key.PAUSE -> KeyCode.PAUSE
    Key.CAPS_LOCK -> KeyCode.CAPS_LOCK
    Key.ESCAPE -> KeyCode.ESCAPE
    Key.SPACE -> KeyCode.SPACE
    Key.PAGE_UP -> KeyCode.PAGE_UP
    Key.PAGE_DOWN -> KeyCode.PAGE_DOWN
    Key.END -> KeyCode.END
    Key.HOME -> KeyCode.HOME
    Key.LEFT -> KeyCode.LEFT
    Key.UP -> KeyCode.UP
    Key.RIGHT -> KeyCode.RIGHT
    Key.DOWN -> KeyCode.DOWN
    Key.COMMA -> KeyCode.COMMA
    Key.MINUS -> KeyCode.MINUS
    Key.PERIOD -> KeyCode.PERIOD
    Key.SLASH -> KeyCode.SLASH
    Key.N0 -> KeyCode.DIGIT_0
    Key.N1 -> KeyCode.DIGIT_1
    Key.N2 -> KeyCode.DIGIT_2
    Key.N3 -> KeyCode.DIGIT_3
    Key.N4 -> KeyCode.DIGIT_4
    Key.N5 -> KeyCode.DIGIT_5
    Key.N6 -> KeyCode.DIGIT_6
    Key.N7 -> KeyCode.DIGIT_7
    Key.N8 -> KeyCode.DIGIT_8
    Key.N9 -> KeyCode.DIGIT_9
    Key.SEMICOLON -> KeyCode.SEMICOLON
    Key.EQUAL -> KeyCode.EQUALS
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
    Key.OPEN_BRACKET -> KeyCode.OPEN_BRACKET
    Key.BACKSLASH -> KeyCode.BACKSLASH
    Key.CLOSE_BRACKET -> KeyCode.CLOSE_BRACKET
    //Key.N0 -> KeyCode.NUMPAD_0
    //Key.N1 -> KeyCode.NUMPAD_1
    //Key.N2 -> KeyCode.NUMPAD_2
    //Key.N3 -> KeyCode.NUMPAD_3
    //Key.N4 -> KeyCode.NUMPAD_4
    //Key.N5 -> KeyCode.NUMPAD_5
    //Key.N6 -> KeyCode.NUMPAD_6
    //Key.N7 -> KeyCode.NUMPAD_7
    //Key.N8 -> KeyCode.NUMPAD_8
    //Key.N9 -> KeyCode.NUMPAD_9
    Key.KP_MULTIPLY -> KeyCode.MULTIPLY
    Key.KP_ADD -> KeyCode.ADD
    Key.KP_SEPARATOR -> KeyCode.SEPARATOR
    Key.KP_SUBTRACT -> KeyCode.SUBTRACT
    Key.KP_DECIMAL -> KeyCode.DECIMAL
    Key.KP_DIVIDE -> KeyCode.DIVIDE
    Key.DELETE -> KeyCode.DELETE
    Key.NUM_LOCK -> KeyCode.NUM_LOCK
    Key.SCROLL_LOCK -> KeyCode.SCROLL_LOCK
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
    Key.F13 -> KeyCode.F13
    Key.F14 -> KeyCode.F14
    Key.F15 -> KeyCode.F15
    Key.F16 -> KeyCode.F16
    Key.F17 -> KeyCode.F17
    Key.F18 -> KeyCode.F18
    Key.F19 -> KeyCode.F19
    Key.F20 -> KeyCode.F20
    Key.F21 -> KeyCode.F21
    Key.F22 -> KeyCode.F22
    Key.F23 -> KeyCode.F23
    Key.F24 -> KeyCode.F24
    Key.PRINT_SCREEN -> KeyCode.PRINT_SCREEN
    Key.INSERT -> KeyCode.INSERT
    Key.HELP -> KeyCode.HELP
    Key.META -> KeyCode.META
    Key.BACKQUOTE -> KeyCode.BACK_QUOTE
    Key.APOSTROPHE -> KeyCode.APOSTROPHE
    Key.KP_UP -> KeyCode.KP_UP
    Key.KP_DOWN -> KeyCode.KP_DOWN
    Key.KP_LEFT -> KeyCode.KP_LEFT
    Key.KP_RIGHT -> KeyCode.KP_RIGHT
    Key.GRAVE -> KeyCode.DEAD_GRAVE
    //Key.DEAD_ACUTE -> KeyCode.DEAD_ACUTE
    //Key.DEAD_CIRCUMFLEX -> KeyCode.DEAD_CIRCUMFLEX
    //Key.DEAD_TILDE -> KeyCode.DEAD_TILDE
    //Key.DEAD_MACRON -> KeyCode.DEAD_MACRON
    //Key.DEAD_BREVE -> KeyCode.DEAD_BREVE
    //Key.DEAD_ABOVEDOT -> KeyCode.DEAD_ABOVEDOT
    //Key.DEAD_DIAERESIS -> KeyCode.DEAD_DIAERESIS
    //Key.DEAD_ABOVERING -> KeyCode.DEAD_ABOVERING
    //Key.DEAD_DOUBLEACUTE -> KeyCode.DEAD_DOUBLEACUTE
    //Key.DEAD_CARON -> KeyCode.DEAD_CARON
    //Key.DEAD_CEDILLA -> KeyCode.DEAD_CEDILLA
    //Key.DEAD_OGONEK -> KeyCode.DEAD_OGONEK
    //Key.DEAD_IOTA -> KeyCode.DEAD_IOTA
    //Key.DEAD_VOICED_SOUND -> KeyCode.DEAD_VOICED_SOUND
    //Key.DEAD_SEMIVOICED_SOUND -> KeyCode.DEAD_SEMIVOICED_SOUND
    //Key.AMPERSAND -> KeyCode.AMPERSAND
    //Key.ASTERISK -> KeyCode.ASTERISK
    Key.QUOTE -> KeyCode.QUOTE
    //Key.LESS -> KeyCode.LESS
    //Key.GREATER -> KeyCode.GREATER
    //Key.BRACE_LEFT -> KeyCode.BRACE_LEFT
    //Key.BRACE_RIGHT -> KeyCode.BRACE_RIGHT
    Key.AT -> KeyCode.AT
    //Key.COLON -> KeyCode.COLON
    //Key.CIRCUMFLEX -> KeyCode.CIRCUMFLEX
    //Key.DOLLAR -> KeyCode.DOLLAR
    //Key.EURO_SIGN -> KeyCode.EURO_SIGN
    //Key.EXCLAMATION_MARK -> KeyCode.EXCLAMATION_MARK
    //Key.INVERTED_EXCLAMATION_MARK -> KeyCode.INVERTED_EXCLAMATION_MARK
    //Key.LEFT_PARENTHESIS -> KeyCode.LEFT_PARENTHESIS
    //Key.NUMBER_SIGN -> KeyCode.NUMBER_SIGN
    Key.PLUS -> KeyCode.PLUS
    //Key.RIGHT_PARENTHESIS -> KeyCode.RIGHT_PARENTHESIS
    //Key.UNDERSCORE -> KeyCode.UNDERSCORE
    //Key.WINDOWS -> KeyCode.WINDOWS
    //Key.CONTEXT_MENU -> KeyCode.CONTEXT_MENU
    Key.CUT -> KeyCode.CUT
    Key.COPY -> KeyCode.COPY
    Key.PASTE -> KeyCode.PASTE
    //Key.UNDO -> KeyCode.UNDO
    //Key.AGAIN -> KeyCode.AGAIN
    //Key.FIND -> KeyCode.FIND
    //Key.PROPS -> KeyCode.PROPS
    //Key.STOP -> KeyCode.STOP
    //Key.COMPOSE -> KeyCode.COMPOSE
    //Key.ALT_GRAPH -> KeyCode.ALT_GRAPH
    //Key.BEGIN -> KeyCode.BEGIN
    else -> KeyCode.UNKNOWN
}