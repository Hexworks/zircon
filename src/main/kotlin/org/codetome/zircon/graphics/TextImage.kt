package org.codetome.zircon.graphics

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.terminal.TerminalSize

/**
 * An image built from [TextCharacter]s with color and style information.
 * These are completely in memory and not visible,
 * but can be used when drawing with a [TextGraphics] objects.
 */
interface TextImage {
    /**
     * Returns the dimensions of this [TextImage], in columns and rows
     */
    fun getSize(): TerminalSize

    /**
     * Returns the character stored at a particular position in this image
     */
    fun getCharacterAt(position: TerminalPosition): TextCharacter

    /**
     * Sets the character at a specific position in the image to a particular [TextCharacter]. If the position is outside
     * of the images size, this method does nothing.
     */
    fun setCharacterAt(position: TerminalPosition, character: TextCharacter)

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
    fun resize(newSize: TerminalSize, filler: TextCharacter): TextImage


    /**
     * Copies this [TextImage]'s content to another [TextImage]. If the destination [TextImage] is larger than this
     * [TextImage], the areas outside of the area that is written to will be untouched.
     */
    fun copyTo(destination: TextImage)

    /**
     * Copies this [TextImage] content to another [TextImage]. If the destination [TextImage] is larger than this
     * [TextImage], the areas outside of the area that is written to will be untouched.
     */
    fun copyTo(
            destination: TextImage,
            startRowIndex: Int = 0,
            rows: Int = getSize().rows,
            startColumnIndex: Int = 0,
            columns: Int = getSize().columns,
            destinationRowOffset: Int = 0,
            destinationColumnOffset: Int = 0)

}
