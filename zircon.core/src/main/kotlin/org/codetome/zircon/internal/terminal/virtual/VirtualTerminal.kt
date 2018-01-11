package org.codetome.zircon.internal.terminal.virtual

import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.behavior.FontOverride
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.behavior.InternalCursorHandler
import org.codetome.zircon.internal.behavior.InternalLayerable
import org.codetome.zircon.internal.behavior.impl.DefaultCursorHandler
import org.codetome.zircon.internal.behavior.impl.DefaultFontOverride
import org.codetome.zircon.internal.behavior.impl.DefaultLayerable
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.terminal.AbstractTerminal
import org.codetome.zircon.internal.terminal.InternalTerminal
import java.util.function.Consumer

class VirtualTerminal(initialSize: Size = Size.DEFAULT_TERMINAL_SIZE,
                      initialFont: Font,
                      private val fontOverride: FontOverride = DefaultFontOverride(
                              initialFont = initialFont),
                      private val cursorHandler: InternalCursorHandler = DefaultCursorHandler(
                              cursorSpace = initialSize),
                      private val layerable: InternalLayerable = DefaultLayerable(
                              size = initialSize,
                              supportedFontSize = initialFont.getSize()))
    : AbstractTerminal(), InternalTerminal,
        InternalCursorHandler by cursorHandler,
        InternalLayerable by layerable,
        FontOverride by fontOverride {

    private var terminalSize = initialSize
    private var backend: TextImage = createBackend(terminalSize)


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
        drawable.getBoundableSize().fetchPositions().forEach {
            setPositionDirty(it + offset)
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

    override fun onInput(listener: Consumer<Input>) {
        EventBus.subscribe<Input>(EventType.Input, { (input) ->
            listener.accept(input)
        })
    }

    @Synchronized
    override fun clear() {
        backend = createBackend(terminalSize)
        terminalSize.fetchPositions().forEach {
            setPositionDirty(it)
        }
        putCursorAt(Position.DEFAULT_POSITION)
    }

    @Synchronized
    override fun putCharacter(c: Char) {
        putTextCharacter(TextCharacterBuilder.newBuilder()
                .character(c)
                .foregroundColor(getForegroundColor())
                .backgroundColor(getBackgroundColor())
                .modifiers(getActiveModifiers())
                .build())
    }

    @Synchronized
    override fun putTextCharacter(tc: TextCharacter) {
        if (tc.getCharacter() == '\n') {
            moveCursorToNextLine()
        } else if (TextUtils.isPrintableCharacter(tc.getCharacter())) {
            backend.setCharacterAt(getCursorPosition(), tc)
            setPositionDirty(getCursorPosition())
            moveCursorForward()
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
            if (char.isPresent) {
                fn(Cell(pos, char.get()))
            }
        }
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
