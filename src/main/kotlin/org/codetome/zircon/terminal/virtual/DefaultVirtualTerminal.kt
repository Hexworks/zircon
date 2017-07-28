package org.codetome.zircon.terminal.virtual

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextUtils
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputType
import org.codetome.zircon.input.KeyStroke
import org.codetome.zircon.screen.TabBehavior
import org.codetome.zircon.terminal.AbstractTerminal
import org.codetome.zircon.terminal.Cell
import org.codetome.zircon.terminal.TerminalSize
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class DefaultVirtualTerminal(initialTerminalSize: TerminalSize = TerminalSize.DEFAULT)
    : AbstractTerminal(), VirtualTerminal {

    private var cursorPosition = TerminalPosition.DEFAULT_POSITION
    private var cursorVisible = true
    private var terminalSize = initialTerminalSize
    private var wholeBufferDirty = false
    private var lastDrawnCursorPosition: TerminalPosition = TerminalPosition.UNKNOWN
    private val buffer: TextCharacterBuffer = TextCharacterBuffer()
    private val dirtyTerminalCells = TreeSet<TerminalPosition>()
    private val listeners = mutableListOf<VirtualTerminalListener>()
    private val inputQueue = LinkedBlockingQueue<Input>()

    init {
        dirtyTerminalCells.add(cursorPosition)
    }

    override fun isCursorVisible() = cursorVisible

    override fun setCursorVisible(cursorVisible: Boolean) {
        this.cursorVisible = cursorVisible
    }

    override fun getTerminalSize(): TerminalSize = terminalSize

    @Synchronized
    override fun setTerminalSize(newSize: TerminalSize) {
        if (newSize != terminalSize) {
            this.terminalSize = newSize
            buffer.resize(newSize)
            cursorPosition.let { (cursorCol, cursorRow) ->
                if (cursorRow >= newSize.rows || cursorCol >= newSize.columns) {
                    setCursorPosition(TerminalPosition(
                            column = Math.min(newSize.columns, cursorCol),
                            row = Math.min(newSize.rows, cursorRow)))
                }
            }
            wholeBufferDirty = true // TODO: this can be optimized later
            listeners.forEach { it.onResized(this, terminalSize) }
            super.onResized(newSize)
        }
    }

    @Synchronized
    override fun clearScreen() {
        buffer.clear()
        setWholeBufferDirty()
        setCursorPosition(TerminalPosition.DEFAULT_POSITION)
    }

    @Synchronized
    override fun getCursorPosition(): TerminalPosition = cursorPosition

    @Synchronized
    override fun setCursorPosition(cursorPosition: TerminalPosition) {
        this.cursorPosition = cursorPosition
        dirtyTerminalCells.add(this.cursorPosition)
    }

    @Synchronized
    override fun putCharacter(c: Char) {
        if (c == '\n') {
            moveCursorToNextLine()
        } else if (TextUtils.isPrintableCharacter(c)) {
            putCharacter(TextCharacter(
                    character = c,
                    foregroundColor = getForegroundColor(),
                    backgroundColor = getBackgroundColor(),
                    modifiersToUse = getActiveModifiers()))
        }
    }

    @Synchronized
    internal fun putCharacter(textCharacter: TextCharacter) {
        if (textCharacter.getCharacter() == '\t') {
            val nrOfSpaces = TabBehavior.DEFAULT_TAB_BEHAVIOR.getTabReplacement(cursorPosition.column).length
            var i = 0
            while (i < nrOfSpaces && cursorPosition.column < terminalSize.columns - 1) {
                putCharacter(textCharacter.withCharacter(' '))
                i++
            }
        } else {
            buffer.setCharacter(cursorPosition, textCharacter)
            dirtyTerminalCells.add(cursorPosition)
            setCursorPosition(cursorPosition.withRelativeColumn(1)) // TODO: extract cursor logic?
            if (cursorIsAtTheEndOfTheLine()) {
                moveCursorToNextLine()
            }
            dirtyTerminalCells.add(cursorPosition)
        }
    }

    @Synchronized
    override fun flush() = listeners.forEach { it.onFlush() }

    override fun close() {
        inputQueue.add(KeyStroke(character = ' ', it = InputType.EOF))
        listeners.forEach { it.onClose() }
    }

    @Synchronized
    override fun pollInput() = Optional.ofNullable(inputQueue.poll())

    override fun newTextGraphics() = VirtualTerminalTextGraphics(this)

    @Synchronized
    override fun addVirtualTerminalListener(listener: VirtualTerminalListener) {
        listeners.add(listener)
    }

    @Synchronized
    override fun removeVirtualTerminalListener(listener: VirtualTerminalListener) {
        listeners.remove(listener)
    }

    override fun addInput(input: Input) {
        inputQueue.add(input)
    }

    @Synchronized
    override fun getCharacter(position: TerminalPosition): TextCharacter {
        return buffer.getCharacter(position)
    }

    override fun forEachDirtyCell(fn: (Cell) -> Unit) {
        if (lastDrawnCursorPosition != getCursorPosition()) {
            dirtyTerminalCells.add(lastDrawnCursorPosition)
        }
        if (wholeBufferDirty) {
            buffer.forEachCell(fn)
            fn(Cell(cursorPosition, buffer.getCharacter(cursorPosition)))
            wholeBufferDirty = false
        } else {
            dirtyTerminalCells.forEach { pos ->
                fn(Cell(pos, buffer.getCharacter(pos)))
            }
        }
        val blinkingChars = dirtyTerminalCells.filter { getCharacter(it).isBlinking() }
        dirtyTerminalCells.clear()
        dirtyTerminalCells.addAll(blinkingChars)
        this.lastDrawnCursorPosition = getCursorPosition()
        dirtyTerminalCells.add(cursorPosition)
    }

    override fun forEachCell(fn: (Cell) -> Unit) {
        buffer.forEachCell(fn)
    }

    private fun moveCursorToNextLine() {
        cursorPosition = cursorPosition.withColumn(0).withRelativeRow(1)
        if (cursorPosition.row >= buffer.getLineCount()) {
            buffer.newLine()
        }
    }

    private fun setWholeBufferDirty() {
        wholeBufferDirty = true
        dirtyTerminalCells.clear()
    }

    private fun cursorIsAtTheEndOfTheLine() = cursorPosition.column == terminalSize.columns
}
