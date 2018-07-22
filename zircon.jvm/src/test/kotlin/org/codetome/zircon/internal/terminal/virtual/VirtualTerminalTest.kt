package org.codetome.zircon.internal.terminal.virtual

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.builder.TextImageBuilder
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.TerminalResizeListener
import org.codetome.zircon.internal.component.impl.DefaultLabelTest
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.font.impl.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.TestFontLoader
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.atomic.AtomicReference

class VirtualTerminalTest {

    lateinit var target: VirtualTerminal
    lateinit var font: Font

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(TestFontLoader())
        font = DefaultLabelTest.FONT.toFont()
        MockitoAnnotations.initMocks(this)
        target = VirtualTerminal(
                initialSize = SIZE,
                initialFont = font)
    }

    @Test
    fun shouldCapCursorColumnsWhenSetToBiggerThanTerminalSize() {
        target.putCursorAt(Position.defaultPosition().withRelativeX(Int.MAX_VALUE))
        assertThat(target.getCursorPosition())
                .isEqualTo(Position.defaultPosition().withX(target.getBoundableSize().xLength - 1))
    }

    @Test
    fun shouldCapCursorRowsWhenSetToBiggerThanTerminalSize() {
        target.putCursorAt(Position.defaultPosition().withRelativeY(Int.MAX_VALUE))
        assertThat(target.getCursorPosition())
                .isEqualTo(Position.defaultPosition().withY(target.getBoundableSize().yLength - 1))
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
        target.putCursorAt(Position.defaultPosition().withX(Int.MAX_VALUE))
        val originalCursorPos = target.getCursorPosition()
        target.setSize(NEW_LESS_COLS_SIZE)
        assertThat(target.getCursorPosition()).isEqualTo(originalCursorPos.withX(NEW_LESS_COLS_SIZE.xLength - 1))
    }

    @Test
    fun shouldResetCursorWhenRowsAreLessAfterResize() {
        target.putCursorAt(Position.defaultPosition().withY(Int.MAX_VALUE))
        val originalCursorPos = target.getCursorPosition()
        target.setSize(NEW_LESS_ROWS_SIZE)
        assertThat(target.getCursorPosition()).isEqualTo(originalCursorPos.withRelativeY(-1))
    }

    @Test
    fun cursorShouldBeVisibleWhenSetVisibleIsCalledWithTrue() {
        target.setCursorVisibility(true)
        assertThat(target.isCursorVisible()).isTrue()
    }

    @Test
    fun shouldProperlySetCursorPositionWhenSetCursorPositionIsCalled() {
        val pos = Position.create(4, 5)
        target.putCursorAt(pos)

        assertThat(target.getCursorPosition()).isEqualTo(pos)
    }

    @Test
    fun shouldEnableModifiersWhenEnableModifiersIsCalled() {
        target.enableModifiers(setOf(Modifiers.BLINK, Modifiers.BOLD))

        assertThat(target.getActiveModifiers()).containsExactlyInAnyOrder(Modifiers.BLINK, Modifiers.BOLD)
    }

    @Test
    fun shouldMoveCursorToNextLineWhenNewLineIsPut() {
        target.putCharacter('\n')
        assertThat(target.getCursorPosition())
                .isEqualTo(Position.defaultPosition().withRelativeY(1))
    }

    @Test
    fun shouldPutCharacterWhenPutCharacterIsCalled() {
        val tc = TextCharacterBuilder.newBuilder()
                .character('a')
                .build()
        target.putCharacter('a')
        assertThat(target.getCharacterAt(Position.defaultPosition()).get())
                .isEqualTo(tc)
    }

    @Test
    fun shouldPutTextCharacterWhenPutTextCharacterIsCalled() {
        val tc = TextCharacterBuilder.newBuilder()
                .character('a')
                .build()
        target.putTextCharacter(tc)
        assertThat(target.getCharacterAt(Position.defaultPosition()).get())
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
    fun shouldBecomeDirtyWhenACharacterIsSet() {
        target.forEachDirtyCell { }
        val pos = Position.offset1x1()
        target.setCharacterAt(pos, 'x')
        val dirtyCells = mutableListOf<Cell>()
        target.forEachDirtyCell { dirtyCells.add(it) }
        assertThat(dirtyCells
                .filter { it.position == Position.offset1x1() }
                .map { it.character })
                .containsExactly(TextCharacter.defaultCharacter().withCharacter('x'))
    }

    @Test
    fun shouldContainProperDirtyCellsWhenPutCharIsCalled() {
        val dirtyCells = addCharAndFetchDirtyCells('a')
        assertThat(dirtyCells).containsExactly(
                Cell(
                        position = Position.defaultPosition(),
                        character = TextCharacterBuilder.newBuilder()
                                .character('a')
                                .build()),
                Cell(
                        position = Position.defaultPosition().withRelativeX(1),
                        character = TextCharacterBuilder.newBuilder()
                                .character(' ')
                                .build())
        )
    }

    @Test
    fun shouldBeDirtyAfterResize() {
        target.setSize(SIZE.withRelativeXLength(-8).withRelativeYLength(-18))

        val dirtyCells = target.drainDirtyPositions()

        assertThat(dirtyCells).hasSize(4)
    }

    @Test
    fun shouldReportDirtyWhenDirty() {
        assertThat(target.isDirty()).isTrue()
    }

    @Test
    fun shouldBeAbleToSetCharacter() {
        val expectedChar = TextCharacter.defaultCharacter().withCharacter('x')
        target.setCharacterAt(Position.offset1x1(), expectedChar)

        assertThat(target.getCharacterAt(Position.offset1x1()).get())
                .isEqualTo(expectedChar)
    }

    @Test
    fun shouldProperlyMarkBlinkingCharactersAsDirtyAfterADirtyDrain() {
        target.setCharacterAt(Position.defaultPosition(), TextCharacterBuilder.newBuilder()
                .modifiers(Modifiers.BLINK)
                .build())

        target.drainDirtyPositions()

        val result = target.drainDirtyPositions()
        assertThat(result).containsExactly(Position.defaultPosition())
    }

    @Test
    fun shouldProperlyClearWhenClearIsCalled() {
        val tc = TextCharacterBuilder.newBuilder().character('x').build()
        SIZE.fetchPositions().forEach {
            target.setCharacterAt(it, tc)
        }
        target.putCursorAt(Position.create(5, 5))
        target.drainDirtyPositions()

        target.clear()

        val positions = SIZE.fetchPositions().map {
            assertThat(target.getCharacterAt(it).get()).isEqualTo(TextCharacter.defaultCharacter())
            it
        }
        assertThat(target.getCursorPosition()).isEqualTo(Position.defaultPosition())
        assertThat(target.drainDirtyPositions()).containsExactlyInAnyOrder(*positions.toTypedArray())
    }

    @Test
    fun shouldProperlyDrawToTerminalWhenDrawCalled() {
        val cursorPos = Position.create(5, 5)
        target.putCursorAt(cursorPos)
        val size = Size.create(2, 2)
        val offset = Position.create(1, 1)
        val tc = TextCharacterBuilder.newBuilder()
                .character(TEST_CHAR)
                .build()
        val image = TextImageBuilder.newBuilder()
                .size(size)
                .filler(tc)
                .build()
        target.drainDirtyPositions()

        target.draw(image, offset)

        val positions = size.fetchPositions().map {
            val realPos = it + offset
            assertThat(target.getCharacterAt(realPos).get()).isEqualTo(tc)
            realPos
        }.plus(cursorPos).toList()

        assertThat(target.drainDirtyPositions()).containsExactlyInAnyOrder(*positions.toTypedArray())
    }

    @Test
    fun shouldProperlyListenToInputEvents() {
        val input = AtomicReference<Input>()
        target.onInput { input.set(it) }

        EventBus.broadcast(Event.Input(KeyStroke.EOF_STROKE))

        assertThat(input.get()).isEqualTo(KeyStroke.EOF_STROKE)
    }

    @Test
    fun closeShouldProperlyEmitEofStroke() {
        val input = AtomicReference<Input>()
        target.onInput { input.set(it) }

        target.close()

        assertThat(input.get()).isEqualTo(KeyStroke.EOF_STROKE)
    }

    @Test
    fun shouldProperlyIgnoreSettingACharWhichIsOutOfBounds() {
        target.drainDirtyPositions()
        val cursorPos = target.getCursorPosition()

        val result = target.setCharacterAt(Position.create(Int.MAX_VALUE, Int.MAX_VALUE), 'x')

        assertThat(result).isFalse()
        assertThat(target.drainDirtyPositions()).containsExactly(cursorPos)
    }


    @Test
    fun shouldSetCursorPositionToNewLineWhenWritingAtTheEndOfTheLine() {
        target.putCursorAt(Position.defaultPosition().withRelativeX(Int.MAX_VALUE))
        target.putCharacter('a')

        assertThat(target.getCursorPosition())
                .isEqualTo(Position.defaultPosition().withRelativeY(1))
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
        val TEST_CHAR = 'o'
        val SIZE = Size.create(10, 20)
        val FONT = CP437TilesetResource.ROGUE_YUN_16X16
        val NEW_BIGGER_SIZE = Size.create(30, 40)
        val NEW_LESS_ROWS_SIZE = SIZE.withRelativeYLength(-1)
        val NEW_LESS_COLS_SIZE = SIZE.withRelativeXLength(-1)
    }
}
