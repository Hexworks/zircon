package org.codetome.zircon.graphics.impl

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.terminal.TerminalSize
import java.util.*

/**
 * Simple implementation of [TextImage] that keeps the content as a two-dimensional [TextCharacter] array. Copy operations
 * between two [BasicTextImage] classes are semi-optimized by using [System.arraycopy] instead of iterating over each
 * character and copying them over one by one.
 */
class BasicTextImage @JvmOverloads constructor(private val size: TerminalSize,
                     toCopy: Array<Array<TextCharacter>> = arrayOf<Array<TextCharacter>>(),
                     initialContent: TextCharacter = TextCharacter.DEFAULT_CHARACTER) : TextImage {

    private val buffer = (0..size.rows - 1).map {
        (0..size.columns - 1).map { initialContent }.toTypedArray()
    }.toTypedArray()

    init {
        size.fetchPositions().forEach { (col, row) ->
            if (row < toCopy.size && col < toCopy[row].size) {
                buffer[row][col] = toCopy[row][col]
            } else {
                buffer[row][col] = initialContent
            }
        }
    }

    override fun getSize() = size

    override fun setAll(character: TextCharacter) {
        buffer.forEach { line -> Arrays.fill(line, character) }
    }

    override fun resize(newSize: TerminalSize, filler: TextCharacter): BasicTextImage {
        if (newSize.rows == buffer.size && (buffer.isEmpty() || newSize.columns == buffer[0].size)) {
            return this
        }
        return BasicTextImage(newSize, buffer, filler)
    }

    override fun setCharacterAt(position: TerminalPosition, character: TextCharacter) {
        val (column, row) = position
        if (column < 0 || row < 0 || row >= buffer.size || column >= buffer[0].size) {
            return
        }
        buffer[row][column] = character
    }

    override fun getCharacterAt(position: TerminalPosition): TextCharacter {
        val (column, row) = position
        if (column < 0 || row < 0 || row >= buffer.size || column >= buffer[0].size) {
            throw IllegalArgumentException("column or row is out of bounds")
        }

        return buffer[row][column]
    }

    override fun copyTo(destination: TextImage) {
        copyTo(destination, 0, buffer.size, 0, buffer[0].size, 0, 0)
    }

    override fun copyTo(
            destination: TextImage,
            startRowIndex: Int,
            rows: Int,
            startColumnIndex: Int,
            columns: Int,
            destinationRowOffset: Int,
            destinationColumnOffset: Int) {
        var startRowIndex = startRowIndex
        var rows = rows
        var startColumnIndex = startColumnIndex
        var columns = columns
        var destinationRowOffset = destinationRowOffset
        var destinationColumnOffset = destinationColumnOffset

        // If the source image position is negative, offset the whole image
        if (startColumnIndex < 0) {
            destinationColumnOffset += -startColumnIndex
            columns += startColumnIndex
            startColumnIndex = 0
        }
        if (startRowIndex < 0) {
            startRowIndex += -startRowIndex
            rows = startRowIndex
            startRowIndex = 0
        }

        // If the destination offset is negative, adjust the source start indexes
        if (destinationColumnOffset < 0) {
            startColumnIndex -= destinationColumnOffset
            columns += destinationColumnOffset
            destinationColumnOffset = 0
        }
        if (destinationRowOffset < 0) {
            startRowIndex -= destinationRowOffset
            rows += destinationRowOffset
            destinationRowOffset = 0
        }

        //Make sure we can't copy more than is available
        columns = Math.min(buffer[0].size - startColumnIndex, columns)
        rows = Math.min(buffer.size - startRowIndex, rows)

        //Adjust target lengths as well
        columns = Math.min(destination.getSize().columns - destinationColumnOffset, columns)
        rows = Math.min(destination.getSize().rows - destinationRowOffset, rows)

        if (columns <= 0 || rows <= 0) {
            return
        }

        val destinationSize = destination.getSize()
        if (destination is BasicTextImage) {
            var targetRow = destinationRowOffset
            var y = startRowIndex
            while (y < startRowIndex + rows && targetRow < destinationSize.rows) {
                System.arraycopy(buffer[y], startColumnIndex, destination.buffer[targetRow++], destinationColumnOffset, columns)
                y++
            }
        } else {
            //Manually copy character by character
            for (y in startRowIndex..startRowIndex + rows - 1) {
                for (x in startColumnIndex..startColumnIndex + columns - 1) {
                    destination.setCharacterAt(
                            TerminalPosition(x - startColumnIndex + destinationColumnOffset,
                                    y - startRowIndex + destinationRowOffset),
                            buffer[y][x])
                }
            }
        }
    }

    override fun newTextGraphics(): TextGraphics {
        return object : AbstractTextGraphics() {

            override fun getSize() = size

            override fun setCharacter(position: TerminalPosition, character: TextCharacter) {
                this@BasicTextImage.setCharacterAt(position, character)
            }

            override fun getCharacter(position: TerminalPosition): Optional<TextCharacter> {
                return Optional.of(this@BasicTextImage.getCharacterAt(position))
            }
        }
    }
}