package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileImage
import org.hexworks.zircon.api.builder.graphics.withSize
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.YELLOW
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile.Companion.defaultTile
import org.hexworks.zircon.fetchCharacters
import org.junit.Test

class DefaultTileImageTest {


    @Test
    fun shouldProperlyGetExistingTile() {

        val other = tileImage {
            withSize {
                width = 3
                height = 3
            }
            filler = defaultTile()
        }

        // TODO: WTF is happening here?
        tileImage {
            filler = defaultTile()
            withSize {
                width = 5
                height = 5
            }
            tileset = CP437TilesetResources.aduDhabi16x16()
        }
            .withTileAt(Position.create(1, 1), defaultTile().withCharacter('x'))
            .combineWith(other, Position.offset1x1())
            .toTileGraphics()

        assertThat(IMAGE_3X3.getTileAtOrNull(FILLED_POS)).isEqualTo(FILLER_TILE_A)
    }

    @Test
    fun shouldProperlyGetEmptyTile() {
        assertThat(IMAGE_3X3.getTileAtOrNull(EMPTY_POS)).isNull()
    }

    @Test
    fun shouldProperlyCreateCopyWithTileAt() {
        val result = IMAGE_3X3.withTileAt(Position.offset1x1(), NEW_TILE_B)

        assertThat(result.getTileAtOrNull(Position.offset1x1())).isEqualTo(NEW_TILE_B)
    }

    @Test
    fun shouldProperlyCreateSubImage() {
        val result = IMAGE_3X3.toSubImage(Position.offset1x1(), Size.create(2, 1))

        assertThat(result.tiles.toMap()).isEqualTo(
            mapOf(
                Position.create(0, 0) to FILLER_TILE_A
            )
        )
    }

    @Test
    fun shouldProperlyResizeToSmaller() {
        val result = IMAGE_3X3.withNewSize(Size.create(1, 1))

        assertThat(result.tiles.toMap()).isEqualTo(
            mapOf(Position.create(0, 0) to FILLER_TILE_A)
        )
    }

    @Test
    fun shouldProperlyResizeToLarger() {
        val result = IMAGE_3X3.withNewSize(Size.create(4, 1))

        assertThat(result.tiles.toMap()).isEqualTo(
            mapOf(
                Position.create(0, 0) to FILLER_TILE_A,
                Position.create(1, 0) to FILLER_TILE_A,
                Position.create(2, 0) to FILLER_TILE_A
            )
        )
    }

    @Test
    fun shouldProperlyResizeToLargerWithFiller() {
        val result = IMAGE_3X3.withNewSize(Size.create(4, 1), NEW_TILE_B)

        assertThat(result.tiles.toMap()).isEqualTo(
            mapOf(
                Position.create(0, 0) to FILLER_TILE_A,
                Position.create(1, 0) to FILLER_TILE_A,
                Position.create(2, 0) to FILLER_TILE_A,
                Position.create(3, 0) to NEW_TILE_B
            )
        )
    }

    @Test
    fun shouldProperlyFill() {
        val result = IMAGE_3X3.withFiller(NEW_TILE_B)


        assertThat(result.fetchCharacters()).containsExactly(
            'a', 'a', 'a',
            'a', 'a', 'b',
            'a', 'a', 'a'
        )
    }

    @Test
    fun shouldProperlyFetchCellsBy() {
        val result = IMAGE_3X3.toSubImage(Position.offset1x1(), Size.create(2, 1))

        assertThat(result.tiles.toMap()).isEqualTo(
            mapOf(
                Position.create(0, 0) to FILLER_TILE_A
            )
        )
    }

    @Test
    fun shouldProperlyCombineWith() {
        val result = IMAGE_3X3.combineWith(OTHER_IMAGE_2X2, Position.create(2, 2))

        assertThat(result.fetchCharacters()).containsExactly(
            'a', 'a', 'a', ' ',
            'a', 'a', ' ', ' ',
            'a', 'a', 'b', 'b',
            ' ', ' ', 'b', 'b'
        )
    }

    @Test
    fun shouldProperlyTransform() {
        val result = IMAGE_3X3.transform {
            NEW_TILE_B
        }

        assertThat(result.tiles.values.toSet()).containsExactly(
            NEW_TILE_B
        )
    }

    @Test
    fun shouldProperlyWithStyle() {
        val style = styleSet {
            foregroundColor = YELLOW
            backgroundColor = GREEN
        }
        val result = IMAGE_3X3.withFiller(FILLER_TILE_A).withStyle(style)

        assertThat(result.tiles.values.toSet()).containsExactly(
            characterTile {
                +'a'
                styleSet = style
            }
        )
    }

    @Test
    fun shouldProperlyWithTileset() {
        val tileset = CP437TilesetResources.rexPaint12x12()

        val result = IMAGE_3X3.withTileset(tileset)

        assertThat(result.tileset == tileset)
    }


    companion object {

        val FILLED_POS = Position.create(1, 0)
        val EMPTY_POS = Position.create(2, 1)
        val FILLER_TILE_A = defaultTile().withCharacter('a')
        val NEW_TILE_B = defaultTile().withCharacter('b')

        val TILESET_CHEEPICUS = CP437TilesetResources.cheepicus16x16()
        val SIZE_3X3 = Size.create(3, 3)
        val IMAGE_3X3 = DefaultTileImage(
            size = SIZE_3X3,
            tileset = TILESET_CHEEPICUS,
            initialTiles = mapOf(
                Position.create(0, 0) to FILLER_TILE_A,
                Position.create(1, 0) to FILLER_TILE_A,
                Position.create(2, 0) to FILLER_TILE_A,

                Position.create(0, 1) to FILLER_TILE_A,
                Position.create(1, 1) to FILLER_TILE_A,
                // 2, 1 empty

                Position.create(0, 2) to FILLER_TILE_A,
                Position.create(1, 2) to FILLER_TILE_A,
                Position.create(2, 2) to FILLER_TILE_A
            )
        )

        val OTHER_IMAGE_2X2 = DefaultTileImage(
            size = Size.create(2, 2),
            tileset = CP437TilesetResources.acorn8X16(),
            initialTiles = mapOf(
                Position.create(0, 0) to NEW_TILE_B,
                Position.create(1, 0) to NEW_TILE_B,
                Position.create(0, 1) to NEW_TILE_B,
                Position.create(1, 1) to NEW_TILE_B
            )
        )
    }
}
