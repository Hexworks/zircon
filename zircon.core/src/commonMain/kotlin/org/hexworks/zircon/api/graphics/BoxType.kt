package org.hexworks.zircon.api.graphics


/**
 * Contains all the box drawing characters and their corresponding positions.
 *
 * All glyphs come from the Unicode Box Drawing block (U+2500–U+257F), which every
 * monospace font that targets terminals ships, so every type renders with any
 * TrueType or Nerd Font tileset.
 */
public enum class BoxType(
    public val topLeft: Char,
    public val topRight: Char,
    public val bottomLeft: Char,
    public val bottomRight: Char,
    public val vertical: Char,
    public val horizontal: Char,
    public val connectorCross: Char,
    public val connectorLeft: Char,
    public val connectorRight: Char,
    public val connectorDown: Char,
    public val connectorUp: Char
) {

    /** ASCII only: `+`, `-` and `|`. */
    BASIC('+', '+', '+', '+', '|', '-', '+', '+', '+', '+', '+'),

    /** Light lines with square corners. */
    SINGLE('┌', '┐', '└', '┘', '│', '─', '┼', '┤', '├', '┬', '┴'),

    /** Double lines on every side. */
    DOUBLE('╔', '╗', '╚', '╝', '║', '═', '╬', '╣', '╠', '╦', '╩'),

    /** Double top and bottom, light left and right. */
    TOP_BOTTOM_DOUBLE('╒', '╕', '╘', '╛', '│', '═', '╪', '╡', '╞', '╤', '╧'),

    /** Light top and bottom, double left and right. */
    LEFT_RIGHT_DOUBLE('╓', '╖', '╙', '╜', '║', '─', '╫', '╢', '╟', '╥', '╨'),

    /** Light lines with rounded corners. */
    ROUNDED('╭', '╮', '╰', '╯', '│', '─', '┼', '┤', '├', '┬', '┴'),

    /** Heavy lines with square corners. */
    HEAVY('┏', '┓', '┗', '┛', '┃', '━', '╋', '┫', '┣', '┳', '┻'),

    /** Light triple-dashed lines with square corners. */
    DASHED('┌', '┐', '└', '┘', '┆', '┄', '┼', '┤', '├', '┬', '┴'),

    /** Light quadruple-dashed lines with square corners. */
    DOTTED('┌', '┐', '└', '┘', '┊', '┈', '┼', '┤', '├', '┬', '┴'),

    /** Light triple-dashed lines with rounded corners. */
    ROUNDED_DASHED('╭', '╮', '╰', '╯', '┆', '┄', '┼', '┤', '├', '┬', '┴'),

    /** Heavy triple-dashed lines with square corners. */
    HEAVY_DASHED('┏', '┓', '┗', '┛', '┇', '┅', '╋', '┫', '┣', '┳', '┻');

}
