package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Position.Companion.DEFAULT_POSITION
import org.codetome.zircon.api.Position.Companion.OFFSET_1x1
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.function.Consumer

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
        checkGetCharacter(target)
    }

    @Test
    fun shouldProperlyImplementGetCharacter() {
        checkGetCharacter<DrawSurface>(target)
    }

    private fun <T : DrawSurface> checkGetCharacter(target: T) {
        assertThat(target.getCharacterAt(DEFAULT_POSITION).get())
                .isEqualTo(TO_COPY_CHAR)
    }


    @Test
    fun shouldContainFillerWhenCreated() {
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeColumn(1)).get())
                .isEqualTo(FILLER)
        assertThat(target.getCharacterAt(DEFAULT_POSITION.withRelativeRow(1)).get())
                .isEqualTo(FILLER)
        assertThat(target.getCharacterAt(Position(2, 2)).get())
                .isEqualTo(FILLER)
    }

    @Test
    fun shouldReportProperSizeWhenGetSizeIsCalled() {
        assertThat(target.getBoundableSize()).isEqualTo(SIZE)
    }

    @Test
    fun shouldProperlyResizeWhenResizeCalledWithDifferentSize() {
        val result = target.resize(Size(4, 4), SET_ALL_CHAR)
        assertThat(result.getCharacterAt(DEFAULT_POSITION).get())
                .isEqualTo(TO_COPY_CHAR)
        assertThat(result.getCharacterAt(Position(1, 1)).get())
                .isEqualTo(FILLER)
        assertThat(result.getCharacterAt(Position(3, 3)).get())
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun shouldNotResizeWhenResizeIsCalledWithSameSize() {
        val result = target.resize(target.getBoundableSize(), TO_COPY_CHAR)

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
        assertThat(target.getCharacterAt(OFFSET_1x1).get())
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun shouldThrowExceptionWhenGettingCharOutOfBounds() {
        fetchOutOfBoundsPositions().forEach {
            var ex: Exception? = null
            try {
                target.getCharacterAt(it).get()
            } catch (e: Exception) {
                ex = e
            }
            assertThat(ex).isNotNull()
        }
    }

    @Test
    fun shouldProperlyCopyWithBasicParams() {
        IMAGE_TO_COPY.drawOnto(target)

        assertThat(target.getCharacterAt(DEFAULT_POSITION).get())
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun shouldProperlyCombineTwoImages() {
        val sourceChar = TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter('x')
        val imageChar = TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter('+')
        val filler = TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter('_')

        val source = TextImageBuilder.newBuilder()
                .size(Size.of(3, 3))
                .filler(filler)
                .build()

        val image = TextImageBuilder.newBuilder()
                .size(Size.of(2, 2))
                .filler(filler)
                .build()

        source.setCharacterAt(Position.of(0, 0), sourceChar)
        source.setCharacterAt(Position.of(1, 0), sourceChar)
        source.setCharacterAt(Position.of(0, 1), sourceChar)
        source.setCharacterAt(Position.of(1, 1), sourceChar)

        image.getBoundableSize().fetchPositions().forEach(Consumer {
            image.setCharacterAt(it, imageChar)
        })

        val result = source.combineWith(image, OFFSET_1x1)

        assertThat(result.getCharacterAt(OFFSET_1x1).get()).isEqualTo(imageChar)
        assertThat(result.getBoundableSize()).isEqualTo(source.getBoundableSize())
        assertThat(source.getCharacterAt(OFFSET_1x1).get()).isEqualTo(sourceChar)
        assertThat(image.getCharacterAt(OFFSET_1x1).get()).isEqualTo(imageChar)
    }

    private fun fetchTargetChars(): List<TextCharacter> {
        return (0..2).flatMap { col ->
            (0..2).map { row ->
                target.getCharacterAt(Position(col, row)).get()
            }
        }
    }

    private fun fetchOutOfBoundsPositions(): List<Position> {
        return listOf(Position(SIZE.columns - 1, Int.MAX_VALUE),
                Position(Int.MAX_VALUE, SIZE.columns - 1),
                Position(Int.MAX_VALUE, Int.MAX_VALUE))
    }

    companion object {
        val SIZE = Size(3, 3)
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
        val IMAGE_TO_COPY = DefaultTextImage(Size.ONE, arrayOf(arrayOf()), SET_ALL_CHAR)
        val IMAGE_TO_COPY_AND_CROP = DefaultTextImage(Size(2, 2), arrayOf(arrayOf()), SET_ALL_CHAR)

    }

}