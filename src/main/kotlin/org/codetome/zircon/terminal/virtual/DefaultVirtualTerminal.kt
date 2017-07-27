package org.codetome.zircon.terminal.virtual

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextUtils
import org.codetome.zircon.input.Input
import org.codetome.zircon.input.InputType
import org.codetome.zircon.input.KeyStroke
import org.codetome.zircon.screen.TabBehavior
import org.codetome.zircon.terminal.AbstractTerminal
import org.codetome.zircon.terminal.TerminalSize
import org.codetome.zircon.terminal.virtual.VirtualTerminal.BufferLine
import org.codetome.zircon.terminal.virtual.VirtualTerminal.BufferWalker
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class DefaultVirtualTerminal(initialTerminalSize: TerminalSize = TerminalSize.DEFAULT)
    : AbstractTerminal(), VirtualTerminal {

    private val cursorPosition = AtomicReference(TerminalPosition.DEFAULT_POSITION)
    private val cursorVisible = AtomicBoolean(true)
    private val terminalSize = AtomicReference(initialTerminalSize)
    private val wholeBufferDirty = AtomicBoolean(false)
    private val textCharacterBuffer: TextCharacterBuffer = TextCharacterBuffer()
    private val dirtyTerminalCells = TreeSet<TerminalPosition>()
    private val listeners = mutableListOf<VirtualTerminalListener>()
    private val inputQueue = LinkedBlockingQueue<Input>()

    override fun isCursorVisible() = cursorVisible.get()

    override fun setCursorVisible(cursorVisible: Boolean) {
        this.cursorVisible.set(cursorVisible)
    }

    override fun getTerminalSize(): TerminalSize = terminalSize.get()

    @Synchronized
    override fun setTerminalSize(newSize: TerminalSize) {
        this.terminalSize.set(newSize)
        cursorPosition.get().let {
            if (it.row >= newSize.rows || it.column >= newSize.columns) {
                setCursorPosition(TerminalPosition.DEFAULT_POSITION)
            }
        }
        listeners.forEach { it.onResized(this, terminalSize.get()) }
        super.onResized(newSize)
    }

    @Synchronized
    override fun clearScreen() {
        textCharacterBuffer.clear()
        setWholeBufferDirty()
        setCursorPosition(TerminalPosition.DEFAULT_POSITION)
    }

    @Synchronized
    override fun getCursorPosition(): TerminalPosition = cursorPosition.get()

    @Synchronized
    override fun setCursorPosition(cursorPosition: TerminalPosition) {
        this.cursorPosition.set(cursorPosition)
        dirtyTerminalCells.add(this.cursorPosition.get())
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
            val nrOfSpaces = TabBehavior.DEFAULT_TAB_BEHAVIOR.getTabReplacement(cursorPosition.get().column).length
            var i = 0
            while (i < nrOfSpaces && cursorPosition.get().column < terminalSize.get().columns - 1) {
                putCharacter(textCharacter.withCharacter(' '))
                i++
            }
        } else {
            textCharacterBuffer.setCharacter(cursorPosition.get(), textCharacter)
            if (wholeBufferDirty.get().not()) {
                dirtyTerminalCells.add(cursorPosition.get())
                if (moreThanThresholdPercentIsDirty()) {
                    setWholeBufferDirty()
                }
            }
            setCursorPosition(cursorPosition.get().withRelativeColumn(1)) // TODO: extract cursor logic?
            if (cursorIsAtTheEndOfTheLine()) {
                moveCursorToNextLine()
            }
            dirtyTerminalCells.add(TerminalPosition(cursorPosition.get().column, cursorPosition.get().row))
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
    override fun getAndResetDirtyCells(): TreeSet<TerminalPosition> {
        val copy = TreeSet<TerminalPosition>(dirtyTerminalCells)
        dirtyTerminalCells.clear()
        return copy
    }

    @Synchronized
    override fun isWholeBufferDirtyThenReset(): Boolean {
        val oldVal = wholeBufferDirty.get()
        wholeBufferDirty.set(false)
        return oldVal
    }

    @Synchronized
    override fun getCharacter(position: TerminalPosition): TextCharacter {
        return textCharacterBuffer.getCharacter(position)
    }

    @Synchronized
    override fun forEachLine(startRow: Int, endRow: Int, bufferWalker: BufferWalker) {
        val emptyLine: BufferLine = object : BufferLine {
            override fun getCharacterAt(column: Int): TextCharacter {
                return TextCharacter.DEFAULT_CHARACTER
            }
        }
        val iterator = textCharacterBuffer.getLinesFrom(startRow)
        (startRow..endRow).forEach { row ->
            var bufferLine = emptyLine
            if (iterator.hasNext()) {
                val list = iterator.next()
                bufferLine = object : BufferLine {
                    override fun getCharacterAt(column: Int): TextCharacter {
                        if (column >= list.size) {
                            return TextCharacter.DEFAULT_CHARACTER
                        }
                        return list[column]
                    }
                }
            }
            bufferWalker.onLine(row, bufferLine)
        }
    }

    private fun moveCursorToNextLine() {
        cursorPosition.set(cursorPosition.get().withColumn(0).withRelativeRow(1))
        if (cursorPosition.get().row >= textCharacterBuffer.getLineCount()) {
            textCharacterBuffer.newLine()
        }
    }

    private fun setWholeBufferDirty() {
        wholeBufferDirty.set(true)
        dirtyTerminalCells.clear()
    }

    private fun moreThanThresholdPercentIsDirty() = dirtyTerminalCells.size > terminalSize.get().columns * terminalSize.get().rows * ALL_DIRTY_THRESHOLD

    private fun cursorIsAtTheEndOfTheLine() = cursorPosition.get().column == terminalSize.get().columns

    companion object {
        private val ALL_DIRTY_THRESHOLD = 0.9
    }
}
