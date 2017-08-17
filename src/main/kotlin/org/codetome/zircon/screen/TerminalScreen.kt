package org.codetome.zircon.screen

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.TextCharacterBuilder.Companion.DEFAULT_CHARACTER
import org.codetome.zircon.behavior.CursorHolder
import org.codetome.zircon.behavior.Layerable
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputProvider
import org.codetome.zircon.input.InputType
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import java.util.*
import kotlin.system.measureNanoTime

/**
 * This class implements the logic defined in the [Screen] interface.
 * It keeps data structures for the front- and back buffers, the cursor location and
 * some other simpler states.
 */
class TerminalScreen constructor(private val terminal: Terminal)
    : Screen, CursorHolder by terminal,
        InputProvider by terminal,
        Layerable by terminal {

    private var backBuffer: ScreenBuffer = ScreenBuffer(terminal.getBoundableSize(), DEFAULT_CHARACTER)
    private var frontBuffer: ScreenBuffer = ScreenBuffer(terminal.getBoundableSize(), DEFAULT_CHARACTER)

    init {
        this.terminal.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: Size) {
                resize()
            }
        })
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

    @Synchronized
    override fun setCharacter(position: Position, screenCharacter: TextCharacter) {
        backBuffer.setCharacterAt(position, screenCharacter)
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
        flipBuffers( true)
    }

    @Synchronized
    private fun resize() {
        backBuffer = backBuffer.resize(getBoundableSize(), DEFAULT_CHARACTER)
        frontBuffer = frontBuffer.resize(getBoundableSize(), DEFAULT_CHARACTER)
    }

    @Synchronized
    private fun flipBuffers(forceRedraw: Boolean) {
        val time = measureNanoTime {
            getBoundableSize().fetchPositions().forEach { position ->
                val character = backBuffer.getCharacterAt(position).get()
                if (charDiffersInBuffers(character, position).or(forceRedraw)) {
                    terminal.setCursorPosition(position)
                    terminal.resetColorsAndModifiers()
                    terminal.setForegroundColor(character.getForegroundColor())
                    terminal.setBackgroundColor(character.getBackgroundColor())
                    character.getModifiers().forEach {
                        terminal.enableModifier(it)
                    }
                    terminal.putCharacter(character.getCharacter())
                }
            }
            backBuffer.copyTo(frontBuffer)
            terminal.flush()
        }
        println("Rendering took: ${time / 1000 / 1000} ms.")
    }

    private fun charDiffersInBuffers(backChar: TextCharacter, position: Position)
            = backChar != frontBuffer.getCharacterAt(position).get()

    private fun getCharacterFromBuffer(position: Position, buffer: ScreenBuffer): Optional<TextCharacter> {
        return buffer.getCharacterAt(position)
    }
}
