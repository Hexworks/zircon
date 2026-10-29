package org.hexworks.zircon.terminal.input

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEvent

/**
 * Parses the bytes a terminal sends while the kitty keyboard protocol (all keys as escape
 * codes, with event types and associated text) and SGR mouse reporting are on. Bytes are
 * fed as they arrive; an incomplete sequence waits for the rest.
 *
 * Mouse coordinates arrive as terminal cells; [toGrid] maps them into the grid.
 */
public class InputParser(private val toGrid: (column: Int, row: Int) -> Position) {

    /** Terminal replies that are not input: the kitty flags answer and the DA1 answer. */
    public interface ReplyListener {
        public fun onKittyKeyboardFlags(flags: Int) {}
        public fun onDeviceAttributes() {}
    }

    public var replyListener: ReplyListener? = null

    private var pending = ByteArray(0)
    private var lastMouse = Position.UNKNOWN
    private var pressPosition = Position.UNKNOWN
    private var dragging = false

    /** Parses [bytes] (the first [length] of them) and returns the events they encode. */
    public fun feed(bytes: ByteArray, length: Int = bytes.size): List<UIEvent> {
        val input = if (pending.isEmpty()) bytes.copyOf(length) else pending + bytes.copyOf(length)
        val events = ArrayList<UIEvent>()
        var i = 0
        while (i < input.size) {
            val b = input[i].toInt() and 0xFF
            if (b == 0x1B) {
                val consumed = parseEscape(input, i, events)
                if (consumed == 0) break // incomplete: wait for more bytes
                i += consumed
            } else {
                i += parseLegacy(input, i, events)
            }
        }
        pending = if (i < input.size) input.copyOfRange(i, input.size) else EMPTY
        return events
    }

    /** Returns the bytes consumed, or 0 when the sequence is not complete yet. */
    private fun parseEscape(input: ByteArray, start: Int, events: MutableList<UIEvent>): Int {
        if (start + 1 >= input.size) {
            // A lone ESC at the end of the input: with the kitty protocol Esc is a CSI u
            // sequence, so this is a real escape key press (legacy or paste).
            events.addKey(KeyboardEventType.KEY_PRESSED, KeyCode.ESCAPE, "", 0)
            return 1
        }
        val second = input[start + 1].toInt() and 0xFF
        if (second != '['.code) {
            // ESC + something: Alt-modified legacy key; deliver the key with alt.
            if (second == 'O'.code) {
                if (start + 2 >= input.size) return 0
                val final = input[start + 2].toInt().toChar()
                functionalLetter(final, 0, 1, events)
                return 3
            }
            val consumed = parseLegacy(input, start + 1, events, alt = true)
            return 1 + consumed
        }
        // CSI: parameters 0x30..0x3F, intermediates 0x20..0x2F, final 0x40..0x7E
        var i = start + 2
        while (i < input.size && (input[i].toInt() and 0xFF) in 0x30..0x3F) i++
        val paramsEnd = i
        while (i < input.size && (input[i].toInt() and 0xFF) in 0x20..0x2F) i++
        if (i >= input.size) return 0
        val final = input[i].toInt().toChar()
        val params = input.decodeToString(start + 2, paramsEnd)
        val intermediates = input.decodeToString(paramsEnd, i)
        handleCsi(params, intermediates, final, events)
        return i - start + 1
    }

    private fun handleCsi(params: String, intermediates: String, final: Char, events: MutableList<UIEvent>) {
        when {
            params.startsWith("<") && (final == 'M' || final == 'm') -> mouse(params.substring(1), final == 'M', events)
            params.startsWith("?") && final == 'u' -> replyListener?.onKittyKeyboardFlags(params.substring(1).toIntOrNull() ?: 0)
            params.startsWith("?") && final == 'c' -> replyListener?.onDeviceAttributes()
            params.startsWith("?") || params.startsWith(">") || intermediates.isNotEmpty() -> Unit
            final == 'u' -> kittyKey(params, events)
            final == '~' -> tildeKey(params, events)
            final in "ABCDEFHPQS" -> {
                val fields = params.split(';')
                val (mods, type) = modsAndType(fields.getOrNull(1))
                functionalLetter(final, mods, type, events)
            }
        }
    }

    private fun kittyKey(params: String, events: MutableList<UIEvent>) {
        val fields = params.split(';')
        val keyField = fields[0].split(':')
        val codePoint = keyField[0].toIntOrNull() ?: return
        val shiftedKey = keyField.getOrNull(1)?.toIntOrNull() ?: 0
        val (mods, type) = modsAndType(fields.getOrNull(1))
        val text = fields.getOrNull(2)
            ?.split(':')
            ?.mapNotNull { it.toIntOrNull() }
            ?.joinToString("") { codePointToString(it) }
            ?: ""
        val code = keyCodeOf(codePoint, shiftedKey)
        val key = when {
            text.isNotEmpty() -> text
            codePoint in 0x20..0x7E && mods and (MOD_CTRL or MOD_ALT or MOD_SUPER) == 0 ->
                codePointToString(if (shiftedKey != 0) shiftedKey else codePoint)
            codePoint == 13 -> "\n"
            codePoint == 9 -> "\t"
            else -> ""
        }
        events.addKey(type, code, key, mods)
    }

    private fun tildeKey(params: String, events: MutableList<UIEvent>) {
        val fields = params.split(';')
        val number = fields[0].split(':')[0].toIntOrNull() ?: return
        val (mods, type) = modsAndType(fields.getOrNull(1))
        val code = when (number) {
            2 -> KeyCode.INSERT
            3 -> KeyCode.DELETE
            5 -> KeyCode.PAGE_UP
            6 -> KeyCode.PAGE_DOWN
            7 -> KeyCode.HOME
            8 -> KeyCode.END
            11 -> KeyCode.F1
            12 -> KeyCode.F2
            13 -> KeyCode.F3
            14 -> KeyCode.F4
            15 -> KeyCode.F5
            17 -> KeyCode.F6
            18 -> KeyCode.F7
            19 -> KeyCode.F8
            20 -> KeyCode.F9
            21 -> KeyCode.F10
            23 -> KeyCode.F11
            24 -> KeyCode.F12
            29 -> KeyCode.CONTEXT_MENU
            else -> KeyCode.UNKNOWN
        }
        events.addKey(type, code, "", mods)
    }

    private fun functionalLetter(final: Char, mods: Int, type: KeyboardEventType, events: MutableList<UIEvent>) {
        val code = when (final) {
            'A' -> KeyCode.UP
            'B' -> KeyCode.DOWN
            'C' -> KeyCode.RIGHT
            'D' -> KeyCode.LEFT
            'E' -> KeyCode.BEGIN
            'F' -> KeyCode.END
            'H' -> KeyCode.HOME
            'P' -> KeyCode.F1
            'Q' -> KeyCode.F2
            'R' -> KeyCode.F3
            'S' -> KeyCode.F4
            else -> return
        }
        events.addKey(type, code, "", mods)
    }

    private fun functionalLetter(final: Char, mods: Int, typeNumber: Int, events: MutableList<UIEvent>) =
        functionalLetter(final, mods, eventType(typeNumber), events)

    /** Parses `modifiers[:event-type]`; modifiers are sent as value + 1. */
    private fun modsAndType(field: String?): Pair<Int, KeyboardEventType> {
        if (field.isNullOrEmpty()) return 0 to KeyboardEventType.KEY_PRESSED
        val parts = field.split(':')
        val mods = (parts[0].toIntOrNull() ?: 1) - 1
        val type = eventType(parts.getOrNull(1)?.toIntOrNull() ?: 1)
        return mods to type
    }

    private fun eventType(number: Int): KeyboardEventType = when (number) {
        3 -> KeyboardEventType.KEY_RELEASED
        else -> KeyboardEventType.KEY_PRESSED // 1 press, 2 repeat
    }

    private fun mouse(params: String, pressOrMotion: Boolean, events: MutableList<UIEvent>) {
        val fields = params.split(';')
        if (fields.size < 3) return
        val cb = fields[0].toIntOrNull() ?: return
        val column = (fields[1].toIntOrNull() ?: return) - 1
        val row = (fields[2].toIntOrNull() ?: return) - 1
        val position = toGrid(column, row)
        val low = cb and 3
        val motion = cb and 32 != 0
        val wheel = cb and 64 != 0
        val button = when {
            wheel -> 0
            low == 0 -> 1
            low == 1 -> 2
            low == 2 -> 3
            else -> 0
        }
        val type = when {
            wheel -> if (low == 0) MouseEventType.MOUSE_WHEEL_ROTATED_UP else MouseEventType.MOUSE_WHEEL_ROTATED_DOWN
            motion -> if (dragging) MouseEventType.MOUSE_DRAGGED else MouseEventType.MOUSE_MOVED
            pressOrMotion -> {
                dragging = true
                pressPosition = position
                MouseEventType.MOUSE_PRESSED
            }
            else -> {
                dragging = false
                MouseEventType.MOUSE_RELEASED
            }
        }
        if ((type == MouseEventType.MOUSE_MOVED || type == MouseEventType.MOUSE_DRAGGED) && position == lastMouse) return
        lastMouse = position
        val event = MouseEvent(type, button, position)
        events.add(event)
        if (type == MouseEventType.MOUSE_RELEASED && position == pressPosition) {
            events.add(event.copy(type = MouseEventType.MOUSE_CLICKED))
        }
    }

    /** A byte that is not an escape sequence: pasted or legacy text, control characters. */
    private fun parseLegacy(input: ByteArray, start: Int, events: MutableList<UIEvent>, alt: Boolean = false): Int {
        val b = input[start].toInt() and 0xFF
        val mods = if (alt) MOD_ALT else 0
        when {
            b == 13 || b == 10 -> events.addKey(KeyboardEventType.KEY_PRESSED, KeyCode.ENTER, "\n", mods)
            b == 9 -> events.addKey(KeyboardEventType.KEY_PRESSED, KeyCode.TAB, "\t", mods)
            b == 127 || b == 8 -> events.addKey(KeyboardEventType.KEY_PRESSED, KeyCode.BACKSPACE, "", mods)
            b < 0x20 -> events.addKey(KeyboardEventType.KEY_PRESSED, keyCodeOf(b + 96, 0), "", mods or MOD_CTRL)
            b < 0x80 -> events.addKey(KeyboardEventType.KEY_PRESSED, keyCodeOf(b, 0), b.toChar().toString(), mods)
            else -> {
                val length = when {
                    b and 0xE0 == 0xC0 -> 2
                    b and 0xF0 == 0xE0 -> 3
                    b and 0xF8 == 0xF0 -> 4
                    else -> 1
                }
                if (start + length > input.size) return input.size - start // drop a truncated tail
                val text = input.decodeToString(start, start + length)
                events.addKey(KeyboardEventType.KEY_PRESSED, KeyCode.UNKNOWN, text, mods)
                return length
            }
        }
        return 1
    }

    private fun MutableList<UIEvent>.addKey(type: KeyboardEventType, code: KeyCode, key: String, mods: Int) {
        val event = KeyboardEvent(
            type = type,
            code = code,
            key = key,
            ctrlDown = mods and MOD_CTRL != 0,
            altDown = mods and MOD_ALT != 0,
            metaDown = mods and (MOD_SUPER or MOD_META) != 0,
            shiftDown = mods and MOD_SHIFT != 0,
        )
        add(event)
        if (type == KeyboardEventType.KEY_PRESSED && key.isNotEmpty() && !event.ctrlDown && !event.altDown) {
            add(event.copy(type = KeyboardEventType.KEY_TYPED))
        }
    }

    private fun codePointToString(codePoint: Int): String = when {
        codePoint < 0x10000 -> codePoint.toChar().toString()
        else -> {
            val v = codePoint - 0x10000
            charArrayOf(((v shr 10) + 0xD800).toChar(), ((v and 0x3FF) + 0xDC00).toChar()).concatToString()
        }
    }

    public companion object {
        private val EMPTY = ByteArray(0)
        public const val MOD_SHIFT: Int = 1
        public const val MOD_ALT: Int = 2
        public const val MOD_CTRL: Int = 4
        public const val MOD_SUPER: Int = 8
        public const val MOD_META: Int = 32
    }
}
