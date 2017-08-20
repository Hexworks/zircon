package org.codetome.zircon.screen.impl

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.StyleSetBuilder
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextCharacterBuilder.Companion.DEFAULT_CHARACTER
import org.codetome.zircon.behavior.ContainerHolder
import org.codetome.zircon.behavior.CursorHandler
import org.codetome.zircon.behavior.Drawable
import org.codetome.zircon.behavior.Layerable
import org.codetome.zircon.behavior.impl.DefaultContainerHolder
import org.codetome.zircon.component.ComponentStyles
import org.codetome.zircon.component.impl.DefaultContainer
import org.codetome.zircon.input.Input
import org.codetome.zircon.screen.Screen
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import java.util.*
import java.util.function.Consumer

/**
 * This class implements the logic defined in the [Screen] interface.
 * It keeps data structures for the front- and back buffers, the cursor location and
 * some other simpler states.
 */
class TerminalScreen constructor(private val terminal: Terminal,
                                 private val containerHolder: ContainerHolder)
    : Screen,
        CursorHandler by terminal,
        Layerable by terminal,
        ContainerHolder by containerHolder {

    private var backBuffer: ScreenBuffer = ScreenBuffer(terminal.getBoundableSize(), DEFAULT_CHARACTER)
    private var frontBuffer: ScreenBuffer = ScreenBuffer(terminal.getBoundableSize(), DEFAULT_CHARACTER)
    private var lastKnownCursorPosition = Position.DEFAULT_POSITION

    constructor(terminal: Terminal) : this(
            terminal = terminal,
            containerHolder = DefaultContainerHolder(DefaultContainer(
                    initialSize = terminal.getBoundableSize(),
                    position = Position.DEFAULT_POSITION,
                    componentStyles = ComponentStyles(StyleSetBuilder.EMPTY))))

    init {
        this.terminal.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: Size) {
                resize()
            }
        })
        
    }

    override fun putCursorAt(cursorPosition: Position) {
        this.lastKnownCursorPosition = cursorPosition
        terminal.putCursorAt(cursorPosition)
    }

    @Synchronized
    override fun getFrontCharacter(position: Position): Optional<TextCharacter> {
        return getCharacterFromBuffer(position, frontBuffer)
    }

    @Synchronized
    override fun getBackCharacter(position: Position): Optional<TextCharacter> {
        return getCharacterFromBuffer(position, backBuffer)
    }

    override fun getCharacterAt(position: Position) = getBackCharacter(position)

    override fun setCharacterAt(position: Position, character: Char): Boolean {
        return setCharacterAt(position, TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter(character))
    }

    override fun draw(drawable: Drawable, offset: Position) {
        backBuffer.draw(drawable, offset)
    }

    @Synchronized
    override fun setCharacterAt(position: Position, character: TextCharacter): Boolean {
        return backBuffer.setCharacterAt(position, character)
    }

    override fun display() {
        // TODO: add layers to terminal
        flipBuffers(true)
    }

    override fun refresh() {
        // TODO: add layers to terminal
        flipBuffers(false)
    }

    override fun close() {
    }

    override fun subscribe(inputCallback: Consumer<Input>) {
        terminal.subscribe(inputCallback)
    }

    @Synchronized
    override fun clear() {
        getBoundableSize().fetchPositions().forEach {
            backBuffer.setCharacterAt(it, DEFAULT_CHARACTER)
        }
        flipBuffers(true)
    }

    @Synchronized
    private fun resize() {
        backBuffer = backBuffer.resize(getBoundableSize(), DEFAULT_CHARACTER)
        frontBuffer = frontBuffer.resize(getBoundableSize(), DEFAULT_CHARACTER)
    }

    @Synchronized
    private fun flipBuffers(forceRedraw: Boolean) {
        getBoundableSize().fetchPositions().forEach { position ->
            val character = backBuffer.getCharacterAt(position).get()
            if (charDiffersInBuffers(character, position).or(forceRedraw)) {
                terminal.setCharacterAt(position, character)
            }
        }
        frontBuffer.draw(backBuffer)
        terminal.flush()
    }

    private fun charDiffersInBuffers(backChar: TextCharacter, position: Position)
            = backChar != frontBuffer.getCharacterAt(position).get()
            || lastKnownCursorPosition != getCursorPosition()

    private fun getCharacterFromBuffer(position: Position, buffer: ScreenBuffer): Optional<TextCharacter> {
        return buffer.getCharacterAt(position)
    }
}
