package org.codetome.zircon.terminal.swing

import org.codetome.zircon.Modifier
import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextColor
import org.codetome.zircon.font.MonospaceFontRenderer
import org.codetome.zircon.graphics.StyleSet
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.input.Input
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import org.codetome.zircon.terminal.TerminalSize
import org.codetome.zircon.terminal.config.FontConfiguration
import org.codetome.zircon.terminal.config.ColorConfiguration
import org.codetome.zircon.terminal.config.DeviceConfiguration
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image
import java.util.*
import javax.swing.JComponent

/**
 * This class provides an Swing implementation of the [Terminal] interface that
 * is an embeddable component you can put into a Swing container.
 */
class SwingTerminalComponent(
        initialTerminalSize: TerminalSize,
        deviceConfiguration: DeviceConfiguration,
        fontConfiguration: MonospaceFontRenderer<Graphics>,
        colorConfiguration: ColorConfiguration)
    : JComponent(), Terminal {

    // this ugly hack is because of the lack of multiple inheritance
    // delegation does not work either because we can't pass `this` in the constructor for a default value
    private val terminalImplementation: SwingTerminalImplementation = SwingTerminalImplementation(
            this,
            fontConfiguration,
            initialTerminalSize,
            deviceConfiguration,
            colorConfiguration)

    override fun getBackgroundColor(): TextColor {
        return terminalImplementation.getBackgroundColor()
    }

    override fun getForegroundColor(): TextColor {
        return terminalImplementation.getForegroundColor()
    }

    override fun enableModifiers(vararg modifiers: Modifier) {
        terminalImplementation.enableModifiers(*modifiers)
    }

    override fun disableModifiers(vararg modifiers: Modifier) {
        terminalImplementation.disableModifiers(*modifiers)
    }

    override fun setModifiers(modifiers: Set<Modifier>) {
        terminalImplementation.setModifiers(modifiers)
    }

    override fun clearModifiers() {
        terminalImplementation.clearModifiers()
    }

    override fun getActiveModifiers(): Set<Modifier> {
        return terminalImplementation.getActiveModifiers()
    }

    override fun setStyleFrom(source: StyleSet) {
        terminalImplementation.setStyleFrom(source)
    }

    override fun setForegroundColor(foregroundColor: TextColor) {
        terminalImplementation.setForegroundColor(foregroundColor)
    }

    override fun setBackgroundColor(backgroundColor: TextColor) {
        terminalImplementation.setBackgroundColor(backgroundColor)
    }

    @Synchronized
    override fun getPreferredSize(): Dimension {
        return terminalImplementation.getPreferredSize()
    }

    @Synchronized
    override fun paintComponent(componentGraphics: Graphics) {
        terminalImplementation.paintComponent(componentGraphics)
    }

    override fun clearScreen() {
        terminalImplementation.clearScreen()
    }

    override fun getCursorPosition() = terminalImplementation.getCursorPosition()

    override fun setCursorPosition(cursorPosition: TerminalPosition) {
        terminalImplementation.setCursorPosition(cursorPosition)
    }

    override fun isCursorVisible() = terminalImplementation.isCursorVisible()

    override fun setCursorVisible(cursorVisible: Boolean) {
        terminalImplementation.setCursorVisible(cursorVisible)
    }

    override fun putCharacter(c: Char) {
        terminalImplementation.putCharacter(c)
    }

    override fun enableModifier(modifier: Modifier) {
        terminalImplementation.enableModifier(modifier)
    }

    override fun disableModifier(modifier: Modifier) {
        terminalImplementation.disableModifier(modifier)
    }

    override fun resetColorsAndModifiers() {
        terminalImplementation.resetColorsAndModifiers()
    }

    override fun getTerminalSize() = terminalImplementation.getTerminalSize()

    override fun setTerminalSize(newSize: TerminalSize) {
        terminalImplementation.setTerminalSize(newSize)
    }

    override fun flush() = terminalImplementation.flush()

    override fun close() = terminalImplementation.close()

    override fun pollInput(): Optional<Input> {
        return terminalImplementation.pollInput()
    }

    override fun newTextGraphics(): TextGraphics {
        return terminalImplementation.newTextGraphics()
    }

    override fun addResizeListener(listener: TerminalResizeListener) {
        terminalImplementation.addResizeListener(listener)
    }

    override fun removeResizeListener(listener: TerminalResizeListener) {
        terminalImplementation.removeResizeListener(listener)
    }
}
