package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.graphics.TileImageBuilder
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.YELLOW
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.Tile.Companion
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.fetchCharacters
import org.junit.Test

class DefaultTileImageTest {


    @Test
    fun shouldProperlyGetExistingTile() {

        val other = TileImageBuilder.newBuilder()
                .withFiller(Tile.defaultTile())
                .withSize(Sizes.create(3, 3))
                .build()

        TileImageBuilder.newBuilder()
                .withFiller(Tile.defaultTile())
                .withSize(Sizes.create(5, 5))
                .withTileset(CP437TilesetResources.aduDhabi16x16())
                .build()
                .withTileAt(Positions.create(1, 1), Tile.defaultTile().withCharacter('x'))
                .combineWith(other, Positions.offset1x1())
                .toTileGraphics()

        assertThat(IMAGE_3X3.getTileAt(FILLED_POS).get()).isEqualTo(FILLER_TILE_A)
    }

    @Test
    fun shouldProperlyGetEmptyTile() {
        assertThat(IMAGE_3X3.getTileAt(EMPTY_POS).get()).isEqualTo(Tile.empty())
    }

    @Test
    fun shouldProperlyCreateCopyWithTileAt() {
        val result = IMAGE_3X3.withTileAt(Positions.offset1x1(), NEW_TILE_B)

        assertThat(result.getTileAt(Positions.offset1x1()).get()).isEqualTo(NEW_TILE_B)
    }

    @Test
    fun shouldProperlyCreateSubImage() {
        val result = IMAGE_3X3.toSubImage(Position.offset1x1(), Size.create(2, 1))

        assertThat(result.tiles.toMap()).isEqualTo(mapOf(
                Positions.create(0, 0) to FILLER_TILE_A,
                Positions.create(1, 0) to Tile.empty()))
    }

    @Test
    fun shouldProperlyResizeToSmaller() {
        val result = IMAGE_3X3.withNewSize(Sizes.create(1, 1))

        assertThat(result.tiles.toMap()).isEqualTo(
                mapOf(Positions.create(0, 0) to FILLER_TILE_A))
    }

    @Test
    fun shouldProperlyResizeToLarger() {
        val result = IMAGE_3X3.withNewSize(Sizes.create(4, 1))

        assertThat(result.tiles.toMap()).isEqualTo(mapOf(
                Positions.create(0, 0) to FILLER_TILE_A,
                Positions.create(1, 0) to FILLER_TILE_A,
                Positions.create(2, 0) to FILLER_TILE_A))
    }

    @Test
    fun shouldProperlyResizeToLargerWithFiller() {
        val result = IMAGE_3X3.withNewSize(Sizes.create(4, 1), NEW_TILE_B)

        assertThat(result.tiles.toMap()).isEqualTo(mapOf(
                Positions.create(0, 0) to FILLER_TILE_A,
                Positions.create(1, 0) to FILLER_TILE_A,
                Positions.create(2, 0) to FILLER_TILE_A,
                Positions.create(3, 0) to NEW_TILE_B))
    }

    @Test
    fun shouldProperlyFill() {
        val result = IMAGE_3X3.withFiller(NEW_TILE_B)


        assertThat(result.fetchCharacters()).containsExactly(
                'a', 'a', 'a',
                'a', 'a', 'b',
                'a', 'a', 'a')
    }

    @Test
    fun shouldProperlyFetchCellsBy() {
        val result = IMAGE_3X3.toSubImage(Position.offset1x1(), Size.create(2, 1))

        assertThat(result.tiles.toMap()).isEqualTo(mapOf(
                Position.create(0, 0) to FILLER_TILE_A,
                Position.create(1, 0) to Tile.empty()))
    }

    @Test
    fun shouldProperlyCombineWith() {
        val result = IMAGE_3X3.combineWith(OTHER_IMAGE_2X2, Position.create(2, 2))

        assertThat(result.fetchCharacters()).containsExactly(
                'a', 'a', 'a', ' ',
                'a', 'a', ' ', ' ',
                'a', 'a', 'b', 'b',
                ' ', ' ', 'b', 'b')
    }

    @Test
    fun shouldProperlyTransform() {
        val result = IMAGE_3X3.transform {
            NEW_TILE_B
        }

        assertThat(result.tiles.values.toSet()).containsExactly(
                NEW_TILE_B)
    }

    @Test
    fun shouldProperlyWithStyle() {
        val style = StyleSet.create(YELLOW, GREEN)
        val result = IMAGE_3X3.withFiller(FILLER_TILE_A).withStyle(style)

        assertThat(result.tiles.values.toSet()).containsExactly(
                Tile.createCharacterTile('a', style))
    }

    @Test
    fun shouldProperlyWithTileset() {
        val tileset = CP437TilesetResources.rexPaint12x12()

        val result = IMAGE_3X3.withTileset(tileset)

        assertThat(result.tileset == tileset)
    }


    companion object {

        val FILLED_POS = Positions.create(1, 0)
        val EMPTY_POS = Positions.create(2, 1)
        val FILLER_TILE_A = Tile.defaultTile().withCharacter('a')
        val NEW_TILE_B = Tile.defaultTile().withCharacter('b')

        val TILESET_CHEEPICUS = CP437TilesetResources.cheepicus16x16()
        val SIZE_3X3 = Sizes.create(3, 3)
        val IMAGE_3X3 = DefaultTileImage(
                size = SIZE_3X3,
                tileset = TILESET_CHEEPICUS,
                initialTiles = mapOf(
                        Positions.create(0, 0) to FILLER_TILE_A,
                        Positions.create(1, 0) to FILLER_TILE_A,
                        Positions.create(2, 0) to FILLER_TILE_A,
                        Positions.create(0, 1) to FILLER_TILE_A,
                        Positions.create(1, 1) to FILLER_TILE_A,
                        // 2, 1 empty
                        Positions.create(0, 2) to FILLER_TILE_A,
                        Positions.create(1, 2) to FILLER_TILE_A,
                        Positions.create(2, 2) to FILLER_TILE_A))

        val OTHER_IMAGE_2X2 = DefaultTileImage(
                size = Size.create(2, 2),
                tileset = CP437TilesetResources.acorn8X16(),
                initialTiles = mapOf(
                        Positions.create(0, 0) to NEW_TILE_B,
                        Positions.create(1, 0) to NEW_TILE_B,
                        Positions.create(0, 1) to NEW_TILE_B,
                        Positions.create(1, 1) to NEW_TILE_B))
    }
}
