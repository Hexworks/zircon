package org.codetome.zircon.terminal.virtual

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.behavior.Clearable
import org.codetome.zircon.terminal.Cell
import org.codetome.zircon.terminal.Size

/**
 * This class is used to store lines of text inside of a terminal emulator.
 */
internal class TextCharacterBuffer: Clearable {

    private var lines = mutableListOf<MutableList<TextCharacter>>()

    init {
        newLine()
    }

    @Synchronized
    fun resize(size: Size) {
        lines = lines.filterIndexed { row, _ -> row < size.rows }
                .map { line ->
                    line.filterIndexed { col, _ ->
                        col < size.columns
                    }.toMutableList()
                }.toMutableList()
    }

    @Synchronized
    fun newLine() {
        lines.add(mutableListOf())
    }

    @Synchronized
    override fun clear() {
        lines.clear()
        newLine()
    }

    fun forEachCell(fn: (Cell) -> Unit) {
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, tc ->
                fn(Cell(Position(col, row), tc))
            }
        }
    }

    @Synchronized
    fun getLineCount() = lines.size

    @Synchronized
    fun getCharacter(position: Position): TextCharacter {
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
    fun setCharacter(position: Position, textCharacter: TextCharacter) {
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

    private fun checkRowAndColumn(position: Position) {
        val (columnIndex, lineNumber) = position
        require(lineNumber >= 0 && columnIndex >= 0) {
            throw IllegalArgumentException("Illegal argument to TextCharacterBuffer.setCharacter(..), lineNumber = " +
                    lineNumber + ", columnIndex = " + columnIndex)
        }
    }
}
