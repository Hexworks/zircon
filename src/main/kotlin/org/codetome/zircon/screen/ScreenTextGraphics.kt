package org.codetome.zircon.screen

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.impl.AbstractTextGraphics
import org.codetome.zircon.graphics.TextGraphics
import java.util.*

/**
 * This is an implementation of [TextGraphics] that targets the output to a [Screen].
 * The ScreenTextGraphics object is valid after screen resizing.
 */
internal open class ScreenTextGraphics(private val screen: Screen) : AbstractTextGraphics() {

    override fun getCharacter(position: TerminalPosition): Optional<TextCharacter> {
        return Optional.of(screen.getBackCharacter(position))
    }

    override fun setCharacter(position: TerminalPosition, character: TextCharacter) {
        screen.setCharacter(position, character)
    }

    override fun getSize() = screen.getTerminalSize()
}
