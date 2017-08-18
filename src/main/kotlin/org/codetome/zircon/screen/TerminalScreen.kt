package org.codetome.zircon.screen

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextCharacterBuilder.Companion.DEFAULT_CHARACTER
import org.codetome.zircon.behavior.CursorHolder
import org.codetome.zircon.behavior.Drawable
import org.codetome.zircon.behavior.Layerable
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputProvider
import org.codetome.zircon.input.InputType
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import java.util.*

/**
 * This class implements the logic defined in the [Screen] interface.
 * It keeps data structures for the front- and back buffers, the cursor location and
 * some other simpler states.
 */
class TerminalScreen constructor(private val terminal: Terminal)
    : Screen,
        CursorHolder by terminal,
        InputProvider by terminal,
        Layerable by terminal {

    private var backBuffer: ScreenBuffer = ScreenBuffer(terminal.getBoundableSize(), DEFAULT_CHARACTER)
    private var frontBuffer: ScreenBuffer = ScreenBuffer(terminal.getBoundableSize(), DEFAULT_CHARACTER)
    private var lastKnownCursorPosition = Position.DEFAULT_POSITION

    init {
        this.terminal.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: Size) {
                resize()
            }
        })
    }

    override fun setCursorPosition(cursorPosition: Position) {
        this.lastKnownCursorPosition = cursorPosition
        terminal.setCursorPosition(cursorPosition)
    }

    override fun addInput(input: Input) {
        terminal.addInput(input)
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
        flipBuffers(true)
    }

    override fun refresh() {
        flipBuffers(false)
    }

    override fun close() {
        //Drain the input queue
        var input: Optional<Input>
        do {
            input = pollInput()
        } while (input.isPresent && input.get().getInputType() !== InputType.EOF)
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
