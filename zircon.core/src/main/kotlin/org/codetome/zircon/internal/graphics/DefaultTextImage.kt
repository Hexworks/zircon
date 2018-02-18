package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.graphics.TextImageBase
import org.codetome.zircon.api.sam.TextCharacterTransformer
import java.util.*

/**
 * Simple implementation of [TextImage] that keeps the content as a two-dimensional [TextCharacter] array.
 * Copy operations between two [DefaultTextImage] classes are semi-optimized by using [System.arraycopy]
 * instead of iterating over each character and copying them over one by one.
 */
class DefaultTextImage(size: Size,
                       toCopy: Array<Array<TextCharacter>>,
                       filler: TextCharacter,
                       styleSet: StyleSet = StyleSetBuilder.DEFAULT_STYLE)
    : TextImageBase(size = size, styleSet = styleSet) {

    private val buffer: Array<Array<TextCharacter>> = copyArray(toCopy, getBoundableSize(), filler)

    override fun toString(): String {
        return (0 until getBoundableSize().yLength).map { y ->
            (0 until getBoundableSize().xLength).map { x ->
                buffer[y][x].getCharacter()
            }.joinToString("").plus("\n")
        }.joinToString("")
    }

    override fun resize(newSize: Size, filler: TextCharacter): TextImage {
        if (newSize.yLength == buffer.size && (buffer.isEmpty() || newSize.xLength == buffer[0].size)) {
            return this
        }
        return DefaultTextImage(newSize, buffer, filler)
    }

    @Synchronized
    override fun toSubImage(offset: Position, size: Size): TextImage {
        require(offset.toSize() + size <= getBoundableSize()) {
            "The bounds supplied are overlapping with this image! this image size: '${getBoundableSize()}'," +
                    " offset: '$offset', requested size: '$size'"
        }
        return TextImageBuilder.newBuilder()
                .size(size)
                .toCopy(copyArray(buffer, size, TextCharacterBuilder.EMPTY, offset))
                .build()
    }

    override fun fetchCells(): Iterable<Cell> {
        return fetchCellsBy(Position.DEFAULT_POSITION, getBoundableSize())
    }

    @Synchronized
    override fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell> {
        require(offset.toSize() + size <= getBoundableSize()) {
            "The bounds supplied are overlapping with this image! this image size: '${getBoundableSize()}'," +
                    " offset: '$offset', requested size: '$size'"
        }
        return size.fetchPositions().map {
            Cell(it, getCharacterAt(it + offset).get())
        }
    }

    override fun transform(transformer: TextCharacterTransformer): TextImage {
        val result = TextImageBuilder.newBuilder()
                .size(getBoundableSize())
                .build()
        fetchCells().forEach { (pos, char) ->
            result.setCharacterAt(pos, transformer.transform(char))
        }
        return result
    }

    @Synchronized
    override fun combineWith(textImage: TextImage, offset: Position): TextImage {
        val xLength = Math.max(getBoundableSize().xLength, offset.x + textImage.getBoundableSize().xLength)
        val yLength = Math.max(getBoundableSize().yLength, offset.y + textImage.getBoundableSize().yLength)

        val surface = TextImageBuilder.newBuilder()
                .size(Size.of(xLength, yLength))
                .toCopy(copyArray(buffer, getBoundableSize(), TextCharacterBuilder.EMPTY))
                .build()
        surface.draw(textImage, offset)
        return surface
    }

    override fun setCharacterAt(position: Position, character: TextCharacter) = position.let { (x, y) ->
        if (positionIsOutOfBounds(x, y)) {
            false
        } else {
            val oldChar = buffer[y][x]
            return if (oldChar == character) {
                false //
            } else {
                buffer[y][x] = character
                true
            }
        }
    }

    override fun getCharacterAt(position: Position) = position.let { (x, y) ->
        if (positionIsOutOfBounds(x, y)) {
            Optional.empty()
        } else {
            Optional.of(buffer[y][x])
        }
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        val startYIdx = 0
        val startXIdx = 0
        val yLength: Int = getBoundableSize().yLength
        val xLength: Int = getBoundableSize().xLength
        val destYOffset = offset.y
        val destXOffset = offset.x
        // TODO: some other operation involving Cells?
        (startYIdx until startYIdx + yLength).forEach { y ->
            (startXIdx until startXIdx + xLength).forEach { x ->
                if (buffer[y][x] != TextCharacterBuilder.EMPTY) {
                    surface.setCharacterAt(
                            Position.of(x - startXIdx + destXOffset,
                                    y - startYIdx + destYOffset),
                            buffer[y][x])
                }
            }
        }
    }

    private fun copyArray(toCopy: Array<Array<TextCharacter>>,
                          size: Size,
                          filler: TextCharacter,
                          offset: Position = Position.DEFAULT_POSITION): Array<Array<TextCharacter>> {
        val result = (0 until size.yLength).map {
            (0 until size.xLength).map { filler }.toTypedArray()
        }.toTypedArray()
        size.fetchPositions().forEach { (x, y) ->
            if (y < toCopy.size && x < toCopy[y].size) {
                result[y][x] = toCopy[y + offset.y][x + offset.x]
            } else {
                result[y][x] = filler
            }
        }
        return result
    }

    private fun positionIsOutOfBounds(x: Int, y: Int)
            = x < 0 || y < 0 || y >= buffer.size || x >= buffer[0].size

}
