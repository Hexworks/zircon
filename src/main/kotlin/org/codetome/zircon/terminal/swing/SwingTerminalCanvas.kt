package org.codetome.zircon.terminal.swing

import org.codetome.zircon.Modifier
import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextColor
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.font.Font
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
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * This class provides an Swing implementation of the [Terminal] interface that
 * is an embeddable component you can put into a Swing container. It also combines
 * the Canvas itself with the terminal implementation.
 */
class SwingTerminalCanvas(
        initialSize: Size,
        deviceConfiguration: DeviceConfiguration,
        font: Font<BufferedImage>)
    : Canvas(), VirtualTerminal {

    // this ugly hack of delegation is necessary because of the lack of multiple inheritance
    // the delegation feature of Kotlin does not work either because we can't pass `this` in
    // the constructor for a default value
    private val terminal: SwingTerminalImplementation = SwingTerminalImplementation(
            this,
            font,
            initialSize,
            deviceConfiguration)

    init {
        background = Color.BLACK
    }

    override fun addInput(input: Input) = terminal.addInput(input)

    override fun getCharacter(position: Position) = terminal.getCharacter(position)

    override fun setCharacter(position: Position, textCharacter: TextCharacter)
            = terminal.setCharacter(position, textCharacter)

    override fun addVirtualTerminalListener(listener: VirtualTerminalListener)
            = terminal.addVirtualTerminalListener(listener)

    override fun removeVirtualTerminalListener(listener: VirtualTerminalListener)
            = terminal.removeVirtualTerminalListener(listener)

    override fun isDirty() = terminal.isDirty()

    override fun forEachDirtyCell(fn: (Cell) -> Unit)
            = terminal.forEachDirtyCell(fn)

    override fun forEachCell(fn: (Cell) -> Unit) = terminal.forEachCell(fn)

    override fun addOverlay(layer: Layer) = terminal.addOverlay(layer)

    override fun intersects(boundable: Boundable) = terminal.intersects(boundable)

    override fun popOverlay() = terminal.popOverlay()

    override fun containsPosition(position: Position) = terminal.containsPosition(position)

    override fun containsBoundable(boundable: Boundable) = terminal.containsBoundable(boundable)

    override fun removeLayer(layer: Layer) = terminal.removeLayer(layer)

    override fun fetchOverlayZIntersection(absolutePosition: Position)
            = terminal.fetchOverlayZIntersection(absolutePosition)

    override fun getBackgroundColor() = terminal.getBackgroundColor()

    override fun getForegroundColor() = terminal.getForegroundColor()

    override fun enableModifiers(vararg modifiers: Modifier) = terminal.enableModifiers(*modifiers)

    override fun disableModifiers(vararg modifiers: Modifier) = terminal.disableModifiers(*modifiers)

    override fun setModifiers(modifiers: Set<Modifier>) = terminal.setModifiers(modifiers)

    override fun clearModifiers() = terminal.clearModifiers()

    override fun getActiveModifiers() = terminal.getActiveModifiers()

    override fun setStyleFrom(source: StyleSet) = terminal.setStyleFrom(source)

    override fun setForegroundColor(foregroundColor: TextColor) = terminal.setForegroundColor(foregroundColor)

    override fun setBackgroundColor(backgroundColor: TextColor) = terminal.setBackgroundColor(backgroundColor)

    @Synchronized
    override fun getPreferredSize() = terminal.getPreferredSize()

    override fun clear() = terminal.clear()

    override fun getCursorPosition() = terminal.getCursorPosition()

    override fun setCursorPosition(cursorPosition: Position) = terminal.setCursorPosition(cursorPosition)

    override fun isCursorVisible() = terminal.isCursorVisible()

    override fun setCursorVisible(cursorVisible: Boolean) = terminal.setCursorVisible(cursorVisible)

    override fun putCharacter(c: Char) = terminal.putCharacter(c)

    override fun enableModifier(modifier: Modifier) = terminal.enableModifier(modifier)

    override fun disableModifier(modifier: Modifier) = terminal.disableModifier(modifier)

    override fun resetColorsAndModifiers() = terminal.resetColorsAndModifiers()

    override fun getBoundableSize(): Size = terminal.getBoundableSize()

    override fun setSize(newSize: Size) = terminal.setSize(newSize)

    override fun flush() = terminal.draw()

    override fun close() = terminal.close()

    override fun pollInput() = terminal.pollInput()

    override fun newTextGraphics() = terminal.newTextGraphics()

    override fun addResizeListener(listener: TerminalResizeListener) = terminal.addResizeListener(listener)

    override fun removeResizeListener(listener: TerminalResizeListener) = terminal.removeResizeListener(listener)
}
