package org.codetome.zircon.screen

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextCharacter.Companion.DEFAULT_CHARACTER
import org.codetome.zircon.behavior.CursorHolder
import org.codetome.zircon.behavior.Layerable
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputProvider
import org.codetome.zircon.input.InputType
import org.codetome.zircon.terminal.Size
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import org.codetome.zircon.terminal.virtual.VirtualTerminal
import java.util.*
import kotlin.system.measureNanoTime

/**
 * This class implements the logic defined in the [Screen] interface.
 * It keeps data structures for the front- and back buffers, the cursor location and
 * some other simpler states.
 */
class TerminalScreen constructor(private val terminal: VirtualTerminal)
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

    override fun getSize() = terminal.getBoundableSize()

    @Synchronized
    override fun getFrontCharacter(position: Position): TextCharacter {
        return getCharacterFromBuffer(position, frontBuffer)
    }

    @Synchronized
    override fun getBackCharacter(position: Position): TextCharacter {
        return getCharacterFromBuffer(position, backBuffer)
    }

    @Synchronized
    override fun setCharacter(position: Position, screenCharacter: TextCharacter) {
        backBuffer.setCharacterAt(position, screenCharacter)
    }

    override fun newTextGraphics(): TextGraphics {
        return object : ScreenTextGraphics(this) {
            override fun drawImage(topLeft: Position, image: TextImage) {
                val sourceImageTopLeft = Position.TOP_LEFT_CORNER
                val sourceImageSize = image.getBoundableSize()
                backBuffer.copyFrom(image, sourceImageTopLeft.row, sourceImageSize.rows, sourceImageTopLeft.column, sourceImageSize.columns, topLeft.row, topLeft.column)
            }
        }
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
        backBuffer.setAll(DEFAULT_CHARACTER)
        flipBuffers(true)
    }

    @Synchronized
    private fun resize() {
        backBuffer = backBuffer.resize(getSize(), DEFAULT_CHARACTER)
        frontBuffer = frontBuffer.resize(getSize(), DEFAULT_CHARACTER)
    }

    @Synchronized
    private fun flipBuffers(forceRedraw: Boolean) {
        val time = measureNanoTime {
            getSize().fetchPositions().forEach { position ->
                val character = backBuffer.getCharacterAt(position)
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
            = backChar != frontBuffer.getCharacterAt(position)

    private fun getCharacterFromBuffer(position: Position, buffer: ScreenBuffer): TextCharacter {
        return buffer.getCharacterAt(position)
    }
}
