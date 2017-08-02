package org.codetome.zircon.graphics.impl

import org.codetome.zircon.*
import org.codetome.zircon.graphics.StyleSet
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.screen.TabBehavior
import org.codetome.zircon.terminal.TerminalSize
import java.util.*

/**
 * [TextGraphics] implementation that does nothing, but has a pre-defined size
 */
internal class NoOpTextGraphics(private val size: TerminalSize) : TextGraphics  {
    override fun enableModifier(modifier: Modifier) {
    }

    override fun disableModifier(modifier: Modifier) {
    }

    override fun resetColorsAndModifiers() {
    }

    override fun getBackgroundColor() = TextColorFactory.DEFAULT_BACKGROUND_COLOR

    override fun setBackgroundColor(backgroundColor: TextColor) {
    }

    override fun getForegroundColor() = TextColorFactory.DEFAULT_FOREGROUND_COLOR

    override fun setForegroundColor(foregroundColor: TextColor) {
    }

    override fun enableModifiers(vararg modifiers: Modifier) {
    }

    override fun disableModifiers(vararg modifiers: Modifier) {
    }

    override fun setModifiers(modifiers: Set<Modifier>) {
    }

    override fun clearModifiers() {
    }

    override fun getActiveModifiers() = setOf<Modifier>()

    override fun setStyleFrom(source: StyleSet) {
    }

    override fun getSize() = size

    override fun newTextGraphics(topLeftCorner: TerminalPosition, size: TerminalSize): TextGraphics {
        return this
    }

    override fun getTabBehavior() = TabBehavior.ALIGN_TO_COLUMN_4

    override fun setTabBehavior(tabBehaviour: TabBehavior) {
    }

    override fun fill(c: Char) {
    }

    override fun setCharacter(position: TerminalPosition, character: Char) {
    }

    override fun setCharacter(position: TerminalPosition, character: TextCharacter) {
    }

    override fun drawLine(fromPoint: TerminalPosition, toPoint: TerminalPosition, character: Char) {
    }

    override fun drawLine(fromPoint: TerminalPosition, toPoint: TerminalPosition, character: TextCharacter) {
    }

    override fun drawTriangle(p1: TerminalPosition, p2: TerminalPosition, p3: TerminalPosition, character: Char) {
    }

    override fun drawTriangle(p1: TerminalPosition, p2: TerminalPosition, p3: TerminalPosition, character: TextCharacter) {
    }

    override fun fillTriangle(p1: TerminalPosition, p2: TerminalPosition, p3: TerminalPosition, character: Char) {
    }

    override fun fillTriangle(p1: TerminalPosition, p2: TerminalPosition, p3: TerminalPosition, character: TextCharacter) {
    }

    override fun drawRectangle(topLeft: TerminalPosition, size: TerminalSize, character: Char) {
    }

    override fun drawRectangle(topLeft: TerminalPosition, size: TerminalSize, character: TextCharacter) {
    }

    override fun fillRectangle(topLeft: TerminalPosition, size: TerminalSize, character: Char) {
    }

    override fun fillRectangle(topLeft: TerminalPosition, size: TerminalSize, character: TextCharacter) {
    }
    override fun drawImage(topLeft: TerminalPosition, image: TextImage) {
    }

    override fun putString(position: TerminalPosition, string: String, extraModifiers: Set<Modifier>) {
    }

    override fun getCharacter(position: TerminalPosition) = Optional.of(TextCharacter.DEFAULT_CHARACTER)
}
