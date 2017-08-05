package org.codetome.zircon.graphics

import org.codetome.zircon.Modifier
import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextColor
import org.codetome.zircon.builder.TextColorFactory
import org.codetome.zircon.graphics.style.StyleSet
import org.codetome.zircon.terminal.Size
import java.util.*

/**
 * [TextGraphics] implementation that does nothing, but has a pre-defined size
 */
internal class NoOpTextGraphics(private val size: Size) : TextGraphics {
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

    override fun newTextGraphics(topLeftCorner: Position, size: Size): TextGraphics {
        return this
    }

    override fun fill(c: Char) {
    }

    override fun setCharacter(position: Position, character: Char) {
    }

    override fun setCharacter(position: Position, character: TextCharacter) {
    }

    override fun drawLine(fromPoint: Position, toPoint: Position, character: Char) {
    }

    override fun drawLine(fromPoint: Position, toPoint: Position, character: TextCharacter) {
    }

    override fun drawTriangle(p1: Position, p2: Position, p3: Position, character: Char) {
    }

    override fun drawTriangle(p1: Position, p2: Position, p3: Position, character: TextCharacter) {
    }

    override fun fillTriangle(p1: Position, p2: Position, p3: Position, character: Char) {
    }

    override fun fillTriangle(p1: Position, p2: Position, p3: Position, character: TextCharacter) {
    }

    override fun drawRectangle(topLeft: Position, size: Size, character: Char) {
    }

    override fun drawRectangle(topLeft: Position, size: Size, character: TextCharacter) {
    }

    override fun fillRectangle(topLeft: Position, size: Size, character: Char) {
    }

    override fun fillRectangle(topLeft: Position, size: Size, character: TextCharacter) {
    }

    override fun drawImage(topLeft: Position, image: TextImage) {
    }

    override fun drawImage(topLeft: Position, image: TextImage, sourceImageTopLeft: Position, sourceImageSize: Size) {
    }

    override fun putString(position: Position, string: String, extraModifiers: Set<Modifier>) {
    }

    override fun getCharacter(position: Position) = Optional.of(TextCharacter.DEFAULT_CHARACTER)
}
