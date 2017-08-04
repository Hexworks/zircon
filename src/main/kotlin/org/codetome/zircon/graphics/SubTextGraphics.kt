package org.codetome.zircon.graphics

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.terminal.TerminalSize
import java.util.*

/**
 * This implementation of [TextGraphics] will take a proper object and composite a view on top of it, by using a
 * top-left position and a size. Any attempts to put text outside of this area will be dropped.
 */
internal class SubTextGraphics(private val underlyingTextGraphics: TextGraphics,
                               private val topLeft: TerminalPosition,
                               private val size: TerminalSize) : AbstractTextGraphics() {

    override fun getSize() = size

    override fun getCharacter(position: TerminalPosition): Optional<TextCharacter> {
        return underlyingTextGraphics.getCharacter(topLeft.withRelative(position))
    }

    override fun setCharacter(position: TerminalPosition, character: TextCharacter) {
        val (column, row) = position
        val writableArea = size
        if (column < 0 || column >= writableArea.columns ||
                row < 0 || row >= writableArea.rows) {
            return
        }
        underlyingTextGraphics.setCharacter(topLeft.withRelative(position), character)
    }
}
