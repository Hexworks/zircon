package org.hexworks.zircon.api.graphics

enum class BoxType(
        val topLeft: Char,
        val topRight: Char,
        val bottomLeft: Char,
        val bottomRight: Char,
        val vertical: Char,
        val horizontal: Char,
        val connectorCross: Char,
        val connectorLeft: Char,
        val connectorRight: Char,
        val connectorDown: Char,
        val connectorUp: Char) {

    BASIC('+', '+', '+', '+', '|', '-', '+', '+', '+', '+', '+'),
    SINGLE('┌', '┐', '└', '┘', '│', '─', '┼', '┤', '├', '┬', '┴'),
    DOUBLE('╔', '╗', '╚', '╝', '║', '═', '╬', '╣', '╠', '╦', '╩'),
    TOP_BOTTOM_DOUBLE('╒', '╕', '╘', '╛', '│', '═', '╪', '╡', '╞', '╤', '╧'),
    LEFT_RIGHT_DOUBLE('╓', '╖', '╙', '╜', '║', '─', '╫', '╢', '╟', '╥', '╨');

}
