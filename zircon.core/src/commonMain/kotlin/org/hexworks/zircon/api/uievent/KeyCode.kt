package org.hexworks.zircon.api.uievent

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.util.TextUtils

enum class KeyCode(
        /**
         * The unicode key code for the given key
         */
        val code: Int) {

    /**
     * This [KeyCode] represents a key which is unknown,
     * thus we have no information about it.
     */
    UNKNOWN(-1),
    ENTER('\n'.toInt()),
    BACKSPACE('\b'.toInt()),
    TAB('\t'.toInt()),
    CANCEL(0x03),
    CLEAR(0x0C),
    SHIFT(0x10),
    CONTROL(0x11),
    ALT(0x12),
    PAUSE(0x13),
    CAPS_LOCK(0x14),
    ESCAPE(0x1B),
    SPACE(0x20),
    PAGE_UP(0x21),
    PAGE_DOWN(0x22),
    END(0x23),
    HOME(0x24),
    LEFT(0x25),
    UP(0x26),
    RIGHT(0x27),
    DOWN(0x28),
    COMMA(0x2C),
    MINUS(0x2D),
    PERIOD(0x2E),
    SLASH(0x2F),

    // numbers
    DIGIT_0(0x30),
    DIGIT_1(0x31),
    DIGIT_2(0x32),
    DIGIT_3(0x33),
    DIGIT_4(0x34),
    DIGIT_5(0x35),
    DIGIT_6(0x36),
    DIGIT_7(0x37),
    DIGIT_8(0x38),
    DIGIT_9(0x39),

    SEMICOLON(0x3B),
    EQUALS(0x3D),

    // characters
    KEY_A(0x41),
    KEY_B(0x42),
    KEY_C(0x43),
    KEY_D(0x44),
    KEY_E(0x45),
    KEY_F(0x46),
    KEY_G(0x47),
    KEY_H(0x48),
    KEY_I(0x49),
    KEY_J(0x4A),
    KEY_K(0x4B),
    KEY_L(0x4C),
    KEY_M(0x4D),
    KEY_N(0x4E),
    KEY_O(0x4F),
    KEY_P(0x50),
    KEY_Q(0x51),
    KEY_R(0x52),
    KEY_S(0x53),
    KEY_T(0x54),
    KEY_U(0x55),
    KEY_V(0x56),
    KEY_W(0x57),
    KEY_X(0x58),
    KEY_Y(0x59),
    KEY_Z(0x5A),

    OPEN_BRACKET(0x5B),
    BACKSLASH(0x5C),
    CLOSE_BRACKET(0x5D),

    // numpad numbers
    NUMPAD_0(0x60),
    NUMPAD_1(0x61),
    NUMPAD_2(0x62),
    NUMPAD_3(0x63),
    NUMPAD_4(0x64),
    NUMPAD_5(0x65),
    NUMPAD_6(0x66),
    NUMPAD_7(0x67),
    NUMPAD_8(0x68),
    NUMPAD_9(0x69),
    MULTIPLY(0x6A),
    ADD(0x6B),
    SEPARATOR(0x6C),
    SUBTRACT(0x6D),
    DECIMAL(0x6E),
    DIVIDE(0x6F),
    DELETE(0x7F),
    NUM_LOCK(0x90),
    SCROLL_LOCK(0x91),

    // function keys
    F1(0x70),
    F2(0x71),
    F3(0x72),
    F4(0x73),
    F5(0x74),
    F6(0x75),
    F7(0x76),
    F8(0x77),
    F9(0x78),
    F10(0x79),
    F11(0x7A),
    F12(0x7B),
    F13(0xF000),
    F14(0xF001),
    F15(0xF002),
    F16(0xF003),
    F17(0xF004),
    F18(0xF005),
    F19(0xF006),
    F20(0xF007),
    F21(0xF008),
    F22(0xF009),
    F23(0xF00A),
    F24(0xF00B),
    PRINT_SCREEN(0x9A),
    INSERT(0x9B),
    HELP(0x9C),
    META(0x9D),
    BACK_QUOTE(0xC0),
    APOSTROPHE(0xDE),

    // keypad keys
    KP_UP(0xE0),
    KP_DOWN(0xE1),
    KP_LEFT(0xE2),
    KP_RIGHT(0xE3),

    // dead keys
    DEAD_GRAVE(0x80),
    DEAD_ACUTE(0x81),
    DEAD_CIRCUMFLEX(0x82),
    DEAD_TILDE(0x83),
    DEAD_MACRON(0x84),
    DEAD_BREVE(0x85),
    DEAD_ABOVEDOT(0x86),
    DEAD_DIAERESIS(0x87),
    DEAD_ABOVERING(0x88),
    DEAD_DOUBLEACUTE(0x89),
    DEAD_CARON(0x8a),
    DEAD_CEDILLA(0x8b),
    DEAD_OGONEK(0x8c),
    DEAD_IOTA(0x8d),
    DEAD_VOICED_SOUND(0x8e),
    DEAD_SEMIVOICED_SOUND(0x8f),

    AMPERSAND(0x96),
    ASTERISK(0x97),
    QUOTE(0x98),
    LESS(0x99),
    GREATER(0xa0),
    BRACE_LEFT(0xa1),
    BRACE_RIGHT(0xa2),
    AT(0x0200),
    COLON(0x0201),
    CIRCUMFLEX(0x0202),
    DOLLAR(0x0203),
    EURO_SIGN(0x0204),
    EXCLAMATION_MARK(0x0205),
    INVERTED_EXCLAMATION_MARK(0x0206),
    LEFT_PARENTHESIS(0x0207),
    NUMBER_SIGN(0x0208),
    PLUS(0x0209),
    RIGHT_PARENTHESIS(0x020A),
    UNDERSCORE(0x020B),
    WINDOWS(0x020C),
    CONTEXT_MENU(0x020D),
    CUT(0xFFD1),
    COPY(0xFFCD),
    PASTE(0xFFCF),
    UNDO(0xFFCB),
    AGAIN(0xFFC9),
    FIND(0xFFD0),
    PROPS(0xFFCA),
    STOP(0xFFC8),
    COMPOSE(0xFF20),
    ALT_GRAPH(0xFF7E),
    BEGIN(0xFF58);

    /**
     * Converts this [KeyCode] to a [Char] if possible.
     */
    fun toChar(): Maybe<Char> {
        val char = code.toChar()
        return Maybe.ofNullable(if (TextUtils.isPrintableCharacter(char)) {
            char
        } else null)
    }

    /**
     * Tells wheher this is the [UNKNOWN] [KeyCode].
     */
    fun isUnknown() = this == UNKNOWN

    companion object {

        /**
         * Returns the corresponding [KeyCode] for the given [char] (if any).
         */
        fun findByChar(char: Char): KeyCode {
            return values().firstOrNull { it.code.toChar() == char } ?: UNKNOWN
        }

        /**
         * Returns the corresponding [KeyCode] for the given [code] (if any).
         */
        fun findByCode(code: Int): KeyCode {
            return values().firstOrNull { it.code == code } ?: UNKNOWN
        }
    }

}
