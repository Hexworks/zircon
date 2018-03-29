package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Position.Companion.DEFAULT_POSITION
import org.codetome.zircon.api.Position.Companion.OFFSET_1x1
import org.codetome.zircon.api.Position.Companion.of
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.junit.Before
import org.junit.Test
import java.util.function.Consumer

class InMemoryTextImageTest {

    lateinit var target: InMemoryTextImage

    @Before
    fun setUp() {
        target = InMemoryTextImage(SIZE_OF_3X3)
    }

    @Test
    fun shouldContainNothingWhenCreated() {
        assertThat(target.getCharacterAt(EMPTY_BY_DEFAULT_POS))
                .contains(EMPTY_CHAR)
    }

    @Test
    fun shouldProperlyImplementGetCharacter() {
        target.setCharacterAt(FILLED_POS, FILLER)

        assertThat(target.getCharacterAt(FILLED_POS)).contains(FILLER)
    }


    @Test
    fun shouldReportProperSizeWhenGetSizeIsCalled() {
        assertThat(target.getBoundableSize()).isEqualTo(SIZE_OF_3X3)
    }

    @Test
    fun shouldProperlyResizeWhenResizeCalledWithDifferentSize() {
        val result = target.resize(Size.of(4, 4), SET_ALL_CHAR)
        assertThat(result.getCharacterAt(DEFAULT_POSITION).get())
                .isEqualTo(EMPTY_CHAR)
        assertThat(result.getCharacterAt(Position.of(1, 1)).get())
                .isEqualTo(EMPTY_CHAR)
        assertThat(result.getCharacterAt(Position.of(3, 3)).get())
                .isEqualTo(SET_ALL_CHAR)
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
    fun givenATextImageThatOverFlowsWhenCombinedThenResizeNewTextImage(){
        val sourceChar = TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter('x')
        val overwriteChar = TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter('+')

        val originalSize = Size.of(3, 1)
        val source = TextImageBuilder.newBuilder()
                .size(originalSize)
                .build()
        originalSize.fetchPositions().forEach {
            source.setCharacterAt(it, sourceChar)
        }

        val newImage = TextImageBuilder.newBuilder()
                .size(Size.of(2, 1))
                .filler(overwriteChar)
                .build()

        val result = source.combineWith(newImage, Position.of(0, 2))
        assertThat(result.getBoundableSize()).isEqualTo(Size.of(3, 3))

        //first yLength should all be xLength's
        for(x in 0..2){
            assertThat(result.getCharacterAt(of(x, 0)).get().getCharacter()).isEqualTo('x')
        }

        //as the second image was offset by 2 yLength there should be nothing here
        for(x in 0..2){
            assertThat(result.getCharacterAt(of(x, 1)).get().getCharacter()).isEqualTo(' ')
        }

        //the 3rd yLength should be + for the first 2 xLength (as that's the size of the second image)
        for(x in 0..1){
            assertThat(result.getCharacterAt(of(x, 2)).get().getCharacter()).isEqualTo('+')
        }

        //the bottom right character should be blank
        assertThat(result.getCharacterAt(of(2, 2)).get().getCharacter()).isEqualTo(' ')
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

    @Test
    fun shouldProperlyFetchCellsBy() {
        val result = target.fetchCellsBy(Position.OFFSET_1x1, Size.of(2, 1))
        assertThat(result).containsExactly(
                Cell(Position.of(1, 1), EMPTY_CHAR),
                Cell(Position.of(2, 1), EMPTY_CHAR))
    }

    private fun fetchTargetChars(): List<TextCharacter> {
        return (0..2).flatMap { col ->
            (0..2).map { row ->
                target.getCharacterAt(Position.of(col, row)).get()
            }
        }
    }

    private fun fetchOutOfBoundsPositions(): List<Position> {
        return listOf(Position.of(SIZE_OF_3X3.xLength - 1, Int.MAX_VALUE),
                Position.of(Int.MAX_VALUE, SIZE_OF_3X3.xLength - 1),
                Position.of(Int.MAX_VALUE, Int.MAX_VALUE))
    }

    companion object {
        val EMPTY_CHAR = TextCharacterBuilder.EMPTY
        val EMPTY_BY_DEFAULT_POS = Position.of(2, 1)
        val FILLED_POS = Position.of(1, 2)
        val SIZE_OF_3X3 = Size.of(3, 3)
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
        val IMAGE_TO_COPY = InMemoryTextImage(
                size = Size.ONE,
                filler = SET_ALL_CHAR)
        val IMAGE_TO_COPY_AND_CROP = InMemoryTextImage(
                size = Size.of(2, 2),
                filler = SET_ALL_CHAR)

    }

}
