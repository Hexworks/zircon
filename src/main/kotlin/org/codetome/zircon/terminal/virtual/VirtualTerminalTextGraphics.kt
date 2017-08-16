package org.codetome.zircon.terminal.virtual

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.AbstractTextGraphics
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.box.BoxConnectingMode
import org.codetome.zircon.graphics.box.BoxType
import org.codetome.zircon.graphics.style.StyleSet
import java.util.*

/**
 * Implementation of [org.codetome.zircon.graphics.TextGraphics] for a [VirtualTerminal].
 */
class VirtualTerminalTextGraphics(private val virtualTerminal: DefaultVirtualTerminal)
    : AbstractTextGraphics() {

    override fun drawBox(textGraphics: TextGraphics, topLeft: Position, size: Size, styleToUse: StyleSet, boxType: BoxType, boxConnectingMode: BoxConnectingMode) {
        TODO("not implemented")
    }

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
            virtualTerminal.setCursorPosition(Position.of(column, row))
            virtualTerminal.putCharacter(character)
        }
    }
}
