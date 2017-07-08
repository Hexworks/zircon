@file:Suppress("unused")

package org.codetome.zircon

/**
 * Some text graphics, taken from http://en.wikipedia.org/wiki/Codepage_437 but converted to its UTF-8 counterpart.
 * This class it mostly here to help out with building text GUIs when you don't have a handy Unicode chart available.
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
     * ♣
     */
    const val CLUB: Char = 0x2663.toChar()
    /**
     * ♦
     */
    const val DIAMOND: Char = 0x2666.toChar()
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
    const val INVERSE_BULLET: Char = 0x25d8.toChar()
    /**
     * ○
     */
    const val WHITE_CIRCLE: Char = 0x25cb.toChar()
    /**
     * ◙
     */
    const val INVERSE_WHITE_CIRCLE: Char = 0x25d9.toChar()

    /**
     * ■
     */
    const val SOLID_SQUARE: Char = 0x25A0.toChar()
    /**
     * ▪
     */
    const val SOLID_SQUARE_SMALL: Char = 0x25AA.toChar()
    /**
     * □
     */
    const val OUTLINED_SQUARE: Char = 0x25A1.toChar()
    /**
     * ▫
     */
    const val OUTLINED_SQUARE_SMALL: Char = 0x25AB.toChar()

    /**
     * ♀
     */
    const val FEMALE: Char = 0x2640.toChar()
    /**
     * ♂
     */
    const val MALE: Char = 0x2642.toChar()

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
     * █
     */
    const val BLOCK_SOLID: Char = 0x2588.toChar()
    /**
     * ▓
     */
    const val BLOCK_DENSE: Char = 0x2593.toChar()
    /**
     * ▒
     */
    const val BLOCK_MIDDLE: Char = 0x2592.toChar()
    /**
     * ░
     */
    const val BLOCK_SPARSE: Char = 0x2591.toChar()

    /**
     * ►
     */
    const val TRIANGLE_RIGHT_POINTING_BLACK: Char = 0x25BA.toChar()
    /**
     * ◄
     */
    const val TRIANGLE_LEFT_POINTING_BLACK: Char = 0x25C4.toChar()
    /**
     * ▲
     */
    const val TRIANGLE_UP_POINTING_BLACK: Char = 0x25B2.toChar()
    /**
     * ▼
     */
    const val TRIANGLE_DOWN_POINTING_BLACK: Char = 0x25BC.toChar()

    /**
     * ⏴
     */
    const val TRIANGLE_RIGHT_POINTING_MEDIUM_BLACK: Char = 0x23F4.toChar()
    /**
     * ⏵
     */
    const val TRIANGLE_LEFT_POINTING_MEDIUM_BLACK: Char = 0x23F5.toChar()
    /**
     * ⏶
     */
    const val TRIANGLE_UP_POINTING_MEDIUM_BLACK: Char = 0x23F6.toChar()
    /**
     * ⏷
     */
    const val TRIANGLE_DOWN_POINTING_MEDIUM_BLACK: Char = 0x23F7.toChar()


    /**
     * ─
     */
    const val SINGLE_LINE_HORIZONTAL: Char = 0x2500.toChar()
    /**
     * ━
     */
    const val BOLD_SINGLE_LINE_HORIZONTAL: Char = 0x2501.toChar()
    /**
     * ╾
     */
    const val BOLD_TO_NORMAL_SINGLE_LINE_HORIZONTAL: Char = 0x257E.toChar()
    /**
     * ╼
     */
    const val BOLD_FROM_NORMAL_SINGLE_LINE_HORIZONTAL: Char = 0x257C.toChar()
    /**
     * ═
     */
    const val DOUBLE_LINE_HORIZONTAL: Char = 0x2550.toChar()
    /**
     * │
     */
    const val SINGLE_LINE_VERTICAL: Char = 0x2502.toChar()
    /**
     * ┃
     */
    const val BOLD_SINGLE_LINE_VERTICAL: Char = 0x2503.toChar()
    /**
     * ╿
     */
    const val BOLD_TO_NORMAL_SINGLE_LINE_VERTICAL: Char = 0x257F.toChar()
    /**
     * ╽
     */
    const val BOLD_FROM_NORMAL_SINGLE_LINE_VERTICAL: Char = 0x257D.toChar()
    /**
     * ║
     */
    const val DOUBLE_LINE_VERTICAL: Char = 0x2551.toChar()

    /**
     * ┌
     */
    const val SINGLE_LINE_TOP_LEFT_CORNER: Char = 0x250C.toChar()
    /**
     * ╔
     */
    const val DOUBLE_LINE_TOP_LEFT_CORNER: Char = 0x2554.toChar()
    /**
     * ┐
     */
    const val SINGLE_LINE_TOP_RIGHT_CORNER: Char = 0x2510.toChar()
    /**
     * ╗
     */
    const val DOUBLE_LINE_TOP_RIGHT_CORNER: Char = 0x2557.toChar()

    /**
     * └
     */
    const val SINGLE_LINE_BOTTOM_LEFT_CORNER: Char = 0x2514.toChar()
    /**
     * ╚
     */
    const val DOUBLE_LINE_BOTTOM_LEFT_CORNER: Char = 0x255A.toChar()
    /**
     * ┘
     */
    const val SINGLE_LINE_BOTTOM_RIGHT_CORNER: Char = 0x2518.toChar()
    /**
     * ╝
     */
    const val DOUBLE_LINE_BOTTOM_RIGHT_CORNER: Char = 0x255D.toChar()

    /**
     * ┼
     */
    const val SINGLE_LINE_CROSS: Char = 0x253C.toChar()
    /**
     * ╬
     */
    const val DOUBLE_LINE_CROSS: Char = 0x256C.toChar()
    /**
     * ╪
     */
    const val DOUBLE_LINE_HORIZONTAL_SINGLE_LINE_CROSS: Char = 0x256A.toChar()
    /**
     * ╫
     */
    const val DOUBLE_LINE_VERTICAL_SINGLE_LINE_CROSS: Char = 0x256B.toChar()

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
    const val SINGLE_LINE_T_RIGHT: Char = 0x251c.toChar()
    /**
     * ┤
     */
    const val SINGLE_LINE_T_LEFT: Char = 0x2524.toChar()

    /**
     * ╨
     */
    const val SINGLE_LINE_T_DOUBLE_UP: Char = 0x2568.toChar()
    /**
     * ╥
     */
    const val SINGLE_LINE_T_DOUBLE_DOWN: Char = 0x2565.toChar()
    /**
     * ╞
     */
    const val SINGLE_LINE_T_DOUBLE_RIGHT: Char = 0x255E.toChar()
    /**
     * ╡
     */
    const val SINGLE_LINE_T_DOUBLE_LEFT: Char = 0x2561.toChar()

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
     * ╣
     */
    const val DOUBLE_LINE_T_LEFT: Char = 0x2563.toChar()

    /**
     * ╧
     */
    const val DOUBLE_LINE_T_SINGLE_UP: Char = 0x2567.toChar()
    /**
     * ╤
     */
    const val DOUBLE_LINE_T_SINGLE_DOWN: Char = 0x2564.toChar()
    /**
     * ╟
     */
    const val DOUBLE_LINE_T_SINGLE_RIGHT: Char = 0x255F.toChar()
    /**
     * ╢
     */
    const val DOUBLE_LINE_T_SINGLE_LEFT: Char = 0x2562.toChar()
}

