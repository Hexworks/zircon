package org.codetome.zircon.terminal.virtual

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.AbstractTextGraphics
import java.util.*

/**
 * Implementation of [org.codetome.zircon.graphics.TextGraphics] for a [VirtualTerminal].
 */
class VirtualTerminalTextGraphics(private val virtualTerminal: DefaultVirtualTerminal)
    : AbstractTextGraphics() {

    override fun getSize() = virtualTerminal.getBoundableSize()

    override fun getCharacter(position: Position): Optional<TextCharacter> {
        return Optional.of(virtualTerminal.getCharacter(position))
    }

    override fun setCharacter(position: Position, character: TextCharacter) {
        val size = getSize()
        val (column, row) = position
        if (column < 0 || column >= size.columns ||
                row < 0 || row >= size.rows) {
            return
        }
        synchronized(virtualTerminal) {
            virtualTerminal.setCursorPosition(Position(column, row))
            virtualTerminal.putCharacter(character)
        }
    }
}
