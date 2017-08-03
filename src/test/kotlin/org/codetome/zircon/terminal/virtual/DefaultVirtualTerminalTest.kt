package org.codetome.zircon.terminal.virtual

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Modifier
import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TerminalPosition.Companion.DEFAULT_POSITION
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.input.KeyStroke.Companion.EOF_STROKE
import org.codetome.zircon.terminal.Cell
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import org.codetome.zircon.terminal.TerminalSize
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class DefaultVirtualTerminalTest {

    lateinit var target: DefaultVirtualTerminal

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = DefaultVirtualTerminal(SIZE)
    }

    @Test
    fun shouldCapCursorColumnsWhenSetToBiggerThanTerminalSize() {
        target.setCursorPosition(DEFAULT_POSITION.withRelativeColumn(Int.MAX_VALUE))
        assertThat(target.getCursorPosition())
                .isEqualTo(DEFAULT_POSITION.withColumn(target.getTerminalSize().columns))
    }

    @Test
    fun shouldCapCursorRowsWhenSetToBiggerThanTerminalSize() {
        target.setCursorPosition(DEFAULT_POSITION.withRelativeRow(Int.MAX_VALUE))
        assertThat(target.getCursorPosition())
                .isEqualTo(DEFAULT_POSITION.withRow(target.getTerminalSize().rows - 1))
    }

    @Test
    fun shouldHaveProperSizeWhenGetSizeIsCalled() {
        assertThat(target.getTerminalSize()).isEqualTo(SIZE)
    }

    @Test
    fun shouldSetSizeCorrectlyWhenSetTerminalSizeIsCalled() {
        target.setTerminalSize(NEW_BIGGER_SIZE)
        assertThat(target.getTerminalSize()).isEqualTo(NEW_BIGGER_SIZE)
    }

    @Test
    fun shouldNotifyResizeListenersWhenResizeHappens() {
        var resized = false
        target.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: TerminalSize) {
                resized = true
            }
        })
        target.setTerminalSize(NEW_BIGGER_SIZE)
        assertThat(resized).isTrue()
    }

    @Test
    fun shouldResetCursorWhenColsAreLessAfterResize() {
        target.setCursorPosition(DEFAULT_POSITION.withColumn(Int.MAX_VALUE))
        val originalCursorPos = target.getCursorPosition()
        target.setTerminalSize(NEW_LESS_COLS_SIZE)
        assertThat(target.getCursorPosition()).isEqualTo(originalCursorPos.withRelativeColumn(-1))
    }

    @Test
    fun shouldResetCursorWhenRowsAreLessAfterResize() {
        target.setCursorPosition(DEFAULT_POSITION.withRow(Int.MAX_VALUE))
        val originalCursorPos = target.getCursorPosition()
        target.setTerminalSize(NEW_LESS_ROWS_SIZE)
        assertThat(target.getCursorPosition()).isEqualTo(originalCursorPos.withRelativeRow(-1))
    }

    @Test
    fun cursorShouldBeVisibleWhenSetVisibleIsCalledWithTrue() {
        target.setCursorVisible(true)
        assertThat(target.isCursorVisible()).isTrue()
    }

    @Test
    fun shouldProperlySetCursorPositionWhenSetCursorPositionIsCalled() {
        val pos = TerminalPosition(4, 5)
        target.setCursorPosition(pos)

        assertThat(target.getCursorPosition()).isEqualTo(pos)
    }

    @Test
    fun shouldEnableModifiersWhenEnableModifiersIsCalled() {
        val modifiers = setOf(Modifier.BLINK, Modifier.BOLD)
        target.enableModifiers(*modifiers.toTypedArray())

        assertThat(target.getActiveModifiers()).containsExactlyInAnyOrder(*modifiers.toTypedArray())
    }

    @Test
    fun shouldClearScreenWhenClearScreenIsCalled() {
        var cellCount = 0
        target.putCharacter(TextCharacter.builder().build())

        target.clearScreen()

        target.forEachCell { cellCount++ }

        assertThat(cellCount).isEqualTo(0)
        assertThat(target.getCursorPosition()).isEqualTo(TerminalPosition.DEFAULT_POSITION)
    }

    @Test
    fun shouldMoveCursorToNextLineWhenNewLineIsPut() {
        target.putCharacter('\n')
        assertThat(target.getCursorPosition())
                .isEqualTo(TerminalPosition.DEFAULT_POSITION.withRelativeRow(1))
    }

    @Test
    fun shouldPutCharacterWhenPutCharacterIsCalled() {
        val tc = TextCharacter.builder()
                .character('a')
                .build()
        target.putCharacter(tc)
        assertThat(target.getCharacter(DEFAULT_POSITION))
                .isEqualTo(tc)
    }

    @Test
    fun shouldNotPutWhenNonPrintableCharacterIsPut() {
        target.putCharacter(1.toChar())
        var count = 0
        target.forEachDirtyCell { count++ }
        assertThat(count).isEqualTo(1) // the cursor
    }

    @Test
    fun shouldContainProperDirtyCellsWhenPutCharIsCalled() {
        val dirtyCells = addCharAndFetchDirtyCells('a')
        assertThat(dirtyCells).containsExactly(
                Cell(
                        position = DEFAULT_POSITION,
                        character = TextCharacter.builder()
                                .character('a')
                                .build()),
                Cell(
                        position = DEFAULT_POSITION.withRelativeColumn(1),
                        character = TextCharacter.builder()
                                .character(' ')
                                .build())
        )
    }

    @Test
    fun shouldStartInNewLineWhenAddingTextAtTheEndOfTheLine() {
        target.setCursorPosition(DEFAULT_POSITION.withRelativeColumn(Int.MAX_VALUE))
        target.putCharacter('a')
        assertThat(target.getCharacter(DEFAULT_POSITION.withRelativeRow(1)))
                .isEqualTo(TextCharacter.builder().character('a').build())
    }

    @Test
    fun shouldSetCursorPositionToNewLineWhenWritingAtTheEndOfTheLine() {
        target.setCursorPosition(DEFAULT_POSITION.withRelativeColumn(Int.MAX_VALUE))
        target.setCursorPosition(target.getCursorPosition().withRelativeColumn(-1))
        target.putCharacter('a')

        assertThat(target.getCursorPosition())
                .isEqualTo(DEFAULT_POSITION.withRelativeRow(1))
    }

    @Test
    fun shouldBeNotifiedOfCloseWhenCloseIsCalled() {
        var closed = false
        target.addVirtualTerminalListener(object : VirtualTerminalListener {
            override fun onClose() {
                closed = true
            }
        })
        target.close()
        assertThat(closed).isTrue()
        val input = target.pollInput()
        assertThat(input.isPresent).isTrue()
        assertThat(input.get()).isEqualTo(EOF_STROKE)
    }

    @Test
    fun shouldBeNotifiedOfFlushWhenFlushIsCalled() {
        var flushed = false
        target.addVirtualTerminalListener(object : VirtualTerminalListener {
            override fun onFlush() {
                flushed = true
            }
        })
        target.flush()
        assertThat(flushed).isTrue()
    }

    @Test
    fun shouldReturnEmptyInputWhenNoneIsPresent() {
        val result = target.pollInput()
        assertThat(result.isPresent).isFalse()
    }

    @Test
    fun shouldReturnNonEmptyInputWhenInputIsAdded() {
        target.addInput(EOF_STROKE)
        assertThat(target.pollInput().get()).isEqualTo(EOF_STROKE)
    }

    @Test
    fun shouldNotBeNotifiedWhenListenerIsAddedThenRemoved() {
        var closed = false
        val listener = object : VirtualTerminalListener {
            override fun onClose() {
                closed = true
            }
        }
        target.addVirtualTerminalListener(listener)
        target.removeVirtualTerminalListener(listener)
        target.close()

        assertThat(closed).isFalse()
    }

    @Test
    fun shouldAddCharsWhenTextGraphicsIsCreatedAndManipulated() {
        val char = 'a'
        val graphics = target.newTextGraphics()
        graphics.putString(DEFAULT_POSITION, char.toString())
        assertThat(target.getCharacter(DEFAULT_POSITION)).isEqualTo(TextCharacter.builder()
                .character(char)
                .build())
    }

    @Test
    fun shouldNotAddCharsWhenTextGraphicsIsCreatedAndCharIsPutOutOfBounds() {
        val char = 'a'
        val graphics = target.newTextGraphics()
        val cells = mutableListOf<Cell>()
        graphics.putString(DEFAULT_POSITION.withRow(Int.MAX_VALUE), char.toString())
        target.forEachCell {
            cells.add(it)
        }
        assertThat(cells).isEmpty()
    }

    private fun addCharAndFetchDirtyCells(char: Char): MutableList<Cell> {
        val dirtyCells = mutableListOf<Cell>()
        target.putCharacter(char)
        target.forEachDirtyCell {
            dirtyCells.add(it)
        }
        return dirtyCells
    }

    companion object {
        val SIZE = TerminalSize(10, 20)
        val NEW_BIGGER_SIZE = TerminalSize(30, 40)
        val NEW_LESS_ROWS_SIZE = SIZE.withRelativeRows(-1)
        val NEW_LESS_COLS_SIZE = SIZE.withRelativeColumns(-1)
    }
}