package org.codetome.zircon.internal.terminal.virtual

import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.CursorHandler
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.behavior.impl.DefaultCursorHandler
import org.codetome.zircon.internal.behavior.impl.DefaultLayerable
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.terminal.AbstractTerminal
import org.codetome.zircon.internal.terminal.IterableTerminal
import java.util.function.Consumer

class VirtualTerminal private constructor(initialSize: Size,
                                          private val cursorHandler: CursorHandler,
                                          private val layerable: Layerable)
    : AbstractTerminal(), IterableTerminal,
        CursorHandler by cursorHandler,
        Layerable by layerable {

    private var terminalSize = initialSize
    private var backend = createBackend(terminalSize)

    constructor(initialSize: Size = Size.DEFAULT)
            : this(
            initialSize = initialSize,
            cursorHandler = DefaultCursorHandler(
                    cursorSpace = initialSize),
            layerable = DefaultLayerable(
                    size = initialSize))

    override fun drainDirtyPositions() =
            cursorHandler.drainDirtyPositions().plus(layerable.drainDirtyPositions()).also { dirtyPositions ->
                val blinkingChars = dirtyPositions.filter {
                    getCharacterAt(it).let { char ->
                        char.isPresent && char.get().isBlinking() // TODO: check layers too
                    }
                }
                blinkingChars.forEach {
                    setPositionDirty(it)
                }
            }

    override fun isDirty() = cursorHandler.isDirty().or(layerable.isDirty())

    override fun setPositionDirty(position: Position) {
        layerable.setPositionDirty(position)
    }

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
            super.onResized(newSize)
        }
    }

    override fun addInputListener(listener: Consumer<Input>) {
        EventBus.subscribe<Input>(EventType.Input, { (input) ->
            listener.accept(input)
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
    override fun flush() {

    }

    override fun close() {
        EventBus.emit(EventType.Input, KeyStroke.EOF_STROKE)
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
