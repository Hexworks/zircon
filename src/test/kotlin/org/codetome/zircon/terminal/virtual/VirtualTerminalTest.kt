package org.codetome.zircon.terminal.virtual

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Modifier
import org.codetome.zircon.Position
import org.codetome.zircon.Position.Companion.DEFAULT_POSITION
import org.codetome.zircon.Position.Companion.OFFSET_1x1
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.input.KeyStroke.Companion.EOF_STROKE
import org.codetome.zircon.Cell
import org.codetome.zircon.terminal.Terminal
import org.codetome.zircon.terminal.TerminalResizeListener
import org.codetome.zircon.Size
import org.codetome.zircon.api.TextCharacterBuilder
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class VirtualTerminalTest {

    lateinit var target: VirtualTerminal

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = VirtualTerminal(SIZE)
    }

    @Test
    fun shouldCapCursorColumnsWhenSetToBiggerThanTerminalSize() {
        target.setCursorPosition(DEFAULT_POSITION.withRelativeColumn(Int.MAX_VALUE))
        assertThat(target.getCursorPosition())
                .isEqualTo(DEFAULT_POSITION.withColumn(target.getBoundableSize().columns))
    }

    @Test
    fun shouldCapCursorRowsWhenSetToBiggerThanTerminalSize() {
        target.setCursorPosition(DEFAULT_POSITION.withRelativeRow(Int.MAX_VALUE))
        assertThat(target.getCursorPosition())
                .isEqualTo(DEFAULT_POSITION.withRow(target.getBoundableSize().rows - 1))
    }

    @Test
    fun shouldHaveProperSizeWhenGetSizeIsCalled() {
        assertThat(target.getBoundableSize()).isEqualTo(SIZE)
    }

    @Test
    fun shouldSetSizeCorrectlyWhenSetTerminalSizeIsCalled() {
        target.setSize(NEW_BIGGER_SIZE)
        assertThat(target.getBoundableSize()).isEqualTo(NEW_BIGGER_SIZE)
    }

    @Test
    fun shouldNotifyResizeListenersWhenResizeHappens() {
        var resized = false
        target.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: Size) {
                resized = true
            }
        })
        target.setSize(NEW_BIGGER_SIZE)
        assertThat(resized).isTrue()
    }

    @Test
    fun shouldResetCursorWhenColsAreLessAfterResize() {
        target.setCursorPosition(DEFAULT_POSITION.withColumn(Int.MAX_VALUE))
        val originalCursorPos = target.getCursorPosition()
        target.setSize(NEW_LESS_COLS_SIZE)
        assertThat(target.getCursorPosition()).isEqualTo(originalCursorPos.withRelativeColumn(-1))
    }

    @Test
    fun shouldResetCursorWhenRowsAreLessAfterResize() {
        target.setCursorPosition(DEFAULT_POSITION.withRow(Int.MAX_VALUE))
        val originalCursorPos = target.getCursorPosition()
        target.setSize(NEW_LESS_ROWS_SIZE)
        assertThat(target.getCursorPosition()).isEqualTo(originalCursorPos.withRelativeRow(-1))
    }

    @Test
    fun cursorShouldBeVisibleWhenSetVisibleIsCalledWithTrue() {
        target.setCursorVisible(true)
        assertThat(target.isCursorVisible()).isTrue()
    }

    @Test
    fun shouldProperlySetCursorPositionWhenSetCursorPositionIsCalled() {
        val pos = Position(4, 5)
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
        target.putCharacter(' ')

        target.clear()

        target.forEachCell { cellCount++ }

        assertThat(cellCount).isEqualTo(0)
        assertThat(target.getCursorPosition()).isEqualTo(Position.DEFAULT_POSITION)
    }

    @Test
    fun shouldMoveCursorToNextLineWhenNewLineIsPut() {
        target.putCharacter('\n')
        assertThat(target.getCursorPosition())
                .isEqualTo(Position.DEFAULT_POSITION.withRelativeRow(1))
    }

    @Test
    fun shouldPutCharacterWhenPutCharacterIsCalled() {
        val tc = TextCharacter.builder()
                .character('a')
                .build()
        target.putCharacter('a')
        assertThat(target.getCharacterAt(DEFAULT_POSITION).get())
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
    fun shouldBeDirtyAfterResize() {
        target.putCharacter(' ')
        target.forEachDirtyCell {  }
        target.setSize(SIZE.withRelativeColumns(1))

        val dirtyCells = mutableListOf<Cell>()

        target.forEachDirtyCell {
            dirtyCells.add(it)
        }

        assertThat(dirtyCells).hasSize(2)
    }

    @Test
    fun shouldReportDirtyWhenDirty() {
        assertThat(target.isDirty()).isTrue()
    }

    @Test
    fun shouldBeAbleToSetCharacter() {
        val expectedChar = TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter('x')
        target.setCharacterAt(OFFSET_1x1, expectedChar)

        assertThat(target.getCharacterAt(OFFSET_1x1).get())
                .isEqualTo(expectedChar)
    }

    @Test
    fun shouldStartInNewLineWhenAddingTextAtTheEndOfTheLine() {
        target.setCursorPosition(DEFAULT_POSITION.withRelativeColumn(Int.MAX_VALUE))
        target.putCharacter('a')
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)).get())
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
    fun shouldReturnEmptyInputWhenNoneIsPresent() {
        val result = target.pollInput()
        assertThat(result.isPresent).isFalse()
    }

    @Test
    fun shouldReturnNonEmptyInputWhenInputIsAdded() {
        target.addInput(EOF_STROKE)
        assertThat(target.pollInput().get()).isEqualTo(EOF_STROKE)
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
        val SIZE = Size(10, 20)
        val NEW_BIGGER_SIZE = Size(30, 40)
        val NEW_LESS_ROWS_SIZE = SIZE.withRelativeRows(-1)
        val NEW_LESS_COLS_SIZE = SIZE.withRelativeColumns(-1)
    }
}