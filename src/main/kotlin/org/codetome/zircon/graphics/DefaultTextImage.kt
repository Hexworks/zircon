package org.codetome.zircon.graphics

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.behavior.impl.DefaultBoundable
import org.codetome.zircon.terminal.Size
import java.util.*

/**
 * Simple implementation of [TextImage] that keeps the content as a two-dimensional [TextCharacter] array.
 * Copy operations between two [DefaultTextImage] classes are semi-optimized by using [System.arraycopy]
 * instead of iterating over each character and copying them over one by one.
 */
class DefaultTextImage private constructor(toCopy: Array<Array<TextCharacter>>,
                                           filler: TextCharacter,
                                           boundable: Boundable) : TextImage, Boundable by boundable {

    constructor(size: Size,
                toCopy: Array<Array<TextCharacter>>,
                filler: TextCharacter) : this(
            toCopy = toCopy,
            filler = filler,
            boundable = DefaultBoundable(
                    size = size))


    private val buffer = (0..getBoundableSize().rows - 1).map {
        (0..getBoundableSize().columns - 1).map { filler }.toTypedArray()
    }.toTypedArray()

    init {
        getBoundableSize().fetchPositions().forEach { (col, row) ->
            if (row < toCopy.size && col < toCopy[row].size) {
                buffer[row][col] = toCopy[row][col]
            } else {
                buffer[row][col] = filler
            }
        }
    }

    override fun toString(): String {
        return (0..getBoundableSize().rows - 1).map { row ->
            (0..getBoundableSize().columns - 1).map { col ->
                buffer[row][col].getCharacter()
            }.joinToString("").plus("\n")
        }.joinToString("")
    }

    override fun setAll(character: TextCharacter) {
        buffer.forEach { line -> Arrays.fill(line, character) }
    }

    override fun resize(newSize: Size, filler: TextCharacter): DefaultTextImage {
        if (newSize.rows == buffer.size && (buffer.isEmpty() || newSize.columns == buffer[0].size)) {
            return this
        }
        return DefaultTextImage(newSize, buffer, filler)
    }

    override fun setCharacterAt(position: Position, character: TextCharacter) {
        val (column, row) = position
        if (column < 0 || row < 0 || row >= buffer.size || column >= buffer[0].size) {
            return
        }
        buffer[row][column] = character
    }

    override fun getCharacterAt(position: Position): TextCharacter {
        val (column, row) = position
        if (column < 0 || row < 0 || row >= buffer.size || column >= buffer[0].size) {
            throw IllegalArgumentException("column or row is out of bounds for position: $position! Size is: ${getBoundableSize()}")
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
        var startRowIdx = startRowIndex
        var rowsToCopy = rows
        var startColumnIdx = startColumnIndex
        var columnsToCopy = columns
        var destRowOffset = destinationRowOffset
        var destColumnOffset = destinationColumnOffset

        // If the source sprite position is negative, offset the whole sprite
        if (startColumnIdx < 0) {
            destColumnOffset += -startColumnIdx
            columnsToCopy += startColumnIdx
            startColumnIdx = 0
        }
        if (startRowIdx < 0) {
            destRowOffset += -startRowIdx
            rowsToCopy += startRowIdx
            startRowIdx = 0
        }

        // If the destination offset is negative, adjust the source start indexes
        if (destColumnOffset < 0) {
            startColumnIdx -= destColumnOffset
            columnsToCopy += destColumnOffset
            destColumnOffset = 0
        }
        if (destRowOffset < 0) {
            startRowIdx -= destRowOffset
            rowsToCopy += destRowOffset
            destRowOffset = 0
        }

        //Make sure we can't copy more than is available
        columnsToCopy = Math.min(buffer[0].size - startColumnIdx, columnsToCopy)
        rowsToCopy = Math.min(buffer.size - startRowIdx, rowsToCopy)

        //Adjust target lengths as well
        columnsToCopy = Math.min(destination.getBoundableSize().columns - destColumnOffset, columnsToCopy)
        rowsToCopy = Math.min(destination.getBoundableSize().rows - destRowOffset, rowsToCopy)

        if (columnsToCopy <= 0 || rowsToCopy <= 0) {
            return
        }

        val destinationSize = destination.getBoundableSize()
        if (destination is DefaultTextImage) {
            var targetRow = destRowOffset
            var y = startRowIdx
            while (y < startRowIdx + rowsToCopy && targetRow < destinationSize.rows) {
                System.arraycopy(buffer[y], startColumnIdx, destination.buffer[targetRow++], destColumnOffset, columnsToCopy)
                y++
            }
        } else {
            //Manually copy character by character
            for (y in startRowIdx..startRowIdx + rowsToCopy - 1) {
                for (x in startColumnIdx..startColumnIdx + columnsToCopy - 1) {
                    destination.setCharacterAt(
                            Position(x - startColumnIdx + destColumnOffset,
                                    y - startRowIdx + destRowOffset),
                            buffer[y][x])
                }
            }
        }
    }

    override fun newTextGraphics(): TextGraphics {
        return object : AbstractTextGraphics() {

            override fun getSize(): Size = this@DefaultTextImage.getBoundableSize()

            override fun setCharacter(position: Position, character: TextCharacter) {
                this@DefaultTextImage.setCharacterAt(position, character)
            }

            override fun getCharacter(position: Position): Optional<TextCharacter> {
                return Optional.of(this@DefaultTextImage.getCharacterAt(position))
            }
        }
    }
}