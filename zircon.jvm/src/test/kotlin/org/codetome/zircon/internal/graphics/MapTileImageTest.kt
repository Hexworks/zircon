package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.builder.graphics.TileImageBuilder
import org.codetome.zircon.api.data.Cell
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Position.Companion.defaultPosition
import org.codetome.zircon.api.data.Position.Companion.offset1x1
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.junit.Before
import org.junit.Test
import java.util.function.Consumer

class MapTileImageTest {

    lateinit var target: MapTileImage

    @Before
    fun setUp() {
        target = MapTileImage(
                size = SIZE_OF_3X3,
                tileset = TILESET)
    }

    @Test
    fun shouldContainNothingWhenCreated() {
        assertThat(target.getTileAt(EMPTY_BY_DEFAULT_POS).get())
                .isEqualTo(EMPTY_CHAR)
    }

    @Test
    fun shouldProperlyImplementGetCharacter() {
        target.setTileAt(FILLED_POS, FILLER)

        assertThat(target.getTileAt(FILLED_POS).get()).isEqualTo(FILLER)
    }


    @Test
    fun shouldReportProperSizeWhenGetSizeIsCalled() {
        assertThat(target.getBoundableSize()).isEqualTo(SIZE_OF_3X3)
    }

    @Test
    fun shouldProperlyResizeWhenResizeCalledWithDifferentSize() {
        val result = target.resize(Size.create(4, 4), SET_ALL_CHAR)
        assertThat(result.getTileAt(Position.defaultPosition()).get())
                .isEqualTo(EMPTY_CHAR)
        assertThat(result.getTileAt(Position.create(1, 1)).get())
                .isEqualTo(EMPTY_CHAR)
        assertThat(result.getTileAt(Position.create(3, 3)).get())
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun shouldNotSetAnythingWhenSetCharAtIsCalledWithOutOfBounds() {
        fetchOutOfBoundsPositions().forEach {
            target.setTileAt(it, SET_ALL_CHAR)
            assertThat(fetchTargetChars().filter { it == SET_ALL_CHAR }).isEmpty()
        }
    }

    @Test
    fun shouldSetCharProperlyWhenCalledWithinBounds() {
        target.setTileAt(Position.offset1x1(), SET_ALL_CHAR)
        assertThat(target.getTileAt(Position.offset1x1()).get())
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun shouldThrowExceptionWhenGettingCharOutOfBounds() {
        fetchOutOfBoundsPositions().forEach {
            var ex: Exception? = null
            try {
                target.getTileAt(it).get()
            } catch (e: Exception) {
                ex = e
            }
            assertThat(ex).isNotNull()
        }
    }

    @Test
    fun shouldProperlyCopyWithBasicParams() {
        IMAGE_TO_COPY.drawOnto(target)

        assertThat(target.getTileAt(defaultPosition()).get())
                .isEqualTo(SET_ALL_CHAR)
    }

    @Test
    fun givenATextImageThatOverFlowsWhenCombinedThenResizeNewTextImage() {
        val sourceChar = Tile.defaultTile().withCharacter('x')
        val overwriteChar = Tile.defaultTile().withCharacter('+')

        val originalSize = Size.create(3, 1)
        val source = TileImageBuilder.newBuilder()
                .size(originalSize)
                .build()
        originalSize.fetchPositions().forEach {
            source.setTileAt(it, sourceChar)
        }

        val newImage = TileImageBuilder.newBuilder()
                .size(Size.create(2, 1))
                .build()
                .fill(overwriteChar)

        val result = source.combineWith(newImage, Position.create(0, 2))
        assertThat(result.getBoundableSize()).isEqualTo(Size.create(3, 3))

        //first yLength should all be xLength's
        for (x in 0..2) {
            assertThat(result.getTileAt(Position.create(x, 0))
                    .get()
                    .asCharacterTile()
                    .get().character).isEqualTo('x')
        }

        //as the second image was offset by 2 yLength there should be nothing here
        for (x in 0..2) {
            assertThat(result.getTileAt(Position.create(x, 1))
                    .get()
                    .asCharacterTile()
                    .get().character).isEqualTo(' ')
        }

        //the 3rd yLength should be + for the first 2 xLength (as that's the size of the second image)
        for (x in 0..1) {
            assertThat(result.getTileAt(Position.create(x, 2))
                    .get()
                    .asCharacterTile()
                    .get().character).isEqualTo('+')
        }

        //the bottom right character should be blank
        assertThat(result.getTileAt(Position.create(2, 2))
                .get()
                .asCharacterTile()
                .get().character).isEqualTo(' ')
    }

    @Test
    fun shouldProperlyCombineTwoImages() {
        val sourceChar = Tile.defaultTile().withCharacter('x')
        val imageChar = Tile.defaultTile().withCharacter('+')
        val filler = Tile.defaultTile().withCharacter('_')

        val source = TileImageBuilder.newBuilder()
                .size(Size.create(3, 3))
                .build()
                .fill(filler)

        val image = TileImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()
                .fill(filler)

        source.setTileAt(Position.create(0, 0), sourceChar)
        source.setTileAt(Position.create(1, 0), sourceChar)
        source.setTileAt(Position.create(0, 1), sourceChar)
        source.setTileAt(Position.create(1, 1), sourceChar)

        image.getBoundableSize().fetchPositions().forEach(Consumer {
            image.setTileAt(it, imageChar)
        })

        val result = source.combineWith(image, offset1x1())

        assertThat(result.getTileAt(offset1x1()).get()).isEqualTo(imageChar)
        assertThat(result.getBoundableSize()).isEqualTo(source.getBoundableSize())
        assertThat(source.getTileAt(offset1x1()).get()).isEqualTo(sourceChar)
        assertThat(image.getTileAt(offset1x1()).get()).isEqualTo(imageChar)
    }

    @Test
    fun shouldProperlyFetchCellsBy() {
        val result = target.fetchCellsBy(Position.offset1x1(), Size.create(2, 1))
        assertThat(result).containsExactly(
                Cell(Position.create(1, 1), EMPTY_CHAR),
                Cell(Position.create(2, 1), EMPTY_CHAR))
    }

    private fun fetchTargetChars(): List<Tile> {
        return (0..2).flatMap { col ->
            (0..2).map { row ->
                target.getTileAt(Position.create(col, row)).get()
            }
        }
    }

    private fun fetchOutOfBoundsPositions(): List<Position> {
        return listOf(Position.create(SIZE_OF_3X3.xLength - 1, Int.MAX_VALUE),
                Position.create(Int.MAX_VALUE, SIZE_OF_3X3.xLength - 1),
                Position.create(Int.MAX_VALUE, Int.MAX_VALUE))
    }

    companion object {
        val TILESET = CP437TilesetResource.JOLLY_12X12
        val EMPTY_CHAR = Tile.empty()
        val EMPTY_BY_DEFAULT_POS = Position.create(2, 1)
        val FILLED_POS = Position.create(1, 2)
        val SIZE_OF_3X3 = Size.create(3, 3)
        val FILLER = TileBuilder.newBuilder()
                .character('a')
                .build()
        val TO_COPY_CHAR = TileBuilder.newBuilder()
                .character('b')
                .build()
        val SET_ALL_CHAR = TileBuilder.newBuilder()
                .character('c')
                .build()
        val TO_COPY = arrayOf(arrayOf(TO_COPY_CHAR))
        val IMAGE_TO_COPY = ConcurrentTileImage(
                size = Size.one(),
                tileset = TILESET).fill(SET_ALL_CHAR)
        val IMAGE_TO_COPY_AND_CROP = ConcurrentTileImage(
                size = Size.create(2, 2),
                tileset = TILESET).fill(SET_ALL_CHAR)

    }

}
