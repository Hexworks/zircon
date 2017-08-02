package org.codetome.zircon.terminal.config

/**
 * Different cursor styles supported by [org.codetome.zircon.terminal.Terminal].
 */
enum class CursorStyle {
    /**
     * The cursor is drawn by inverting the front- and background colors of the cursor position.
     */
    REVERSED,
    /**
     * The cursor is drawn by using the cursor color as the background color for the character at the cursor position.
     */
    FIXED_BACKGROUND,
    /**
     * The cursor is rendered as a thick horizontal line at the bottom of the character.
     */
    UNDER_BAR,
    /**
     * The cursor is rendered as a left-side aligned vertical line.
     */
    VERTICAL_BAR
}