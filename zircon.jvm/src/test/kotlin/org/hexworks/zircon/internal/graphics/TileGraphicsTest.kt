package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.kotlin.toMap
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.behavior.impl.DefaultBoundable
import org.junit.Before
import org.junit.Test

class TileGraphicsTest {

    lateinit var target: TileGraphics

    @Before
    fun setUp() {
        target = TileGraphicsBuilder.newBuilder()
                .size(SIZE_OF_3X3)
                .tileset(TILESET)
                .build()
    }

    @Test
    fun shouldReportProperBounds() {
        assertThat(target.rect).isEqualTo(Rect.create(size = SIZE_OF_3X3))
    }

    @Test
    fun shouldReportProperSize() {
        assertThat(target.size).isEqualTo(SIZE_OF_3X3)
    }

    @Test
    fun shouldIntersectWithIntersectingBoundable() {
        assertThat(target.intersects(DefaultBoundable(Size.create(5, 5))))
    }

    @Test
    fun shouldNotIntersectWithNonIntersectingBoundable() {
        assertThat(target.intersects(DefaultBoundable(Size.create(2, 2), Position.create(3, 3))))
    }

    @Test
    fun shouldContainContainedPosition() {
        assertThat(target.containsPosition(Position.create(2, 2))).isTrue()
    }

    @Test
    fun shouldNotContainNotContainedPosition() {
        assertThat(target.containsPosition(Position.create(3, 3))).isFalse()
    }

    @Test
    fun shouldContainContainedBoundable() {
        assertThat(target.containsBoundable(Boundable.create(Position.offset1x1(), Sizes.create(2, 2))))
    }

    @Test
    fun shouldNotContainNotContainedBoundable() {
        assertThat(target.containsBoundable(Boundable.create(Position.create(2, 2), Sizes.create(2, 2))))
    }

    @Test
    fun shouldProperlyClear() {
        target.fill(FILLER)

        target.clear()

        assertThat(target.fetchCells().map { it.tile }).containsExactly(
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE,
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE,
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE)
    }

    @Test
    fun shouldProperlyGetTileWhenTileIsPresent() {
        target.setTileAt(FILLED_POS, FILLER)

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
            target.setTileAt(it, FILLER)
            assertThat(fetchTargetChars().filter { it == FILLER }).isEmpty()
        }
    }

    @Test
    fun shouldSetTileProperlyWhenCalledWithinBounds() {
        target.setTileAt(Position.offset1x1(), FILLER)
        assertThat(target.getTileAt(Position.offset1x1()).get())
                .isEqualTo(FILLER)
    }

    @Test
    fun shouldProperlyCreateSnapshot() {
        target.setTileAt(FILLED_POS, FILLER)

        assertThat(target.createSnapshot().cells.toMap()).isEqualTo(mapOf(FILLED_POS to FILLER))
    }

    @Test
    fun shouldNotChangeSnapshotAfterCreation() {

        val result = target.createSnapshot()

        target.setTileAt(FILLED_POS, FILLER)

        assertThat(result.cells).isEmpty()
    }

    @Test
    fun shouldProperlyDrawTile() {
        target.draw(FILLER, Position.create(1, 1))

        assertThat(target.fetchCells().map { it.tile }).containsExactly(
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE,
                EMPTY_TILE, FILLER, EMPTY_TILE,
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE)
    }

    @Test
    fun shouldProperlyDrawOtherTileGraphics() {
        val other = TileGraphicsBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()
                .fill(FILLER)
        target.draw(other, Position.create(1, 1))

        assertThat(target.fetchCells().map { it.tile }).containsExactly(
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE,
                EMPTY_TILE, FILLER, FILLER,
                EMPTY_TILE, FILLER, FILLER)
    }

    @Test
    fun shouldProperlyDrawOverflowingTileGraphics() {
        val other = TileGraphicsBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()
                .fill(FILLER)
        target.draw(other, Position.create(2, 2))

        assertThat(target.fetchCells().map { it.tile }).containsExactly(
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE,
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE,
                EMPTY_TILE, EMPTY_TILE, FILLER)
    }

    @Test
    fun shouldProperlyDrawOntoOther() {
        val other = TileGraphicsBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()
                .fill(FILLER)
        other.drawOnto(target, Position.create(2, 2))

        assertThat(target.fetchCells().map { it.tile }).containsExactly(
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE,
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE,
                EMPTY_TILE, EMPTY_TILE, FILLER)
    }

    @Test
    fun shouldBeAbleToUseDifferentTileset() {
        val tileset = CP437TilesetResources.rexPaint12x12()

        target.useTileset(tileset)

        assertThat(target.currentTileset()).isEqualTo(tileset)
    }

    @Test
    fun shouldBeAbleToUseDifferentStyles() {
        val style = StyleSet.defaultStyle()
                .withForegroundColor(ANSITileColor.GREEN)
                .withBackgroundColor(ANSITileColor.YELLOW)
        target.setStyleFrom(style)

        assertThat(target.toStyleSet()).isEqualTo(style)
    }

    @Test
    fun shouldProperlySetStyleFrom() {
        val style = StyleSet.defaultStyle()
                .withForegroundColor(ANSITileColor.GREEN)
                .withBackgroundColor(ANSITileColor.YELLOW)
        target.setStyleFrom(style)
        assertThat(target.toStyleSet()).isEqualTo(style)
    }

    @Test
    fun shouldReturnEmptyListIfFetchingFilledPositionsOfEmptyTileGraphics() {
        assertThat(target.fetchFilledPositions()).isEmpty()
    }

    @Test
    fun shouldReturnFilledPositionsWhenThereAreSome() {
        target.setTileAt(Position.offset1x1(), FILLER)
        assertThat(target.fetchFilledPositions()).containsExactly(Position.offset1x1())
    }

    @Test
    fun shouldProperlyResizeWhenResizeCalledWithFiller() {
        val defaultTile = FILLER.withCharacter('x')
        target.fill(defaultTile)
        val result = target.resize(Size.create(4, 4), FILLER)
        assertThat(result.fetchCells().map { it.tile }).containsExactly(
                defaultTile, defaultTile, defaultTile, FILLER,
                defaultTile, defaultTile, defaultTile, FILLER,
                defaultTile, defaultTile, defaultTile, FILLER,
                FILLER, FILLER, FILLER, FILLER)
    }

    @Test
    fun shouldProperlyResizeWhenResizeCalledWithoutFiller() {
        val defaultTile = FILLER.withCharacter('x')
        target.fill(defaultTile)
        val result = target.resize(Size.create(4, 4))
        assertThat(result.fetchCells().map { it.tile }).containsExactly(
                defaultTile, defaultTile, defaultTile, EMPTY_TILE,
                defaultTile, defaultTile, defaultTile, EMPTY_TILE,
                defaultTile, defaultTile, defaultTile, EMPTY_TILE,
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE, EMPTY_TILE)
    }

    @Test
    fun shouldProperlyResizeToSmallerGraphics() {
        val defaultTile = FILLER.withCharacter('x')
        target.fill(defaultTile)
        val result = target.resize(Size.create(2, 2))
        assertThat(result.fetchCells().map { it.tile }).containsExactly(
                defaultTile, defaultTile,
                defaultTile, defaultTile)
    }

    @Test
    fun shouldProperlyFetchCells() {
        val graphics = target.resize(Sizes.create(2, 2))
        val result = graphics.fetchCells()
        assertThat(result).containsExactly(
                Cell.create(Position.create(0, 0), EMPTY_TILE), Cell.create(Position.create(1, 0), EMPTY_TILE),
                Cell.create(Position.create(0, 1), EMPTY_TILE), Cell.create(Position.create(1, 1), EMPTY_TILE))
    }

    @Test
    fun shouldProperlyFetchCellsBy() {
        val result = target.fetchCellsBy(Position.offset1x1(), Size.create(2, 1))
        assertThat(result).containsExactly(
                Cell.create(Position.create(1, 1), EMPTY_TILE),
                Cell.create(Position.create(2, 1), EMPTY_TILE))
    }

    @Test
    fun shouldProperlyPutTextWithStyle() {
        val style = StyleSet.defaultStyle()
                .withForegroundColor(ANSITileColor.GREEN)
                .withBackgroundColor(ANSITileColor.YELLOW)
        target.setStyleFrom(style)

        target.putText("foo", Position.offset1x1())

        assertThat(target.fetchCells().map { it.tile }).containsExactly(
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE,
                EMPTY_TILE, Tile.empty().withCharacter('f').withStyle(style), Tile.empty().withCharacter('o').withStyle(style),
                EMPTY_TILE, EMPTY_TILE, EMPTY_TILE)
    }

    @Test
    fun shouldProperlyApplyStyle() {
        val oldStyle = StyleSet.empty()
        val newStyle = StyleSet.defaultStyle()
                .withForegroundColor(ANSITileColor.GREEN)
                .withBackgroundColor(ANSITileColor.YELLOW)

        target.applyStyle(newStyle, Rect.create(Position.offset1x1(), Size.one()))

        assertThat(target.fetchCells().map { it.tile.styleSet() }).containsExactly(
                oldStyle, oldStyle, oldStyle,
                oldStyle, newStyle, oldStyle,
                oldStyle, oldStyle, oldStyle)
    }

    @Test
    fun shouldNotApplyStyleToEmptyCells() {
        val oldStyle = StyleSet.empty()
        val newStyle = StyleSet.defaultStyle()
                .withForegroundColor(ANSITileColor.GREEN)
                .withBackgroundColor(ANSITileColor.YELLOW)

        target.applyStyle(
                styleSet = newStyle,
                rect = Rect.create(Position.offset1x1(), Size.one()),
                applyToEmptyCells = false)

        assertThat(target.fetchCells().map { it.tile.styleSet() }).containsExactly(
                oldStyle, oldStyle, oldStyle,
                oldStyle, oldStyle, oldStyle,
                oldStyle, oldStyle, oldStyle)
    }

    @Test
    fun shouldKeepModifiersWhenApplyingStyle() {
        val oldStyle = StyleSet.defaultStyle().withModifiers(SimpleModifiers.Underline)
        val newStyle = StyleSet.defaultStyle()
                .withForegroundColor(ANSITileColor.GREEN)
                .withBackgroundColor(ANSITileColor.YELLOW)
                .withModifiers(SimpleModifiers.Underline)
        val styleToApply = StyleSet.defaultStyle()
                .withForegroundColor(ANSITileColor.GREEN)
                .withBackgroundColor(ANSITileColor.YELLOW)


        target.fill(Tile.defaultTile().withStyle(oldStyle))

        target.applyStyle(
                styleSet = styleToApply,
                rect = Rect.create(Position.offset1x1(), Size.create(2, 1)),
                keepModifiers = true)

        assertThat(target.fetchCells().map { it.tile.styleSet() }).containsExactly(
                oldStyle, oldStyle, oldStyle,
                oldStyle, newStyle, newStyle,
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
        return listOf(Position.create(SIZE_OF_3X3.xLength - 1, Int.MAX_VALUE),
                Position.create(Int.MAX_VALUE, SIZE_OF_3X3.xLength - 1),
                Position.create(Int.MAX_VALUE, Int.MAX_VALUE))
    }

    companion object {
        val TILESET = BuiltInCP437TilesetResource.JOLLY_12X12
        val EMPTY_TILE = Tile.empty()
        val FILLED_POS = Position.create(1, 2)
        val SIZE_OF_3X3 = Size.create(3, 3)
        val FILLER: CharacterTile = TileBuilder.newBuilder()
                .character('a')
                .buildCharacterTile()

    }

}
