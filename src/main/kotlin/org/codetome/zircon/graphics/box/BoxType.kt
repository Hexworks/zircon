package org.codetome.zircon.graphics.box

import org.codetome.zircon.TextCharacter
import java.util.*

enum class BoxType(boxCharacters: CharArray) {
    BASIC(charArrayOf('+', '+', '+', '+', '|', '-', '+', '+', '+', '+', '+')),
    SINGLE(charArrayOf('┌', '┐', '└', '┘', '│', '─', '┼', '┤', '├', '┬', '┴')),
    DOUBLE(charArrayOf('╔', '╗', '╚', '╝', '║', '═', '╬', '╣', '╠', '╦', '╩')),
    TOP_BOTTOM_DOUBLE(charArrayOf('╒', '╕', '╘', '╛', '│', '═', '╪', '╡', '╞', '╤', '╧')),
    LEFT_RIGHT_DOUBLE(charArrayOf('╓', '╖', '╙', '╜', '║', '─', '╫', '╢', '╟', '╥', '╨'));

    val topLeft: Char = boxCharacters[0]
    val topRight: Char = boxCharacters[1]
    val bottomLeft: Char = boxCharacters[2]
    val bottomRight: Char = boxCharacters[3]
    val vertical: Char = boxCharacters[4]
    val horizontal: Char = boxCharacters[5]
    val connectorCross: Char = boxCharacters[6]
    val connectorLeft: Char = boxCharacters[7]
    val connectorRight: Char = boxCharacters[8]
    val connectorDown: Char = boxCharacters[9]
    val connectorUp: Char = boxCharacters[10]

    private val validTopCharacters = charArrayOf(vertical, connectorCross, connectorLeft, connectorRight, connectorDown, topLeft, topRight)
    private val validBottomCharacters = charArrayOf(vertical, connectorCross, connectorLeft, connectorRight, connectorUp, bottomLeft, bottomRight)
    private val validLeftCharacters = charArrayOf(horizontal, connectorCross, connectorRight, connectorDown, connectorUp, topLeft, bottomLeft)
    private val validRightCharacters = charArrayOf(horizontal, connectorCross, connectorLeft, connectorDown, connectorUp, topRight, bottomRight)

    val neighborPatterns = mapOf(
            Pair(booleanArrayOf(true, true, true, true).joinToString(""), connectorCross),
            Pair(booleanArrayOf(true, true, true, false).joinToString(""), connectorUp),
            Pair(booleanArrayOf(true, false, true, true).joinToString(""), connectorDown),
            Pair(booleanArrayOf(false, true, true, true).joinToString(""), connectorLeft),
            Pair(booleanArrayOf(true, true, false, true).joinToString(""), connectorRight),
            Pair(booleanArrayOf(true, false, true, false).joinToString(""), horizontal),
            Pair(booleanArrayOf(false, true, false, true).joinToString(""), vertical),
            Pair(booleanArrayOf(false, false, true, true).joinToString(""), topRight),
            Pair(booleanArrayOf(false, true, true, false).joinToString(""), bottomRight),
            Pair(booleanArrayOf(true, false, false, true).joinToString(""), topLeft),
            Pair(booleanArrayOf(true, true, false, false).joinToString(""), bottomLeft))

    fun getCharacterByNeighborPattern(pattern: BooleanArray): Optional<Char> {
        return Optional.ofNullable(neighborPatterns[pattern.joinToString("")])
    }

    fun isValidTopCharacter(textCharacter: TextCharacter): Boolean {
        return isValidCharacter(textCharacter.getCharacter(), validTopCharacters)
    }

    fun isValidBottomCharacter(textCharacter: TextCharacter): Boolean {
        return isValidCharacter(textCharacter.getCharacter(), validBottomCharacters)
    }

    fun isValidLeftCharacter(textCharacter: TextCharacter): Boolean {
        return isValidCharacter(textCharacter.getCharacter(), validLeftCharacters)
    }

    fun isValidRightCharacter(textCharacter: TextCharacter): Boolean {
        return isValidCharacter(textCharacter.getCharacter(), validRightCharacters)
    }

    private fun isValidCharacter(character: Char, validCharacters: CharArray): Boolean {
        return validCharacters.contains(character)
    }

}
