package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.graphics.TileImageBuilder
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.YELLOW
import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.kotlin.transform
import org.junit.Test

class DefaultTileImageTest {


    @Test
    fun shouldProperlyGetExistingTile() {

        val other = TileImageBuilder.newBuilder()
                .filler(Tile.defaultTile())
                .size(Sizes.create(3, 3))
                .build()

        TileImageBuilder.newBuilder()
                .filler(Tiles.defaultTile())
                .size(Sizes.create(5, 5))
                .tileset(CP437TilesetResources.aduDhabi16x16())
                .build()
                .withTileAt(Positions.create(1, 1), Tiles.defaultTile().withCharacter('x'))
                .combineWith(other, Positions.offset1x1())
                .toTileGraphic()

        assertThat(IMAGE.getTileAt(FILLED_POS).get()).isEqualTo(FILLER_TILE)
    }

    @Test
    fun shouldProperlyGetEmptyTile() {
        assertThat(IMAGE.getTileAt(EMPTY_POS).get()).isEqualTo(Tile.empty())
    }

    @Test
    fun shouldProperlyCreateCopyWithTileAt() {
        val result = IMAGE.withTileAt(Positions.offset1x1(), NEW_TILE)

        assertThat(result.getTileAt(Positions.offset1x1()).get()).isEqualTo(NEW_TILE)

        val cells = IMAGE.fetchCells()

        assertThat(cells.filter { it.position != EMPTY_POS }.all { it.tile == FILLER_TILE }).isTrue()
    }

    @Test
    fun shouldReturnSameImageWhenWithTileIsCalledWithSameChar() {
        val result = IMAGE.withTileAt(Positions.offset1x1(), FILLER_TILE)

        assertThat(result).isSameAs(IMAGE)
    }

    @Test
    fun shouldProperlyReturnFilledPositions() {
        val result = IMAGE.fetchFilledPositions()

        assertThat(SIZE.fetchPositions().subtract(result)).containsExactly(EMPTY_POS)
    }

    @Test
    fun shouldProperlyCopyImage() {
        val result = IMAGE.toTileImage()

        assertThat(result.fetchCells()).containsExactlyElementsOf(IMAGE.fetchCells())
    }

    @Test
    fun shouldProperlyCreateSubImage() {
        val result = IMAGE.toSubImage(Position.offset1x1(), Size.create(2, 1))

        assertThat(result.fetchCells()).containsExactly(
                Cell.create(Positions.create(0, 0), FILLER_TILE),
                Cell.create(Positions.create(1, 0), Tiles.empty()))
    }

    @Test
    fun shouldProperlyResizeToSmaller() {
        val result = IMAGE.withNewSize(Sizes.create(1, 1))

        assertThat(result.fetchCells()).containsExactly(
                Cell.create(Positions.create(0, 0), FILLER_TILE))
    }

    @Test
    fun shouldProperlyResizeToLarger() {
        val result = IMAGE.withNewSize(Sizes.create(4, 1))

        assertThat(result.fetchCells()).containsExactly(
                Cell.create(Positions.create(0, 0), FILLER_TILE),
                Cell.create(Positions.create(1, 0), FILLER_TILE),
                Cell.create(Positions.create(2, 0), FILLER_TILE),
                Cell.create(Positions.create(3, 0), Tiles.empty()))
    }

    @Test
    fun shouldProperlyResizeToLargerWithFiller() {
        val result = IMAGE.withNewSize(Sizes.create(4, 1), NEW_TILE)

        assertThat(result.fetchCells()).containsExactly(
                Cell.create(Positions.create(0, 0), FILLER_TILE),
                Cell.create(Positions.create(1, 0), FILLER_TILE),
                Cell.create(Positions.create(2, 0), FILLER_TILE),
                Cell.create(Positions.create(3, 0), NEW_TILE))
    }

    @Test
    fun shouldProperlyFill() {
        val result = IMAGE.withFiller(NEW_TILE)


        assertThat(result.fetchCells().map { it.tile }).containsExactly(
                FILLER_TILE, FILLER_TILE, FILLER_TILE,
                FILLER_TILE, FILLER_TILE, NEW_TILE,
                FILLER_TILE, FILLER_TILE, FILLER_TILE)
    }

    @Test
    fun shouldProperlyFetchCellsBy() {
        val result = IMAGE.fetchCellsBy(Position.offset1x1(), Size.create(2, 1))

        assertThat(result).containsExactly(
                Cell.create(Position.create(1, 1), FILLER_TILE),
                Cell.create(Position.create(2, 1), Tiles.empty()))
    }

    @Test
    fun shouldProperlyCombineWith() {
        val result = IMAGE.combineWith(OTHER_IMAGE, Position.create(2, 2))

        assertThat(result.fetchCells().map { it.tile }).containsExactly(
                FILLER_TILE, FILLER_TILE, FILLER_TILE, Tile.empty(),
                FILLER_TILE, FILLER_TILE, Tile.empty(), Tile.empty(),
                FILLER_TILE, FILLER_TILE, NEW_TILE, NEW_TILE,
                Tile.empty(), Tile.empty(), NEW_TILE, NEW_TILE)
    }

    @Test
    fun shouldProperlyTransform() {
        val result = IMAGE.transform {
            NEW_TILE
        }

        assertThat(result.fetchCells().map { it.tile }.toSet()).containsExactly(
                NEW_TILE)
    }

    @Test
    fun shouldProperlyWithText() {
        val style = StyleSet.create(YELLOW, GREEN)
        val result = IMAGE.withText("foo", style, Position.create(1, 1))

        assertThat(result.fetchCells().map { it.tile }).containsExactly(
                FILLER_TILE, FILLER_TILE, FILLER_TILE, FILLER_TILE,
                Tile.createCharacterTile('f', style),
                Tile.createCharacterTile('o', style),
                FILLER_TILE, FILLER_TILE, FILLER_TILE)
    }

    @Test
    fun shouldProperlyWithStyle() {
        val style = StyleSet.create(YELLOW, GREEN)
        val result = IMAGE.withFiller(FILLER_TILE).withStyle(style)

        assertThat(result.fetchCells().map { it.tile }.toSet()).containsExactly(
                Tile.createCharacterTile('a', style))
    }

    @Test
    fun shouldProperlyWithTileset() {
        val tileset = CP437TilesetResources.rexPaint12x12()

        val result = IMAGE.withTileset(tileset)

        assertThat(result.fetchCells().map { it.tile }).containsExactly(
                FILLER_TILE, FILLER_TILE, FILLER_TILE,
                FILLER_TILE, FILLER_TILE, Tile.empty(),
                FILLER_TILE, FILLER_TILE, FILLER_TILE)
        assertThat(result.currentTileset() == tileset)
    }


    companion object {

        val FILLED_POS = Positions.create(1, 0)
        val EMPTY_POS = Positions.create(2, 1)
        val FILLER_TILE = Tiles.defaultTile().withCharacter('a')
        val NEW_TILE = Tiles.defaultTile().withCharacter('b')

        val TILESET = CP437TilesetResources.cheepicus16x16()
        val SIZE = Sizes.create(3, 3)
        val IMAGE = DefaultTileImage(
                size = SIZE,
                tileset = TILESET,
                tiles = mapOf(
                        Positions.create(0, 0) to FILLER_TILE,
                        Positions.create(1, 0) to FILLER_TILE,
                        Positions.create(2, 0) to FILLER_TILE,
                        Positions.create(0, 1) to FILLER_TILE,
                        Positions.create(1, 1) to FILLER_TILE,
                        // 2, 1 empty
                        Positions.create(0, 2) to FILLER_TILE,
                        Positions.create(1, 2) to FILLER_TILE,
                        Positions.create(2, 2) to FILLER_TILE))

        val OTHER_IMAGE = DefaultTileImage(
                size = Size.create(2, 2),
                tileset = CP437TilesetResources.acorn8X16(),
                tiles = mapOf(
                        Positions.create(0, 0) to NEW_TILE,
                        Positions.create(1, 0) to NEW_TILE,
                        Positions.create(0, 1) to NEW_TILE,
                        Positions.create(1, 1) to NEW_TILE))
    }
}
