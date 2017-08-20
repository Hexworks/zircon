package org.codetome.zircon.terminal.virtual

import org.codetome.zircon.Cell
import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.behavior.CursorHandler
import org.codetome.zircon.behavior.Drawable
import org.codetome.zircon.behavior.Layerable
import org.codetome.zircon.behavior.impl.DefaultCursorHandler
import org.codetome.zircon.behavior.impl.DefaultLayerable
import org.codetome.zircon.event.EventBus
import org.codetome.zircon.event.EventType
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.KeyStroke
import org.codetome.zircon.terminal.AbstractTerminal
import org.codetome.zircon.terminal.IterableTerminal
import org.codetome.zircon.util.TextUtils
import java.util.function.Consumer

class VirtualTerminal private constructor(initialSize: Size,
                                          private val cursorHandler: CursorHandler,
                                          private val layerable: Layerable)
    : AbstractTerminal(), IterableTerminal,
        CursorHandler by cursorHandler,
        Layerable by layerable {

    private var terminalSize = initialSize
    private var backend = createBackend(terminalSize)
    private val listeners = mutableListOf<VirtualTerminalListener>()

    constructor(initialSize: Size = Size.DEFAULT)
            : this(
            initialSize = initialSize,
            cursorHandler = DefaultCursorHandler(
                    cursorSpace = initialSize),
            layerable = DefaultLayerable(
                    size = initialSize))

    @Synchronized
    override fun draw(drawable: Drawable, offset: Position) {
        drawable.drawOnto(this, offset)
        // TODO: this can be optimized later
        terminalSize.fetchPositions().forEach {
            setPositionDirty(it)
        }
    }

    override fun getBoundableSize(): Size = terminalSize

    @Synchronized
    override fun setSize(newSize: Size) {
        if (newSize != terminalSize) {
            this.terminalSize = newSize
            backend = backend.resize(newSize, TextCharacterBuilder.DEFAULT_CHARACTER)
            resizeCursorSpace(newSize)
            // TODO: this can be optimized later
            terminalSize.fetchPositions().forEach {
                setPositionDirty(it)
            }
            listeners.forEach { it.onResized(this, terminalSize) }
            super.onResized(newSize)
        }
    }

    override fun subscribe(inputCallback: Consumer<Input>) {
        EventBus.subscribe<Input>(EventType.INPUT, { (input) ->
            inputCallback.accept(input)
        })
    }

    @Synchronized
    override fun clear() {
        backend = createBackend(terminalSize)
        // TODO: this can be optimized later
        terminalSize.fetchPositions().forEach {
            setPositionDirty(it)
        }
        putCursorAt(Position.DEFAULT_POSITION)
    }

    @Synchronized
    override fun putCharacter(c: Char) {
        if (c == '\n') {
            moveCursorToNextLine()
        } else if (TextUtils.isPrintableCharacter(c)) {
            putCharacter(TextCharacterBuilder.newBuilder()
                    .character(c)
                    .foregroundColor(getForegroundColor())
                    .backgroundColor(getBackgroundColor())
                    .modifiers(getActiveModifiers())
                    .build())
        }
    }

    @Synchronized
    override fun flush() = listeners.forEach { it.onFlush() }

    override fun close() {
        EventBus.emit(EventType.INPUT, KeyStroke.EOF_STROKE)
        listeners.forEach { it.onClose() }
    }

    @Synchronized
    override fun getCharacterAt(position: Position) =
            backend.getCharacterAt(position)

    @Synchronized
    override fun setCharacterAt(position: Position, character: Char) =
            setCharacterAt(position, TextCharacterBuilder.newBuilder()
                    .character(character)
                    .styleSet(toStyleSet())
                    .build())

    @Synchronized
    override fun setCharacterAt(position: Position, character: TextCharacter) =
            if (containsPosition(position)) {
                backend.setCharacterAt(position, character)
                setPositionDirty(position)
                true
            } else {
                false
            }

    @Synchronized
    override fun forEachDirtyCell(fn: (Cell) -> Unit) {
        val dirtyPositions = drainDirtyPositions()
        dirtyPositions.forEach { pos ->
            val char = backend.getCharacterAt(pos)
            if (char.isPresent.not()) {
                println("pos: $pos, backend size: ${backend.getBoundableSize()}")
            } else {
                fn(Cell(pos, char.get()))
            }
        }
        val blinkingChars = dirtyPositions.filter {
            getCharacterAt(it).let { char ->
                char.isPresent && char.get().isBlinking()
            }
        }
        blinkingChars.forEach {
            setPositionDirty(it)
        }
    }

    override fun forEachCell(fn: (Cell) -> Unit) {
        terminalSize.fetchPositions().forEach {
            fn(Cell(it, getCharacterAt(it).get()))
        }
    }

    @Synchronized
    private fun putCharacter(textCharacter: TextCharacter) {
        backend.setCharacterAt(getCursorPosition(), textCharacter)
        setPositionDirty(getCursorPosition())
        advanceCursor()
    }

    private fun moveCursorToNextLine() {
        putCursorAt(getCursorPosition().withRelativeRow(1).withColumn(0))
    }

    private fun createBackend(initialSize: Size) =
            TextImageBuilder.newBuilder()
                    .size(initialSize)
                    .filler(TextCharacterBuilder.DEFAULT_CHARACTER)
                    .build()

}
