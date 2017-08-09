package org.codetome.zircon.terminal.swing

import org.codetome.zircon.Modifier
import org.codetome.zircon.Position
import org.codetome.zircon.TextColor
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.font.FontRenderer
import org.codetome.zircon.graphics.layer.Layer
import org.codetome.zircon.graphics.style.StyleSet
import org.codetome.zircon.input.Input
import org.codetome.zircon.terminal.Cell
import org.codetome.zircon.terminal.Size
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import org.codetome.zircon.terminal.config.DeviceConfiguration
import org.codetome.zircon.terminal.virtual.VirtualTerminal
import org.codetome.zircon.terminal.virtual.VirtualTerminalListener
import java.awt.Graphics
import javax.swing.JComponent

/**
 * This class provides an Swing implementation of the [Terminal] interface that
 * is an embeddable component you can put into a Swing container.
 */
class SwingTerminalComponent(
        initialSize: Size,
        deviceConfiguration: DeviceConfiguration,
        fontConfiguration: FontRenderer<Graphics>)
    : JComponent(), VirtualTerminal {

    // this ugly hack of delegation is necessary because of the lack of multiple inheritance
    // the delegation feature of Kotlin does not work either because we can't pass `this` in
    // the constructor for a default value
    private val terminalImplementation: SwingTerminalImplementation = SwingTerminalImplementation(
            this,
            fontConfiguration,
            initialSize,
            deviceConfiguration)

    override fun addInput(input: Input) = terminalImplementation.addInput(input)

    override fun getCharacter(position: Position) = terminalImplementation.getCharacter(position)

    override fun addVirtualTerminalListener(listener: VirtualTerminalListener)
            = terminalImplementation.addVirtualTerminalListener(listener)

    override fun removeVirtualTerminalListener(listener: VirtualTerminalListener)
            = terminalImplementation.removeVirtualTerminalListener(listener)

    override fun forEachDirtyCell(fn: (Cell) -> Unit)
            = terminalImplementation.forEachDirtyCell(fn)

    override fun forEachCell(fn: (Cell) -> Unit) = terminalImplementation.forEachCell(fn)

    override fun addOverlay(layer: Layer) = terminalImplementation.addOverlay(layer)

    override fun intersects(boundable: Boundable) = terminalImplementation.intersects(boundable)

    override fun popOverlay() = terminalImplementation.popOverlay()

    override fun containsPosition(position: Position) = terminalImplementation.containsPosition(position)

    override fun containsBoundable(boundable: Boundable) = terminalImplementation.containsBoundable(boundable)

    override fun removeLayer(layer: Layer) = terminalImplementation.removeLayer(layer)

    override fun fetchOverlayZIntersection(absolutePosition: Position)
            = terminalImplementation.fetchOverlayZIntersection(absolutePosition)

    override fun getBackgroundColor() = terminalImplementation.getBackgroundColor()

    override fun getForegroundColor() = terminalImplementation.getForegroundColor()

    override fun enableModifiers(vararg modifiers: Modifier) = terminalImplementation.enableModifiers(*modifiers)

    override fun disableModifiers(vararg modifiers: Modifier) = terminalImplementation.disableModifiers(*modifiers)

    override fun setModifiers(modifiers: Set<Modifier>) = terminalImplementation.setModifiers(modifiers)

    override fun clearModifiers() = terminalImplementation.clearModifiers()

    override fun getActiveModifiers() = terminalImplementation.getActiveModifiers()

    override fun setStyleFrom(source: StyleSet) = terminalImplementation.setStyleFrom(source)

    override fun setForegroundColor(foregroundColor: TextColor) = terminalImplementation.setForegroundColor(foregroundColor)

    override fun setBackgroundColor(backgroundColor: TextColor) = terminalImplementation.setBackgroundColor(backgroundColor)

    @Synchronized
    override fun getPreferredSize() = terminalImplementation.getPreferredSize()

    @Synchronized
    override fun paintComponent(componentGraphics: Graphics) = terminalImplementation.paintComponent(componentGraphics)

    override fun clear() = terminalImplementation.clear()

    override fun getCursorPosition() = terminalImplementation.getCursorPosition()

    override fun setCursorPosition(cursorPosition: Position) = terminalImplementation.setCursorPosition(cursorPosition)

    override fun isCursorVisible() = terminalImplementation.isCursorVisible()

    override fun setCursorVisible(cursorVisible: Boolean) = terminalImplementation.setCursorVisible(cursorVisible)

    override fun putCharacter(c: Char) = terminalImplementation.putCharacter(c)

    override fun enableModifier(modifier: Modifier) = terminalImplementation.enableModifier(modifier)

    override fun disableModifier(modifier: Modifier) = terminalImplementation.disableModifier(modifier)

    override fun resetColorsAndModifiers() = terminalImplementation.resetColorsAndModifiers()

    override fun getBoundableSize(): Size = terminalImplementation.getBoundableSize()

    override fun setSize(newSize: Size) = terminalImplementation.setSize(newSize)

    override fun flush() = terminalImplementation.flush()

    override fun close() = terminalImplementation.close()

    override fun pollInput() = terminalImplementation.pollInput()

    override fun newTextGraphics() = terminalImplementation.newTextGraphics()

    override fun addResizeListener(listener: TerminalResizeListener) = terminalImplementation.addResizeListener(listener)

    override fun removeResizeListener(listener: TerminalResizeListener) = terminalImplementation.removeResizeListener(listener)
}
