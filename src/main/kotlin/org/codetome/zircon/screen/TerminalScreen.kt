package org.codetome.zircon.screen

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextCharacter.Companion.DEFAULT_CHARACTER
import org.codetome.zircon.behavior.CursorHolder
import org.codetome.zircon.behavior.Layerable
import org.codetome.zircon.behavior.impl.DefaultLayerable
import org.codetome.zircon.builder.FontRendererBuilder
import org.codetome.zircon.font.FontRenderer
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputProvider
import org.codetome.zircon.input.InputType
import org.codetome.zircon.terminal.Size
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import java.util.*

/**
 * This class implements the logic defined in the [Screen] interface.
 * It keeps data structures for the front- and back buffers, the cursor location and
 * some other simpler states.
 */
class TerminalScreen private constructor(private val terminal: Terminal,
                                         private val layerable: Layerable)
    : Screen, CursorHolder by terminal,
        InputProvider by terminal,
        Layerable by layerable {

    internal constructor(terminal: Terminal) : this(terminal, DefaultLayerable(
            offset = Position(0, 0),
            size = terminal.getTerminalSize()))

    private var backBuffer: ScreenBuffer = ScreenBuffer(terminal.getTerminalSize(), DEFAULT_CHARACTER)
    private var frontBuffer: ScreenBuffer = ScreenBuffer(terminal.getTerminalSize(), DEFAULT_CHARACTER)

    init {
        this.terminal.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: Size) {
                resize()
            }
        })
    }

    override fun getTerminalSize() = terminal.getTerminalSize()

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
                val sourceImageSize = image.getSize()
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
        backBuffer = backBuffer.resize(getTerminalSize(), DEFAULT_CHARACTER)
        frontBuffer = frontBuffer.resize(getTerminalSize(), DEFAULT_CHARACTER)
    }

    @Synchronized
    private fun flipBuffers(forceRedraw: Boolean) {
        getTerminalSize().fetchPositions().forEach { position ->
            val backChar = backBuffer.getCharacterAt(position)
            // TODO:    add smart refresh for layers (`charDiffersInBuffers` should
            // TODO:    check z level intersections of layers)

            fetchBackgroundZIntersectionForPosition(position).forEach {
                drawCharacter(position, it) // FIXME: don't overwrite the char just draw
            }

            if (charDiffersInBuffers(backChar, position).or(forceRedraw)) {
                drawCharacter(position, backChar)
            }

            fetchOverlayZIntersectionForPosition(position).forEach {
                drawCharacter(position, it) // FIXME: don't overwrite the char just draw
            }
        }
        backBuffer.copyTo(frontBuffer)
        terminal.flush()
    }

    private fun drawCharacter(position: Position, character: TextCharacter) {
        terminal.setCursorPosition(position)
        terminal.resetColorsAndModifiers()
        terminal.setForegroundColor(character.getForegroundColor())
        terminal.setBackgroundColor(character.getBackgroundColor())
        character.getModifiers().forEach {
            terminal.enableModifier(it)
        }
        terminal.putCharacter(character.getCharacter())
    }

    private fun charDiffersInBuffers(backChar: TextCharacter, position: Position)
            = backChar != frontBuffer.getCharacterAt(position)

    private fun getCharacterFromBuffer(position: Position, buffer: ScreenBuffer): TextCharacter {
        return buffer.getCharacterAt(position)
    }
}
