package org.hexworks.zircon.terminal.input

import org.hexworks.zircon.api.uievent.KeyCode

/**
 * Maps a kitty keyboard protocol key code (a Unicode code point for printable keys, a
 * private-use number for functional keys) to a [KeyCode]. [shifted] is the shifted key the
 * terminal reports alongside, used for keys like `!` whose base key is `1`.
 */
internal fun keyCodeOf(codePoint: Int, shifted: Int): KeyCode {
    val base = codePoint.lowercaseLetter()
    return when (base) {
        in 'a'.code..'z'.code -> KeyCode.valueOf("KEY_${base.toChar().uppercaseChar()}")
        in '0'.code..'9'.code -> KeyCode.valueOf("DIGIT_${base.toChar()}")
        27 -> KeyCode.ESCAPE
        13 -> KeyCode.ENTER
        9 -> KeyCode.TAB
        127 -> KeyCode.BACKSPACE
        8 -> KeyCode.BACKSPACE
        ' '.code -> KeyCode.SPACE
        ','.code -> KeyCode.COMMA
        '-'.code -> KeyCode.MINUS
        '.'.code -> KeyCode.PERIOD
        '/'.code -> KeyCode.SLASH
        ';'.code -> KeyCode.SEMICOLON
        '='.code -> KeyCode.EQUALS
        '['.code -> KeyCode.OPEN_BRACKET
        '\\'.code -> KeyCode.BACKSLASH
        ']'.code -> KeyCode.CLOSE_BRACKET
        '\''.code -> KeyCode.APOSTROPHE
        '`'.code -> KeyCode.BACK_QUOTE
        '&'.code -> KeyCode.AMPERSAND
        '*'.code -> KeyCode.ASTERISK
        '"'.code -> KeyCode.QUOTE
        '<'.code -> KeyCode.LESS
        '>'.code -> KeyCode.GREATER
        '{'.code -> KeyCode.BRACE_LEFT
        '}'.code -> KeyCode.BRACE_RIGHT
        '@'.code -> KeyCode.AT
        ':'.code -> KeyCode.COLON
        '^'.code -> KeyCode.CIRCUMFLEX
        '$'.code -> KeyCode.DOLLAR
        '!'.code -> KeyCode.EXCLAMATION_MARK
        '('.code -> KeyCode.LEFT_PARENTHESIS
        ')'.code -> KeyCode.RIGHT_PARENTHESIS
        '#'.code -> KeyCode.NUMBER_SIGN
        '+'.code -> KeyCode.PLUS
        '_'.code -> KeyCode.UNDERSCORE
        57358 -> KeyCode.CAPS_LOCK
        57359 -> KeyCode.SCROLL_LOCK
        57360 -> KeyCode.NUM_LOCK
        57361 -> KeyCode.PRINT_SCREEN
        57362 -> KeyCode.PAUSE
        57363 -> KeyCode.CONTEXT_MENU
        in 57364..57375 -> KeyCode.valueOf("F${base - 57364 + 13}")
        in 57399..57408 -> KeyCode.valueOf("NUMPAD_${base - 57399}")
        57409 -> KeyCode.DECIMAL
        57410 -> KeyCode.DIVIDE
        57411 -> KeyCode.MULTIPLY
        57412 -> KeyCode.SUBTRACT
        57413 -> KeyCode.ADD
        57414 -> KeyCode.ENTER
        57415 -> KeyCode.EQUALS
        57416 -> KeyCode.SEPARATOR
        57417 -> KeyCode.KP_LEFT
        57418 -> KeyCode.KP_RIGHT
        57419 -> KeyCode.KP_UP
        57420 -> KeyCode.KP_DOWN
        57421 -> KeyCode.PAGE_UP
        57422 -> KeyCode.PAGE_DOWN
        57423 -> KeyCode.HOME
        57424 -> KeyCode.END
        57425 -> KeyCode.INSERT
        57426 -> KeyCode.DELETE
        57427 -> KeyCode.BEGIN
        57441, 57447 -> KeyCode.SHIFT
        57442, 57448 -> KeyCode.CONTROL
        57443, 57449 -> KeyCode.ALT
        57444, 57450 -> KeyCode.META
        57446, 57452 -> KeyCode.META
        57453 -> KeyCode.ALT_GRAPH
        else -> if (shifted != 0 && shifted != codePoint) keyCodeOf(shifted, 0) else KeyCode.UNKNOWN
    }
}

private fun Int.lowercaseLetter(): Int = if (this in 'A'.code..'Z'.code) this + 32 else this
