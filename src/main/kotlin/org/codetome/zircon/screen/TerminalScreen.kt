package org.codetome.zircon.screen

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextCharacter.Companion.DEFAULT_CHARACTER
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputType
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import org.codetome.zircon.terminal.TerminalSize
import java.util.*
import java.util.concurrent.atomic.AtomicReference

/**
 * This class implements the logic defined in the [Screen] interface.
 * It keeps data structures for the front- and back buffers, the cursor location and
 * some other simpler states.
 */
class TerminalScreen(private val terminal: Terminal) : Screen {

    private var cursorPosition = TerminalPosition.DEFAULT_POSITION
    private var backBuffer: ScreenBuffer = ScreenBuffer(terminal.getTerminalSize(), DEFAULT_CHARACTER)
    private var frontBuffer: ScreenBuffer = ScreenBuffer(terminal.getTerminalSize(), DEFAULT_CHARACTER)
    private var tabBehavior = AtomicReference(TabBehavior.DEFAULT_TAB_BEHAVIOR)
    private var latestResizeRequest: Optional<TerminalSize> = Optional.empty()
    private var fullRedrawHint: Boolean = true

    init {
        this.terminal.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: TerminalSize) = addResizeRequest(newSize)
        })
        this.fullRedrawHint = true
    }

    override fun getTabBehavior() = tabBehavior.get()

    override fun setTabBehavior(tabBehavior: TabBehavior) {
        this.tabBehavior.set(tabBehavior)
    }

    override fun readInput(): Input {
        return terminal.readInput()
    }

    override fun pollInput(): Optional<Input> {
        return terminal.pollInput()
    }

    override fun getTerminalSize() = terminal.getTerminalSize()

    override fun getCursorPosition() = cursorPosition

    override fun setCursorPosition(position: TerminalPosition) {
        this.cursorPosition = position
        terminal.setCursorPosition(position)
    }

    override fun display() {
        flipBuffers(true)
    }

    override fun refresh() {
        flipBuffers(false)
    }

    override fun newTextGraphics(): TextGraphics {
        return object : ScreenTextGraphics(this) {
            override fun drawImage(topLeft: TerminalPosition, image: TextImage) {
                val sourceImageTopLeft = TerminalPosition.TOP_LEFT_CORNER
                val sourceImageSize = image.getSize()
                backBuffer.copyFrom(image, sourceImageTopLeft.row, sourceImageSize.rows, sourceImageTopLeft.column, sourceImageSize.columns, topLeft.row, topLeft.column)
            }
        }
    }

    @Synchronized
    override fun setCharacter(position: TerminalPosition, screenCharacter: TextCharacter) {
        val (column, _) = position
        var character = screenCharacter
        if (character.getCharacter() == '\t') {
            character = character.withCharacter(' ')
            for (i in 0..tabBehavior.get().replaceTabs("\t", column).length - 1) {
                backBuffer.setCharacterAt(position.withRelativeColumn(i), character)
            }
        } else {
            backBuffer.setCharacterAt(position, character)
        }
    }

    @Synchronized
    override fun getFrontCharacter(position: TerminalPosition): TextCharacter {
        return getCharacterFromBuffer(position, frontBuffer)
    }

    @Synchronized
    override fun getBackCharacter(position: TerminalPosition): TextCharacter {
        return getCharacterFromBuffer(position, backBuffer)
    }

    @Synchronized
    override fun clear() {
        backBuffer.setAll(DEFAULT_CHARACTER)
        fullRedrawHint = true
    }

    @Synchronized
    override fun doResizeIfNecessary(): Optional<TerminalSize> {
        val pendingResize = getAndClearPendingResize()
        if (pendingResize.isPresent) {
            backBuffer = backBuffer.resize(pendingResize.get(), DEFAULT_CHARACTER)
            frontBuffer = frontBuffer.resize(pendingResize.get(), DEFAULT_CHARACTER)
        }
        if (pendingResize.isPresent) {
            fullRedrawHint = true
        }
        return pendingResize
    }

    override fun close() {
        //Drain the input queue
        var input: Optional<Input>
        do {
            input = pollInput()
        } while (input.isPresent && input.get().getInputType() !== InputType.EOF)
    }

    @Synchronized
    private fun flipBuffers(forceRedraw: Boolean) {
        getTerminalSize().fetchPositions().forEach { position ->
            val backChar = backBuffer.getCharacterAt(position)
            if (charDiffersInBuffers(backChar, position).or(forceRedraw)) {
                terminal.setCursorPosition(position)
                terminal.resetColorsAndModifiers()
                terminal.setForegroundColor(backChar.getForegroundColor())
                terminal.setBackgroundColor(backChar.getBackgroundColor())
                backChar.getModifiers().forEach {
                    terminal.enableModifier(it)
                }
                terminal.putCharacter(backChar.getCharacter())
            }
        }
        backBuffer.copyTo(frontBuffer)
        terminal.flush()
    }

    private fun charDiffersInBuffers(backChar: TextCharacter, position: TerminalPosition) = backChar != frontBuffer.getCharacterAt(position)

    @Synchronized
    private fun getAndClearPendingResize(): Optional<TerminalSize> {
        if (latestResizeRequest.isPresent) {
            terminal.getTerminalSize()
            terminal.setTerminalSize(latestResizeRequest.get())
            latestResizeRequest = Optional.empty()
            return Optional.of(terminal.getTerminalSize())
        }
        return Optional.empty<TerminalSize>()
    }

    private fun addResizeRequest(newSize: TerminalSize) {
        latestResizeRequest = Optional.of(newSize)
    }

    private fun getCharacterFromBuffer(position: TerminalPosition, buffer: ScreenBuffer): TextCharacter {
        return buffer.getCharacterAt(position)
    }
}
