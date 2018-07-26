package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.data.Tile

/**
 * A [TypingSupport] is a specialized [CursorHandler] which not only handles a cursor
 * but can simulate typing (eg.: put a character at the current cursor position and
 * advance the cursor).
 */
interface TypingSupport : CursorHandler {

    /**
     * Prints one character to the screen at the current cursor location using the style information
     * contained in the implementation of this class or the defaults if the implementor is
     * not `Styleable`.
     * Please note that the cursor will then move one column to the right, so multiple calls to
     * [.putCharacter] will print out a char without the need
     * to reposition the cursor. If you reach the end of the line while putting characters
     * using this method, you can expect the cursor to move to the beginning of the next line.
     *
     * If you try to print non-printable control characters, they will be ignored.
     */
    fun putCharacter(c: Char)

    /**
     * Prints one text character to the screen at the current cursor location with using the
     * style information present in `tc`.
     * Please note that the cursor will then move one column to the right, so multiple calls to
     * [.putTextCharacter] will print out a char without the need
     * to reposition the cursor. If you reach the end of the line while putting characters
     * using this method, you can expect the cursor to move to the beginning of the next line.
     *
     * If you try to print non-printable control characters, they will be ignored.
     */
    fun putTextCharacter(tc: Tile)

}
