package org.codetome.zircon.terminal.swing

import org.codetome.zircon.*
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.behavior.Drawable
import org.codetome.zircon.color.TextColor
import org.codetome.zircon.component.Container
import org.codetome.zircon.font.Font
import org.codetome.zircon.graphics.layer.Layer
import org.codetome.zircon.graphics.style.StyleSet
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputProvider
import org.codetome.zircon.terminal.IterableTerminal
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import org.codetome.zircon.terminal.config.DeviceConfiguration
import java.awt.Canvas
import java.awt.Color
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
    : Canvas(), IterableTerminal {

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

    override fun getContainer() = terminal.getContainer()

    override fun setContainer(container: Container) = terminal.setContainer(container)

    override fun drawComponentsToImage() = terminal.drawComponentsToImage()

    override fun setInputProvider(inputProvider: InputProvider)
            = terminal.setInputProvider(inputProvider)

    override fun draw(drawable: Drawable, offset: Position)
            = terminal.draw(drawable, offset)

    override fun addInput(input: Input) = terminal.addInput(input)

    override fun getCharacterAt(position: Position) = terminal.getCharacterAt(position)

    override fun setCharacterAt(position: Position, character: TextCharacter)
            = terminal.setCharacterAt(position, character)

    override fun setCharacterAt(position: Position, character: Char)
            = terminal.setCharacterAt(position, character)

    override fun toStyleSet() = terminal.toStyleSet()

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

    override fun addResizeListener(listener: TerminalResizeListener) = terminal.addResizeListener(listener)

    override fun removeResizeListener(listener: TerminalResizeListener) = terminal.removeResizeListener(listener)
}
