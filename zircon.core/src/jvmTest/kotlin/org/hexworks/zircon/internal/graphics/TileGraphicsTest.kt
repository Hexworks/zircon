package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.fetchCharacters
import org.junit.Before
import org.junit.Test

class TileGraphicsTest {

    lateinit var target: InternalTileGraphics

    @Before
    fun setUp() {
        target = TileGraphicsBuilder.newBuilder()
                .withSize(SIZE_OF_3X3)
                .withTileset(TILESET)
                .buildThreadSafeTileGraphics() as ThreadSafeTileGraphics
    }

    @Test
    fun shouldReportProperSize() {
        assertThat(target.size).isEqualTo(SIZE_OF_3X3)
    }

    @Test
    fun shouldProperlyClear() {
        target.apply {
            fill(FILLER)
        }

        target.clear()

        assertThat(target.tiles.toMap()).isEmpty()
    }

    @Test
    fun shouldProperlyGetTileWhenTileIsPresent() {
        target.draw(FILLER, FILLED_POS)

        assertThat(target.getTileAt(FILLED_POS).get()).isEqualTo(FILLER)
    }

    @Test
    fun shouldProperlyReturnEmptyTileWhenGetIsCalledWithMissingTile() {
        assertThat(target.getTileAt(FILLED_POS).get()).isEqualTo(Tile.empty())
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
    fun shouldNotSetAnythingWhenSetTileAtIsCalledWithOutOfBounds() {
        fetchOutOfBoundsPositions().forEach {
            target.draw(FILLER, it)
            assertThat(fetchTargetChars().filter { it == FILLER }).isEmpty()
        }
    }

    @Test
    fun shouldSetTileProperlyWhenCalledWithinBounds() {
        target.draw(FILLER, Position.offset1x1())
        assertThat(target.getTileAt(Position.offset1x1()).get())
                .isEqualTo(FILLER)
    }

    @Test
    fun shouldProperlyCreateSnapshot() {
        target.draw(FILLER, FILLED_POS)

        assertThat(target.state.tiles.toMap()).isEqualTo(mapOf(FILLED_POS to FILLER))
    }

    @Test
    fun shouldNotChangeSnapshotAfterCreation() {

        val result = target.state

        target.draw(FILLER, FILLED_POS)

        assertThat(result.tiles).isEmpty()
    }

    @Test
    fun shouldProperlyDrawTile() {
        val pos = Position.create(1, 1)
        target.draw(FILLER, pos)

        assertThat(target.tiles.toMap()).isEqualTo(mapOf(pos to FILLER))
    }

    @Test
    fun shouldProperlyDrawOtherTileGraphics() {
        val other = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()
                .apply {
                    fill(FILLER)
                }
        target.draw(other, pos(1, 1))

        assertThat(target.tiles.toMap()).isEqualTo(mapOf(
                pos(1, 1) to FILLER, pos(2, 1) to FILLER,
                pos(1, 2) to FILLER, pos(2, 2) to FILLER))
    }

    @Test
    fun shouldProperlyDrawOverflowingTileGraphics() {
        val other = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()
                .apply {
                    fill(FILLER)
                }
        target.draw(other, pos(2, 2))

        assertThat(target.tiles.toMap()).isEqualTo(mapOf(pos(2, 2) to FILLER))
    }

    @Test
    fun shouldProperlyDrawOntoOther() {
        val other = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()
                .apply {
                    fill(FILLER)
                }
        target.draw(other, pos(2, 2))

        assertThat(target.tiles.toMap()).isEqualTo(mapOf(
                pos(2, 2) to FILLER))
    }

    @Test
    fun shouldBeAbleToUseDifferentTileset() {
        val tileset = CP437TilesetResources.rexPaint12x12()

        target.tileset = tileset

        assertThat(target.tileset).isEqualTo(tileset)
    }


    @Test
    fun shouldProperlyResizeWhenResizeCalledWithFiller() {
        val defaultTile = FILLER.withCharacter('x')
        target.fill(defaultTile)
        val result = target.toResized(Size.create(4, 4), FILLER)
        assertThat(result.fetchCharacters()).containsExactly(
                'x', 'x', 'x', 'a',
                'x', 'x', 'x', 'a',
                'x', 'x', 'x', 'a',
                'a', 'a', 'a', 'a')
    }

    @Test
    fun shouldProperlyResizeWhenResizeCalledWithoutFiller() {
        val defaultTile = FILLER.withCharacter('x')
        target.fill(defaultTile)
        val result = target.toResized(Size.create(4, 4))
        assertThat(result.fetchCharacters()).containsExactly(
                'x', 'x', 'x', ' ',
                'x', 'x', 'x', ' ',
                'x', 'x', 'x', ' ',
                ' ', ' ', ' ', ' ')
    }

    @Test
    fun shouldProperlyResizeToSmallerGraphics() {
        val defaultTile = FILLER.withCharacter('x')
        target.fill(defaultTile)
        val result = target.toResized(Size.create(2, 2))
        assertThat(result.tiles.values).containsExactly(
                defaultTile, defaultTile,
                defaultTile, defaultTile)
    }


    @Test
    fun shouldProperlyApplyStyle() {
        target.fill(FILLER)
        val oldStyle = FILLER.styleSet
        val newStyle = StyleSet.defaultStyle()
                .withForegroundColor(ANSITileColor.GREEN)
                .withBackgroundColor(ANSITileColor.YELLOW)

        target.toSubTileGraphics(Rect.create(Position.offset1x1(), Size.one()))
                .applyStyle(newStyle)

        assertThat(target.size.fetchPositions().map {
            target.getTileAt(it).get().styleSet
        }).containsExactly(
                oldStyle, oldStyle, oldStyle,
                oldStyle, newStyle, oldStyle,
                oldStyle, oldStyle, oldStyle)
    }

    private fun fetchTargetChars(): List<Tile> {
        return (0..2).flatMap { col ->
            (0..2).map { row ->
                target.getTileAt(Position.create(col, row)).get()
            }
        }
    }

    private fun fetchOutOfBoundsPositions(): List<Position> {
        return listOf(Position.create(SIZE_OF_3X3.width - 1, Int.MAX_VALUE),
                Position.create(Int.MAX_VALUE, SIZE_OF_3X3.width - 1),
                Position.create(Int.MAX_VALUE, Int.MAX_VALUE))
    }

    private fun pos(x: Int, y: Int) = Position.create(x, y)

    companion object {
        val TILESET = CP437TilesetResources.jolly12x12()
        val FILLED_POS = Position.create(1, 2)
        val SIZE_OF_3X3 = Size.create(3, 3)
        val FILLER: CharacterTile = TileBuilder.newBuilder()
                .withCharacter('a')
                .buildCharacterTile()

    }

}
