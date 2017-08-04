package org.codetome.zircon.graphics.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TerminalPosition.Companion.DEFAULT_POSITION
import org.codetome.zircon.TerminalPosition.Companion.OFFSET_1x1
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.builder.TextCharacterBuilder
import org.codetome.zircon.graphics.DefaultTextImage
import org.codetome.zircon.terminal.TerminalSize
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class DefaultTextImageTest {

    lateinit var target: DefaultTextImage

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = DefaultTextImage(
                size = SIZE,
                toCopy = TO_COPY,
                filler = FILLER)
    }

    @Test
    fun shouldContainToCopyWhenCreated() {
        assertThat(target.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(TO_COPY_CHAR)
    }

    @Test
    fun shouldContainFillerWhenCreated() {
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(1)))
                .isEqualTo(FILLER)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)))
                .isEqualTo(FILLER)
        assertThat(target.getCharacterAt(TerminalPosition(2, 2)))
                .isEqualTo(FILLER)
    }

    @Test
    fun shouldReportProperSizeWhenGetSizeIsCalled() {
        assertThat(target.getSize()).isEqualTo(SIZE)
    }

    @Test
    fun shouldSetAllWhenItIsCalled() {
        target.setAll(SET_ALL_CHAR)
        (0..2).forEach { col ->
            (0..2).forEach { row ->
                assertThat(target.getCharacterAt(TerminalPosition(col, row)))
                        .isEqualTo(SET_ALL_CHAR)
            }
        }
    }

    @Test
    fun shouldProperlyResizeWhenResizeCalledWithDifferentSize() {
        val result = target.resize(TerminalSize(4, 4), SET_ALL_CHAR)
        assertThat(result.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(TO_COPY_CHAR)
        assertThat(result.getCharacterAt(TerminalPosition(1, 1)))
                .isEqualTo(FILLER)
        assertThat(result.getCharacterAt(TerminalPosition(3, 3)))
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun shouldNotResizeWhenResizeIsCalledWithSameSize() {
        val result = target.resize(target.getSize(), TO_COPY_CHAR)

        assertThat(result).isSameAs(target)
    }

    @Test
    fun shouldNotSetAnythingWhenSetCharAtIsCalledWithOutOfBounds() {
        fetchOutOfBoundsPositions().forEach {

            target.setCharacterAt(it, SET_ALL_CHAR)
            assertThat(fetchTargetChars().filter { it == SET_ALL_CHAR }).isEmpty()
        }
    }

    @Test
    fun shouldSetCharProperlyWhenCalledWithinBounds() {
        target.setCharacterAt(OFFSET_1x1, SET_ALL_CHAR)
        assertThat(target.getCharacterAt(OFFSET_1x1))
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun shouldThrowExceptionWhenGettingCharOutOfBounds() {
        fetchOutOfBoundsPositions().forEach {
            var ex: Exception? = null
            try {
                target.getCharacterAt(it)
            } catch (e: Exception) {
                ex = e
            }
            assertThat(ex).isNotNull()
        }
    }

    @Test
    fun shouldProperlyCopyWithBasicParams() {
        IMAGE_TO_COPY.copyTo(target)

        assertThat(target.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun shouldProperlyCopyWhenOffsettingRow() {
        IMAGE_TO_COPY.copyTo(
                destination = target,
                destinationRowOffset = 1)

        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)))
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun shouldProperlyCopyWhenOffsettingColumn() {
        IMAGE_TO_COPY.copyTo(
                destination = target,
                destinationColumnOffset = 1)

        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(1)))
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun shouldProperlyCopyWhenStartAtSecondRow() {
        IMAGE_TO_COPY_AND_CROP.copyTo(
                destination = target,
                startRowIndex = 1)

        assertThat(target.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(1)))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)))
                .isEqualTo(FILLER)

    }

    @Test
    fun shouldProperlyCopyWhenStartAtSecondColumn() {
        IMAGE_TO_COPY_AND_CROP.copyTo(
                destination = target,
                startColumnIndex = 1)

        assertThat(target.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(1)))
                .isEqualTo(FILLER)

    }

    @Test
    fun shouldProperlyCopyWhenStartAtMinusOneColumn() {
        IMAGE_TO_COPY_AND_CROP.copyTo(
                destination = target,
                startColumnIndex = -1)

        assertThat(target.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(TO_COPY_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(1)))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(TerminalPosition(1, 1)))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(2)))
                .isEqualTo(FILLER)
    }

    @Test
    fun shouldProperlyCopyWhenStartAtMinusOneRow() {
        IMAGE_TO_COPY_AND_CROP.copyTo(
                destination = target,
                startRowIndex = -1)

        assertThat(target.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(TO_COPY_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(TerminalPosition(1, 1)))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(2)))
                .isEqualTo(FILLER)
    }

    @Test
    fun shouldProperlyCopyWhenRowOffsetIsNegative() {
        IMAGE_TO_COPY_AND_CROP.copyTo(
                destination = target,
                destinationRowOffset = -1)

        assertThat(target.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(1)))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)))
                .isEqualTo(FILLER)
    }

    @Test
    fun shouldProperlyCopyWhenColumnOffsetIsNegative() {
        IMAGE_TO_COPY_AND_CROP.copyTo(
                destination = target,
                destinationColumnOffset = -1)

        assertThat(target.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(1)))
                .isEqualTo(FILLER)
    }

    @Test
    fun shouldProperlyCopyWhenOneRowToCopy() {
        IMAGE_TO_COPY_AND_CROP.copyTo(
                destination = target,
                rows = 1)

        assertThat(target.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(1)))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)))
                .isEqualTo(FILLER)

    }

    @Test
    fun shouldProperlyCopyWhenOneColToCopy() {
        IMAGE_TO_COPY_AND_CROP.copyTo(
                destination = target,
                columns = 1)

        assertThat(target.getCharacterAt(DEFAULT_POSITION))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)))
                .isEqualTo(SET_ALL_CHAR)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(1)))
                .isEqualTo(FILLER)

    }

    @Test
    fun shouldAddCharsWhenTextGraphicsIsCreatedAndManipulated() {
        val char = 'a'
        val graphics = target.newTextGraphics()
        graphics.putString(DEFAULT_POSITION, char.toString())
        assertThat(target.getCharacterAt(DEFAULT_POSITION)).isEqualTo(TextCharacter.builder()
                .character(char)
                .build())
    }

    @Test
    fun shouldNotAddCharsWhenTextGraphicsIsCreatedAndCharIsPutOutOfBounds() {
        val char = 'x'
        val graphics = target.newTextGraphics()
        graphics.putString(DEFAULT_POSITION.withRow(Int.MAX_VALUE), char.toString())
        val cells  = fetchTargetChars()
        assertThat(cells.filter { it.getCharacter() == 'x' }).isEmpty()
    }

    private fun fetchTargetChars(): List<TextCharacter> {
        return (0..2).flatMap { col ->
            (0..2).map { row ->
                target.getCharacterAt(TerminalPosition(col, row))
            }
        }
    }

    private fun fetchOutOfBoundsPositions(): List<TerminalPosition> {
        return listOf(TerminalPosition(Int.MAX_VALUE, Int.MAX_VALUE),
                TerminalPosition(Int.MIN_VALUE, Int.MAX_VALUE),
                TerminalPosition(Int.MAX_VALUE, Int.MIN_VALUE),
                TerminalPosition(Int.MIN_VALUE, Int.MIN_VALUE))
    }

    companion object {
        val SIZE = TerminalSize(3, 3)
        val FILLER = TextCharacterBuilder.newBuilder()
                .character('a')
                .build()
        val TO_COPY_CHAR = TextCharacterBuilder.newBuilder()
                .character('b')
                .build()
        val SET_ALL_CHAR = TextCharacterBuilder.newBuilder()
                .character('c')
                .build()
        val TO_COPY = arrayOf(arrayOf(TO_COPY_CHAR))
        val IMAGE_TO_COPY = DefaultTextImage(TerminalSize.ONE, arrayOf(arrayOf()), SET_ALL_CHAR)
        val IMAGE_TO_COPY_AND_CROP = DefaultTextImage(TerminalSize(2, 2), arrayOf(arrayOf()), SET_ALL_CHAR)

    }

}