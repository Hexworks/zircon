package org.codetome.zircon.terminal.virtual

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.impl.AbstractTextGraphics
import java.util.*

/**
 * Implementation of [org.codetome.zircon.graphics.TextGraphics] for a [VirtualTerminal].
 */
class VirtualTerminalTextGraphics(private val virtualTerminal: DefaultVirtualTerminal)
    : AbstractTextGraphics() {

    override fun getSize() = virtualTerminal.getTerminalSize()

    override fun getCharacter(position: TerminalPosition): Optional<TextCharacter> {
        return Optional.of(virtualTerminal.getCharacter(position))
    }

    override fun setCharacter(position: TerminalPosition, character: TextCharacter) {
        val size = getSize()
        val (column, row) = position
        if (column < 0 || column >= size.columns ||
                row < 0 || row >= size.rows) {
            return
        }
        synchronized(virtualTerminal) {
            virtualTerminal.setCursorPosition(TerminalPosition(column, row))
            virtualTerminal.putCharacter(character)
        }
    }
}
