package org.codetome.zircon.terminal.virtual

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import java.util.*

/**
 * This class is used to store lines of text inside of a terminal emulator.
 */
internal class TextCharacterBuffer {

    private val lines = LinkedList<MutableList<TextCharacter>>()

    init {
        newLine()
    }

    @Synchronized
    fun newLine() {
        lines.add(mutableListOf())
    }

    @Synchronized
    fun clear() {
        lines.clear()
        newLine()
    }

    fun getLinesFrom(rowNumber: Int): ListIterator<List<TextCharacter>> {
        return lines.listIterator(rowNumber)
    }

    @Synchronized
    fun getLineCount() = lines.size

    @Synchronized
    fun getCharacter(position: TerminalPosition): TextCharacter {
        checkRowAndColumn(position)
        val (col, row) = position
        if (row >= lines.size) {
            return TextCharacter.DEFAULT_CHARACTER
        }
        val line = lines[row]
        if (line.size <= col) {
            return TextCharacter.DEFAULT_CHARACTER
        }
        return line[col]
    }

    @Synchronized
    fun setCharacter(position: TerminalPosition, textCharacter: TextCharacter) {
        checkRowAndColumn(position)
        val (col, row) = position
        while (row >= lines.size) {
            newLine()
        }
        val line = lines[row]
        while (line.size <= col) {
            line.add(TextCharacter.DEFAULT_CHARACTER)
        }
        line[col] = textCharacter
    }

    private fun checkRowAndColumn(position: TerminalPosition) {
        val (columnIndex, lineNumber) = position
        require(lineNumber >= 0 && columnIndex >= 0) {
            throw IllegalArgumentException("Illegal argument to TextCharacterBuffer.setCharacter(..), lineNumber = " +
                    lineNumber + ", columnIndex = " + columnIndex)
        }
    }
}
