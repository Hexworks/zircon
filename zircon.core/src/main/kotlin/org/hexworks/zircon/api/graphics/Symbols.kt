@file:Suppress("unused")

package org.hexworks.zircon.api.graphics

/**
 * Some text graphics, taken from http://en.wikipedia.org/wiki/Codepage_437 but converted
 * to its UTF-8 counterpart.
 * This class it mostly here to help out with building text GUIs when you don't have a
 * handy Unicode chart available.
 * This object was taken from Lanterna (https://github.com/mabe02/lanterna) as-is.
 */
object Symbols {

    /**
     * ☺
     */
    const val FACE_WHITE: Char = 0x263A.toChar()
    /**
     * ☻
     */
    const val FACE_BLACK: Char = 0x263B.toChar()
    /**
     * ♥
     */
    const val HEART: Char = 0x2665.toChar()
    /**
     * ♦
     */
    const val DIAMOND: Char = 0x2666.toChar()
    /**
     * ♣
     */
    const val CLUB: Char = 0x2663.toChar()
    /**
     * ♠
     */
    const val SPADES: Char = 0x2660.toChar()
    /**
     * •
     */
    const val BULLET: Char = 0x2022.toChar()
    /**
     * ◘
     */
    const val INVERSE_BULLET: Char = 0x25D8.toChar()
    /**
     * ○
     */
    const val WHITE_CIRCLE: Char = 0x25CB.toChar()
    /**
     * ◙
     */
    const val INVERSE_WHITE_CIRCLE: Char = 0x25D9.toChar()
    /**
     * ♂
     */
    const val MALE: Char = 0x2642.toChar()
    /**
     * ♀
     */
    const val FEMALE: Char = 0x2640.toChar()
    /**
     * ♪
     */
    const val NOTE: Char = 0x266A.toChar()
    /**
     * ♫
     */
    const val DOUBLE_NOTE: Char = 0x266B.toChar()
    /**
     * ☼
     */
    const val SOLAR_SYMBOL: Char = 0x263C.toChar()
    /**
     * ►
     */
    const val TRIANGLE_RIGHT_POINTING_BLACK: Char = 0x25BA.toChar()
    /**
     * ◄
     */
    const val TRIANGLE_LEFT_POINTING_BLACK: Char = 0x25C4.toChar()
    /**
     * ↕
     */
    const val ARROW_UP_DOWN: Char = 0x2195.toChar()
    /**
     * ‼
     */
    const val DOUBLE_EXCLAMATION_MARK: Char = 0x203C.toChar()
    /**
     * ¶
     */
    const val PILCROW: Char = 0x00B6.toChar()
    /**
     * §
     */
    const val SECTION_SIGN: Char = 0x00A7.toChar()
    /**
     * ▬
     */
    const val BLACK_RECTANGLE: Char = 0x25AC.toChar()
    /**
     * ↨
     */
    const val ARROW_UP_DOWN_WITH_BASE: Char = 0x21A8.toChar()
    /**
     * ↑
     */
    const val ARROW_UP: Char = 0x2191.toChar()
    /**
     * ↓
     */
    const val ARROW_DOWN: Char = 0x2193.toChar()
    /**
     * →
     */
    const val ARROW_RIGHT: Char = 0x2192.toChar()
    /**
     * ←
     */
    const val ARROW_LEFT: Char = 0x2190.toChar()
    /**
     * ∟
     */
    const val RIGHT_ANGLE: Char = 0x221F.toChar()
    /**
     * ↔
     */
    const val ARROW_LEFT_RIGHT: Char = 0x2194.toChar()
    /**
     * ▲
     */
    const val TRIANGLE_UP_POINTING_BLACK: Char = 0x25B2.toChar()
    /**
     * ▼
     */
    const val TRIANGLE_DOWN_POINTING_BLACK: Char = 0x25BC.toChar()
    /**
     * ^
     */
    const val CIRCUMFLEX: Char = 0x005E.toChar()
    /**
     * ⌂
     */
    const val HOUSE: Char = 0x2302.toChar()
    /**
     * ¢
     */
    const val CENT: Char = 0x00A2.toChar()
    /**
     * £
     */
    const val POUND: Char = 0x00A3.toChar()
    /**
     * ¥
     */
    const val YEN: Char = 0x00A5.toChar()
    /**
     * ₧
     */
    const val PESETA: Char = 0x20A7.toChar()
    /**
     * ƒ
     */
    const val F_WITH_HOOK: Char = 0x0192.toChar()
    /**
     * ª
     */
    const val ORDINAL_INDICATOR_FEMININE: Char = 0x00AA.toChar()
    /**
     * º
     */
    const val ORDINAL_INDICATOR_MASCULINE: Char = 0x00BA.toChar()
    /**
     * ¿
     */
    const val INVERTED_QUESTION_MARK: Char = 0x00BF.toChar()
    /**
     * ⌐
     */
    const val NEGATION_LEFT: Char = 0x2310.toChar()
    /**
     * ¬
     */
    const val NEGATION_RIGHT: Char = 0x00AC.toChar()
    /**
     * ½
     */
    const val HALF: Char = 0x00BD.toChar()
    /**
     * ¼
     */
    const val QUARTER: Char = 0x00BC.toChar()
    /**
     * ¡
     */
    const val INVERTED_EXCLAMATION_MARK: Char = 0x00A1.toChar()
    /**
     * «
     */
    const val GUILLEMET_LEFT: Char = 0x00AB.toChar()
    /**
     * »
     */
    const val GUILLEMET_RIGHT: Char = 0x00BB.toChar()
    /**
     * ░
     */
    const val BLOCK_SPARSE: Char = 0x2591.toChar()
    /**
     * ▒
     */
    const val BLOCK_MIDDLE: Char = 0x2592.toChar()
    /**
     * ▓
     */
    const val BLOCK_DENSE: Char = 0x2593.toChar()
    /**
     * │
     */
    const val SINGLE_LINE_VERTICAL: Char = 0x2502.toChar()
    /**
     * ┤
     */
    const val SINGLE_LINE_T_LEFT: Char = 0x2524.toChar()
    /**
     * ╡
     */
    const val SINGLE_LINE_T_DOUBLE_LEFT: Char = 0x2561.toChar()
    /**
     * ╢
     */
    const val DOUBLE_LINE_T_SINGLE_LEFT: Char = 0x2562.toChar()
    /**
     * ╖
     */
    const val DOUBLE_LINE_SINGLE_LEFT: Char = 0x2556.toChar()
    /**
     * ╕
     */
    const val SINGLE_LINE_DOUBLE_LEFT: Char = 0x2555.toChar()
    /**
     * ╣
     */
    const val DOUBLE_LINE_T_LEFT: Char = 0x2563.toChar()
    /**
     * ║
     */
    const val DOUBLE_LINE_VERTICAL: Char = 0x2551.toChar()
    /**
     * ╗
     */
    const val DOUBLE_LINE_TOP_RIGHT_CORNER: Char = 0x2557.toChar()
    /**
     * ╝
     */
    const val DOUBLE_LINE_BOTTOM_RIGHT_CORNER: Char = 0x255D.toChar()
    /**
     * ╜
     */
    const val DOUBLE_LINE_BOTTOM_RIGHT_CORNER_SINGLE: Char = 0x255C.toChar()
    /**
     * ╛
     */
    const val SINGLE_LINE_BOTTOM_RIGHT_CORNER_DOUBLE: Char = 0x255B.toChar()
    /**
     * ┐
     */
    const val SINGLE_LINE_TOP_RIGHT_CORNER: Char = 0x2510.toChar()
    /**
     * └
     */
    const val SINGLE_LINE_BOTTOM_LEFT_CORNER: Char = 0x2514.toChar()
    /**
     * ┴
     */
    const val SINGLE_LINE_T_UP: Char = 0x2534.toChar()
    /**
     * ┬
     */
    const val SINGLE_LINE_T_DOWN: Char = 0x252C.toChar()
    /**
     * ├
     */
    const val SINGLE_LINE_T_RIGHT: Char = 0x251C.toChar()
    /**
     * ─
     */
    const val SINGLE_LINE_HORIZONTAL: Char = 0x2500.toChar()
    /**
     * ┼
     */
    const val SINGLE_LINE_CROSS: Char = 0x253C.toChar()
    /**
     * ╞
     */
    const val SINGLE_LINE_T_DOUBLE_RIGHT: Char = 0x255E.toChar()
    /**
     * ╟
     */
    const val DOUBLE_LINE_T_SINGLE_RIGHT: Char = 0x255F.toChar()
    /**
     * ╚
     */
    const val DOUBLE_LINE_BOTTOM_LEFT_CORNER: Char = 0x255A.toChar()
    /**
     * ╔
     */
    const val DOUBLE_LINE_TOP_LEFT_CORNER: Char = 0x2554.toChar()
    /**
     * ╩
     */
    const val DOUBLE_LINE_T_UP: Char = 0x2569.toChar()
    /**
     * ╦
     */
    const val DOUBLE_LINE_T_DOWN: Char = 0x2566.toChar()
    /**
     * ╠
     */
    const val DOUBLE_LINE_T_RIGHT: Char = 0x2560.toChar()
    /**
     * ═
     */
    const val DOUBLE_LINE_HORIZONTAL: Char = 0x2550.toChar()
    /**
     * ╬
     */
    const val DOUBLE_LINE_CROSS: Char = 0x256C.toChar()
    /**
     * ╧
     */
    const val DOUBLE_LINE_T_SINGLE_UP: Char = 0x2567.toChar()
    /**
     * ╨
     */
    const val SINGLE_LINE_T_DOUBLE_UP: Char = 0x2568.toChar()
    /**
     * ╤
     */
    const val DOUBLE_LINE_T_SINGLE_DOWN: Char = 0x2564.toChar()
    /**
     * ╥
     */
    const val SINGLE_LINE_T_DOUBLE_DOWN: Char = 0x2565.toChar()
    /**
     * ╙
     */
    const val DOUBLE_LINE_BOTTOM_LEFT_CORNER_SINGLE: Char = 0x2559.toChar()
    /**
     * ╘
     */
    const val SINGLE_LINE_BOTTOM_LEFT_CORNER_DOUBLE: Char = 0x2558.toChar()
    /**
     * ╒
     */
    const val SINGLE_LINE_TOP_LEFT_CORNER_DOUBLE: Char = 0x2552.toChar()
    /**
     * ╓
     */
    const val DOUBLE_LINE_TOP_LEFT_CORNER_SINGLE: Char = 0x2553.toChar()
    /**
     * ╫
     */
    const val DOUBLE_LINE_VERTICAL_SINGLE_LINE_CROSS: Char = 0x256B.toChar()
    /**
     * ╪
     */
    const val DOUBLE_LINE_HORIZONTAL_SINGLE_LINE_CROSS: Char = 0x256A.toChar()
    /**
     * ┘
     */
    const val SINGLE_LINE_BOTTOM_RIGHT_CORNER: Char = 0x2518.toChar()
    /**
     * ┌
     */
    const val SINGLE_LINE_TOP_LEFT_CORNER: Char = 0x250C.toChar()
    /**
     * █
     */
    const val BLOCK_SOLID: Char = 0x2588.toChar()
    /**
     * ▄
     */
    const val LOWER_HALF_BLOCK: Char = 0x2584.toChar()
    /**
     * ▌
     */
    const val LEFT_HALF_BLOCK: Char = 0x258C.toChar()
    /**
     * ▐
     */
    const val RIGHT_HALF_BLOCK: Char = 0x2590.toChar()
    /**
     * ▀
     */
    const val UPPER_HALF_BLOCK: Char = 0x2580.toChar()
    /**
     * α
     */
    const val ALPHA: Char = 0x03B1.toChar()
    /**
     * ß
     */
    const val BETA: Char = 0x00DF.toChar()
    /**
     * Γ
     */
    const val GAMMA: Char = 0x0393.toChar()
    /**
     * π
     */
    const val PI: Char = 0x03C0.toChar()
    /**
     * Σ
     */
    const val SIGMA_UPPERCASE: Char = 0x03A3.toChar()
    /**
     * σ
     */
    const val SIGMA_LOWERCASE: Char = 0x03C3.toChar()
    /**
     * µ
     */
    const val MU: Char = 0x00B5.toChar()
    /**
     * τ
     */
    const val TAU: Char = 0x03C4.toChar()
    /**
     * Φ
     */
    const val PHI_UPPERCASE: Char = 0x03A6.toChar()
    /**
     * Θ
     */
    const val THETA: Char = 0x0398.toChar()
    /**
     * Ω
     */
    const val OMEGA: Char = 0x03A9.toChar()
    /**
     * δ
     */
    const val DELTA: Char = 0x03B4.toChar()
    /**
     * ∞
     */
    const val INFINITY: Char = 0x221E.toChar()
    /**
     * φ
     */
    const val PHI_LOWERCASE: Char = 0x03C6.toChar()
    /**
     * ε
     */
    const val EPSILON: Char = 0x03B5.toChar()
    /**
     * ∩
     */
    const val INTERSECTION: Char = 0x2229.toChar()
    /**
     * ≡
     */
    const val TRIPLE_BAR: Char = 0x2261.toChar()
    /**
     * ±
     */
    const val PLUS_MINUS: Char = 0x00B1.toChar()
    /**
     * ≥
     */
    const val GREATER_THAN_OR_EQUAL: Char = 0x2265.toChar()
    /**
     * ≤
     */
    const val LESS_THAN_OR_EQUAL: Char = 0x2264.toChar()
    /**
     * ⌠
     */
    const val INTEGRAL_UPPER: Char = 0x2320.toChar()
    /**
     * ⌡
     */
    const val INTEGRAL_LOWER: Char = 0x2321.toChar()
    /**
     * ÷
     */
    const val OBELUS: Char = 0x00F7.toChar()
    /**
     * ≈
     */
    const val APPROXIMATION: Char = 0x2248.toChar()
    /**
     * °
     */
    const val DEGREE: Char = 0x00B0.toChar()
    /**
     * ∙
     */
    const val BULLET_SMALL: Char = 0x2219.toChar()
    /**
     * ·
     */
    const val INTERPUNCT: Char = 0x00B7.toChar()
    /**
     * √
     */
    const val SQUARE_ROOT: Char = 0x221A.toChar()
    /**
     * ⁿ
     */
    const val SUPERSCRIPT_N: Char = 0x207F.toChar()
    /**
     * ²
     */
    const val SUPERSCRIPT_SQUARE: Char = 0x00B2.toChar()
    /**
     * ■
     */
    const val SOLID_SQUARE: Char = 0x25A0.toChar()

}

