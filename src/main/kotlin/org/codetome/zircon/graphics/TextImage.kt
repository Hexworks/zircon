package org.codetome.zircon.graphics

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.terminal.Size

/**
 * An image built from [TextCharacter]s with color and style information.
 * These are completely in memory and not visible,
 * but can be used when drawing with [TextGraphics] objects.
 */
interface TextImage: Boundable {

    /**
     * Returns the character stored at a particular position in this image
     */
    fun getCharacterAt(position: Position): TextCharacter

    /**
     * Sets the character at a specific position in the image to a particular [TextCharacter]. If the position is outside
     * of the images size, this method does nothing.
     */
    fun setCharacterAt(position: Position, character: TextCharacter)

    /**
     * Sets the text image content to one specified character (including color and style)
     */
    fun setAll(character: TextCharacter)

    /**
     * Creates a [TextGraphics] object that targets this [TextImage] for all its drawing operations.
     */
    fun newTextGraphics(): TextGraphics

    /**
     * Returns a copy of this image resized to a new size and using a specified filler character if the new size is
     * larger than the old and we need to fill in empty areas. The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size, filler: TextCharacter): TextImage


    /**
     * Copies this [TextImage]'s content to another [TextImage]. If the destination [TextImage] is larger than this
     * [TextImage], the areas outside of the area that is written to will be untouched.
     */
    fun copyTo(destination: TextImage)

    /**
     * Copies this [TextImage]'s content to another [TextImage]. If the destination [TextImage] is larger than this
     * [TextImage], the areas outside of the area that is written to will be untouched.
     * Note that start row / column indexes and destination row / column offsets can be negative.
     * If the start row / column is negative `n` rows will be skipped from the source image.
     * So if you copy a 2x2 image over a 3x3 image and `startRowIndex` is -1 the first row of the 3x3 image
     * won't be touched.
     * If the offsets is -1 the first row / column will be overwritten by the 2x2 image's second row
     * @param destination [TextImage] to copy to
     * @param startRowIndex which row in this image to start copying from
     * @param rows how many rows to copy
     * @param startColumnIndex which column in this image to start copying from
     * @param columns how many columns to copy
     * @param destinationRowOffset Offset (in number of rows) in the target image where we want to first copied row to be
     * @param destinationColumnOffset Offset (in number of columns) in the target image where we want to first copied column to be
     */
    fun copyTo(
            destination: TextImage,
            startRowIndex: Int = 0,
            rows: Int = getBoundableSize().rows,
            startColumnIndex: Int = 0,
            columns: Int = getBoundableSize().columns,
            destinationRowOffset: Int = 0,
            destinationColumnOffset: Int = 0)

}
