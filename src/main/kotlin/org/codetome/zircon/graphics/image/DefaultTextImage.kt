package org.codetome.zircon.graphics.image

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.behavior.DrawSurface
import org.codetome.zircon.behavior.Drawable
import org.codetome.zircon.behavior.impl.DefaultBoundable
import org.codetome.zircon.graphics.style.DefaultStyleSet
import org.codetome.zircon.graphics.style.StyleSet
import java.util.*

/**
 * Simple implementation of [TextImage] that keeps the content as a two-dimensional [TextCharacter] array.
 * Copy operations between two [DefaultTextImage] classes are semi-optimized by using [System.arraycopy]
 * instead of iterating over each character and copying them over one by one.
 */
class DefaultTextImage private constructor(toCopy: Array<Array<TextCharacter>>,
                                           filler: TextCharacter,
                                           boundable: Boundable,
                                           styleSet: StyleSet = DefaultStyleSet())
    : TextImage, Boundable by boundable, StyleSet by styleSet {

    constructor(size: Size,
                toCopy: Array<Array<TextCharacter>>,
                filler: TextCharacter) : this(
            toCopy = toCopy,
            filler = filler,
            boundable = DefaultBoundable(
                    size = size))


    private val buffer = (0 until getBoundableSize().rows).map {
        (0 until getBoundableSize().columns).map { filler }.toTypedArray()
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
        return (0 until getBoundableSize().rows).map { row ->
            (0 until getBoundableSize().columns).map { col ->
                buffer[row][col].getCharacter()
            }.joinToString("").plus("\n")
        }.joinToString("")
    }

    override fun draw(drawable: Drawable, offset: Position) {
        drawable.drawOnto(this, offset)
    }

    override fun putText(text: String, position: Position) {
        text.forEachIndexed { col, char ->
            setCharacterAt(position.withRelativeColumn(col), TextCharacterBuilder
                    .newBuilder()
                    .styleSet(toStyleSet())
                    .character(char)
                    .build())
        }
    }

    @Synchronized
    override fun applyStyle(styleSet: StyleSet) {
        setStyleFrom(styleSet)
        getBoundableSize().fetchPositions().forEach { pos ->
            setCharacterAt(pos, getCharacterAt(pos).get().withStyle(styleSet))
        }
    }

    override fun resize(newSize: Size, filler: TextCharacter): DefaultTextImage {
        if (newSize.rows == buffer.size && (buffer.isEmpty() || newSize.columns == buffer[0].size)) {
            return this
        }
        return DefaultTextImage(newSize, buffer, filler)
    }

    override fun setCharacterAt(position: Position, character: Char)
            = setCharacterAt(position, TextCharacterBuilder.newBuilder()
            .character(character)
            .styleSet(toStyleSet())
            .build())

    override fun setCharacterAt(position: Position, character: TextCharacter) = position.let { (column, row) ->
        if (positionIsOutOfBounds(column, row)) {
            false
        } else {
            buffer[row][column] = character
            true
        }
    }

    override fun getCharacterAt(position: Position) = position.let { (column, row) ->
        if (positionIsOutOfBounds(column, row)) {
            Optional.empty()
        } else {
            Optional.of(buffer[row][column])
        }
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        drawOnto(surface, 0, buffer.size, 0, buffer[0].size, offset.row, offset.column)
    }

    override fun drawOnto(
            destination: DrawSurface,
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
                            Position.of(x - startColumnIdx + destColumnOffset,
                                    y - startRowIdx + destRowOffset),
                            buffer[y][x])
                }
            }
        }
    }

    private fun positionIsOutOfBounds(column: Int, row: Int)
            = column < 0 || row < 0 || row >= buffer.size || column >= buffer[0].size

}