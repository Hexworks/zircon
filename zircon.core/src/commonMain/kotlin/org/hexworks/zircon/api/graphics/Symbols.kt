@file:Suppress("unused")

package org.hexworks.zircon.api.graphics

/**
 * Some text graphics, taken from http://en.wikipedia.org/wiki/Codepage_437 but converted
 * to its UTF-8 counterpart.
 * This class is here to help out with building text GUIs when you don't have a
 * handy Unicode chart available.
 * This object was taken from Lanterna (https://github.com/mabe02/lanterna) as-is.
 */
public object Symbols {

    /**
     * ☺
     */
    public const val FACE_WHITE: Char = 0x263A.toChar()

    /**
     * ☻
     */
    public const val FACE_BLACK: Char = 0x263B.toChar()

    /**
     * ♥
     */
    public const val HEART: Char = 0x2665.toChar()

    /**
     * ♦
     */
    public const val DIAMOND: Char = 0x2666.toChar()

    /**
     * ♣
     */
    public const val CLUB: Char = 0x2663.toChar()

    /**
     * ♠
     */
    public const val SPADES: Char = 0x2660.toChar()

    /**
     * •
     */
    public const val BULLET: Char = 0x2022.toChar()

    /**
     * ◘
     */
    public const val INVERSE_BULLET: Char = 0x25D8.toChar()

    /**
     * ○
     */
    public const val WHITE_CIRCLE: Char = 0x25CB.toChar()

    /**
     * ◙
     */
    public const val INVERSE_WHITE_CIRCLE: Char = 0x25D9.toChar()

    /**
     * ♂
     */
    public const val MALE: Char = 0x2642.toChar()

    /**
     * ♀
     */
    public const val FEMALE: Char = 0x2640.toChar()

    /**
     * ♪
     */
    public const val NOTE: Char = 0x266A.toChar()

    /**
     * ♫
     */
    public const val DOUBLE_NOTE: Char = 0x266B.toChar()

    /**
     * ☼
     */
    public const val SOLAR_SYMBOL: Char = 0x263C.toChar()

    /**
     * ►
     */
    public const val TRIANGLE_RIGHT_POINTING_BLACK: Char = 0x25BA.toChar()

    /**
     * ◄
     */
    public const val TRIANGLE_LEFT_POINTING_BLACK: Char = 0x25C4.toChar()

    /**
     * ↕
     */
    public const val ARROW_UP_DOWN: Char = 0x2195.toChar()

    /**
     * ‼
     */
    public const val DOUBLE_EXCLAMATION_MARK: Char = 0x203C.toChar()

    /**
     * ¶
     */
    public const val PILCROW: Char = 0x00B6.toChar()

    /**
     * §
     */
    public const val SECTION_SIGN: Char = 0x00A7.toChar()

    /**
     * ▬
     */
    public const val BLACK_RECTANGLE: Char = 0x25AC.toChar()

    /**
     * ↨
     */
    public const val ARROW_UP_DOWN_WITH_BASE: Char = 0x21A8.toChar()

    /**
     * ↑
     */
    public const val ARROW_UP: Char = 0x2191.toChar()

    /**
     * ↓
     */
    public const val ARROW_DOWN: Char = 0x2193.toChar()

    /**
     * →
     */
    public const val ARROW_RIGHT: Char = 0x2192.toChar()

    /**
     * ←
     */
    public const val ARROW_LEFT: Char = 0x2190.toChar()

    /**
     * ∟
     */
    public const val RIGHT_ANGLE: Char = 0x221F.toChar()

    /**
     * ↔
     */
    public const val ARROW_LEFT_RIGHT: Char = 0x2194.toChar()

    /**
     * ▲
     */
    public const val TRIANGLE_UP_POINTING_BLACK: Char = 0x25B2.toChar()

    /**
     * ▼
     */
    public const val TRIANGLE_DOWN_POINTING_BLACK: Char = 0x25BC.toChar()

    /**
     * ^
     */
    public const val CIRCUMFLEX: Char = 0x005E.toChar()

    /**
     * ⌂
     */
    public const val HOUSE: Char = 0x2302.toChar()

    /**
     * ¢
     */
    public const val CENT: Char = 0x00A2.toChar()

    /**
     * £
     */
    public const val POUND: Char = 0x00A3.toChar()

    /**
     * ¥
     */
    public const val YEN: Char = 0x00A5.toChar()

    /**
     * ₧
     */
    public const val PESETA: Char = 0x20A7.toChar()

    /**
     * ƒ
     */
    public const val F_WITH_HOOK: Char = 0x0192.toChar()

    /**
     * ª
     */
    public const val ORDINAL_INDICATOR_FEMININE: Char = 0x00AA.toChar()

    /**
     * º
     */
    public const val ORDINAL_INDICATOR_MASCULINE: Char = 0x00BA.toChar()

    /**
     * ¿
     */
    public const val INVERTED_QUESTION_MARK: Char = 0x00BF.toChar()

    /**
     * ⌐
     */
    public const val NEGATION_LEFT: Char = 0x2310.toChar()

    /**
     * ¬
     */
    public const val NEGATION_RIGHT: Char = 0x00AC.toChar()

    /**
     * ½
     */
    public const val HALF: Char = 0x00BD.toChar()

    /**
     * ¼
     */
    public const val QUARTER: Char = 0x00BC.toChar()

    /**
     * ¡
     */
    public const val INVERTED_EXCLAMATION_MARK: Char = 0x00A1.toChar()

    /**
     * «
     */
    public const val GUILLEMET_LEFT: Char = 0x00AB.toChar()

    /**
     * »
     */
    public const val GUILLEMET_RIGHT: Char = 0x00BB.toChar()

    /**
     * ░
     */
    public const val BLOCK_SPARSE: Char = 0x2591.toChar()

    /**
     * ▒
     */
    public const val BLOCK_MIDDLE: Char = 0x2592.toChar()

    /**
     * ▓
     */
    public const val BLOCK_DENSE: Char = 0x2593.toChar()

    /**
     * │
     */
    public const val SINGLE_LINE_VERTICAL: Char = 0x2502.toChar()

    /**
     * ┤
     */
    public const val SINGLE_LINE_T_LEFT: Char = 0x2524.toChar()

    /**
     * ╡
     */
    public const val SINGLE_LINE_T_DOUBLE_LEFT: Char = 0x2561.toChar()

    /**
     * ╢
     */
    public const val DOUBLE_LINE_T_SINGLE_LEFT: Char = 0x2562.toChar()

    /**
     * ╖
     */
    public const val DOUBLE_LINE_SINGLE_LEFT: Char = 0x2556.toChar()

    /**
     * ╕
     */
    public const val SINGLE_LINE_DOUBLE_LEFT: Char = 0x2555.toChar()

    /**
     * ╣
     */
    public const val DOUBLE_LINE_T_LEFT: Char = 0x2563.toChar()

    /**
     * ║
     */
    public const val DOUBLE_LINE_VERTICAL: Char = 0x2551.toChar()

    /**
     * ╗
     */
    public const val DOUBLE_LINE_TOP_RIGHT_CORNER: Char = 0x2557.toChar()

    /**
     * ╝
     */
    public const val DOUBLE_LINE_BOTTOM_RIGHT_CORNER: Char = 0x255D.toChar()

    /**
     * ╜
     */
    public const val DOUBLE_LINE_BOTTOM_RIGHT_CORNER_SINGLE: Char = 0x255C.toChar()

    /**
     * ╛
     */
    public const val SINGLE_LINE_BOTTOM_RIGHT_CORNER_DOUBLE: Char = 0x255B.toChar()

    /**
     * ┐
     */
    public const val SINGLE_LINE_TOP_RIGHT_CORNER: Char = 0x2510.toChar()

    /**
     * └
     */
    public const val SINGLE_LINE_BOTTOM_LEFT_CORNER: Char = 0x2514.toChar()

    /**
     * ┴
     */
    public const val SINGLE_LINE_T_UP: Char = 0x2534.toChar()

    /**
     * ┬
     */
    public const val SINGLE_LINE_T_DOWN: Char = 0x252C.toChar()

    /**
     * ├
     */
    public const val SINGLE_LINE_T_RIGHT: Char = 0x251C.toChar()

    /**
     * ─
     */
    public const val SINGLE_LINE_HORIZONTAL: Char = 0x2500.toChar()

    /**
     * ┼
     */
    public const val SINGLE_LINE_CROSS: Char = 0x253C.toChar()

    /**
     * ╞
     */
    public const val SINGLE_LINE_T_DOUBLE_RIGHT: Char = 0x255E.toChar()

    /**
     * ╟
     */
    public const val DOUBLE_LINE_T_SINGLE_RIGHT: Char = 0x255F.toChar()

    /**
     * ╚
     */
    public const val DOUBLE_LINE_BOTTOM_LEFT_CORNER: Char = 0x255A.toChar()

    /**
     * ╔
     */
    public const val DOUBLE_LINE_TOP_LEFT_CORNER: Char = 0x2554.toChar()

    /**
     * ╩
     */
    public const val DOUBLE_LINE_T_UP: Char = 0x2569.toChar()

    /**
     * ╦
     */
    public const val DOUBLE_LINE_T_DOWN: Char = 0x2566.toChar()

    /**
     * ╠
     */
    public const val DOUBLE_LINE_T_RIGHT: Char = 0x2560.toChar()

    /**
     * ═
     */
    public const val DOUBLE_LINE_HORIZONTAL: Char = 0x2550.toChar()

    /**
     * ╬
     */
    public const val DOUBLE_LINE_CROSS: Char = 0x256C.toChar()

    /**
     * ╧
     */
    public const val DOUBLE_LINE_T_SINGLE_UP: Char = 0x2567.toChar()

    /**
     * ╨
     */
    public const val SINGLE_LINE_T_DOUBLE_UP: Char = 0x2568.toChar()

    /**
     * ╤
     */
    public const val DOUBLE_LINE_T_SINGLE_DOWN: Char = 0x2564.toChar()

    /**
     * ╥
     */
    public const val SINGLE_LINE_T_DOUBLE_DOWN: Char = 0x2565.toChar()

    /**
     * ╙
     */
    public const val DOUBLE_LINE_BOTTOM_LEFT_CORNER_SINGLE: Char = 0x2559.toChar()

    /**
     * ╘
     */
    public const val SINGLE_LINE_BOTTOM_LEFT_CORNER_DOUBLE: Char = 0x2558.toChar()

    /**
     * ╒
     */
    public const val SINGLE_LINE_TOP_LEFT_CORNER_DOUBLE: Char = 0x2552.toChar()

    /**
     * ╓
     */
    public const val DOUBLE_LINE_TOP_LEFT_CORNER_SINGLE: Char = 0x2553.toChar()

    /**
     * ╫
     */
    public const val DOUBLE_LINE_VERTICAL_SINGLE_LINE_CROSS: Char = 0x256B.toChar()

    /**
     * ╪
     */
    public const val DOUBLE_LINE_HORIZONTAL_SINGLE_LINE_CROSS: Char = 0x256A.toChar()

    /**
     * ┘
     */
    public const val SINGLE_LINE_BOTTOM_RIGHT_CORNER: Char = 0x2518.toChar()

    /**
     * ┌
     */
    public const val SINGLE_LINE_TOP_LEFT_CORNER: Char = 0x250C.toChar()

    /**
     * █
     */
    public const val BLOCK_SOLID: Char = 0x2588.toChar()

    /**
     * ▄
     */
    public const val LOWER_HALF_BLOCK: Char = 0x2584.toChar()

    /**
     * ▌
     */
    public const val LEFT_HALF_BLOCK: Char = 0x258C.toChar()

    /**
     * ▐
     */
    public const val RIGHT_HALF_BLOCK: Char = 0x2590.toChar()

    /**
     * ▀
     */
    public const val UPPER_HALF_BLOCK: Char = 0x2580.toChar()

    /**
     * α
     */
    public const val ALPHA: Char = 0x03B1.toChar()

    /**
     * ß
     */
    public const val BETA: Char = 0x00DF.toChar()

    /**
     * Γ
     */
    public const val GAMMA: Char = 0x0393.toChar()

    /**
     * π
     */
    public const val PI: Char = 0x03C0.toChar()

    /**
     * Σ
     */
    public const val SIGMA_UPPERCASE: Char = 0x03A3.toChar()

    /**
     * σ
     */
    public const val SIGMA_LOWERCASE: Char = 0x03C3.toChar()

    /**
     * µ
     */
    public const val MU: Char = 0x00B5.toChar()

    /**
     * τ
     */
    public const val TAU: Char = 0x03C4.toChar()

    /**
     * Φ
     */
    public const val PHI_UPPERCASE: Char = 0x03A6.toChar()

    /**
     * Θ
     */
    public const val THETA: Char = 0x0398.toChar()

    /**
     * Ω
     */
    public const val OMEGA: Char = 0x03A9.toChar()

    /**
     * δ
     */
    public const val DELTA: Char = 0x03B4.toChar()

    /**
     * ∞
     */
    public const val INFINITY: Char = 0x221E.toChar()

    /**
     * φ
     */
    public const val PHI_LOWERCASE: Char = 0x03C6.toChar()

    /**
     * ε
     */
    public const val EPSILON: Char = 0x03B5.toChar()

    /**
     * ∩
     */
    public const val INTERSECTION: Char = 0x2229.toChar()

    /**
     * ≡
     */
    public const val TRIPLE_BAR: Char = 0x2261.toChar()

    /**
     * ±
     */
    public const val PLUS_MINUS: Char = 0x00B1.toChar()

    /**
     * ≥
     */
    public const val GREATER_THAN_OR_EQUAL: Char = 0x2265.toChar()

    /**
     * ≤
     */
    public const val LESS_THAN_OR_EQUAL: Char = 0x2264.toChar()

    /**
     * ⌠
     */
    public const val INTEGRAL_UPPER: Char = 0x2320.toChar()

    /**
     * ⌡
     */
    public const val INTEGRAL_LOWER: Char = 0x2321.toChar()

    /**
     * ÷
     */
    public const val OBELUS: Char = 0x00F7.toChar()

    /**
     * ≈
     */
    public const val APPROXIMATION: Char = 0x2248.toChar()

    /**
     * °
     */
    public const val DEGREE: Char = 0x00B0.toChar()

    /**
     * ∙
     */
    public const val BULLET_SMALL: Char = 0x2219.toChar()

    /**
     * ·
     */
    public const val INTERPUNCT: Char = 0x00B7.toChar()

    /**
     * √
     */
    public const val SQUARE_ROOT: Char = 0x221A.toChar()

    /**
     * ⁿ
     */
    public const val SUPERSCRIPT_N: Char = 0x207F.toChar()

    /**
     * ²
     */
    public const val SUPERSCRIPT_SQUARE: Char = 0x00B2.toChar()

    /**
     * ■
     */
    public const val SOLID_SQUARE: Char = 0x25A0.toChar()

}

