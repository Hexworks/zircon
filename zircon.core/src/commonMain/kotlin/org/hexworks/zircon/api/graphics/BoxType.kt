package org.hexworks.zircon.api.graphics


/**
 * Contains all the box drawing characters and their corresponding positions.
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

    BASIC('+', '+', '+', '+', '|', '-', '+', '+', '+', '+', '+'),
    SINGLE('┌', '┐', '└', '┘', '│', '─', '┼', '┤', '├', '┬', '┴'),
    DOUBLE('╔', '╗', '╚', '╝', '║', '═', '╬', '╣', '╠', '╦', '╩'),
    TOP_BOTTOM_DOUBLE('╒', '╕', '╘', '╛', '│', '═', '╪', '╡', '╞', '╤', '╧'),
    LEFT_RIGHT_DOUBLE('╓', '╖', '╙', '╜', '║', '─', '╫', '╢', '╟', '╥', '╨');

}
