package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.fetchCharacters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Suppress("TestFunctionName")
abstract class TileGraphicsTest {

    abstract var target: InternalTileGraphics

    abstract fun setUp()

    @Test
    fun When_getting_size_Then_it_should_return_the_proper_size() {
        assertEquals(
                expected = SIZE_OF_3X3,
                actual = target.size
        )
    }

    @Test
    fun When_filling_then_clearing_Then_it_should_be_empty() {
        target.apply {
            fill(FILLER)
        }

        target.clear()

        assertEquals(
                expected = mapOf(),
                actual = target.tiles.toMap()
        )
    }

    @Test
    fun When_darwing_a_tile_at_a_position_Then_getting_it_should_return_the_tile() {
        target.draw(FILLER, FILLED_POS)

        assertEquals(
                expected = FILLER,
                actual = target.getTileAt(FILLED_POS).get()
        )
    }

    @Test
    fun When_it_is_empty_Then_getting_a_tile_should_return_the_empty_tile() {
        assertEquals(
                expected = Tile.empty(),
                actual = target.getTileAt(FILLED_POS).get()
        )
    }

    @Test
    fun When_fetching_out_of_bounds_positions_Then_they_should_throw_an_exception_when_tyring_to_get() {
        fetchOutOfBoundsPositions().forEach {
            var ex: Exception? = null
            try {
                target.getTileAt(it).get()
            } catch (e: Exception) {
                ex = e
            }

            assertTrue(ex !== null)
        }
    }

    @Test
    fun When_trying_to_set_out_of_bounds_tile_Then_it_should_not_set() {
        fetchOutOfBoundsPositions().forEach {
            target.draw(FILLER, it)

            assertTrue(fetchTargetChars().none { char -> char == FILLER })
        }
    }

    @Test
    fun When_setting_a_tile_within_bounds_Then_it_should_be_set() {
        target.draw(FILLER, Position.offset1x1())

        assertEquals(
                expected = FILLER,
                actual = target.getTileAt(Position.offset1x1()).get()
        )
    }

    @Test
    fun When_creating_a_snapshot_Then_it_should_return_a_proper_snapshot() {
        target.draw(FILLER, FILLED_POS)

        assertEquals(
                expected = mapOf(FILLED_POS to FILLER),
                actual = target.state.tiles.toMap()
        )

    }

    @Test
    fun When_drawing_other_tile_graphics_Then_it_should_be_properly_drawn() {
        val other = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .buildPersistent()
                .apply {
                    fill(FILLER)
                }
        target.draw(other, pos(1, 1))

        assertEquals(
                actual = target.tiles.toMap(),
                expected = mapOf(
                        pos(1, 1) to FILLER, pos(2, 1) to FILLER,
                        pos(1, 2) to FILLER, pos(2, 2) to FILLER)
        )

    }

    @Test
    fun When_drawing_onto_other_tile_graphics_Then_it_should_properly_be_drawn() {
        val other = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()
                .apply {
                    fill(FILLER)
                }
        target.draw(other, pos(2, 2))

        assertEquals(
                expected = mapOf(pos(2, 2) to FILLER),
                actual = target.tiles.toMap()
        )
    }

    @Test
    fun When_drawing_overflowing_tile_graphics_Then_it_should_be_properly_drawn() {
        val other = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .buildPersistent()
                .apply {
                    fill(FILLER)
                }
        target.draw(other, pos(2, 2))

        assertEquals(
                expected = mapOf(pos(2, 2) to FILLER),
                actual = target.tiles.toMap()
        )
    }

    @Test
    fun When_picking_a_different_tileset_Then_it_should_be_properly_set() {
        val tileset = CP437TilesetResources.rexPaint12x12()

        target.tileset = tileset

        assertEquals(
                expected = tileset,
                actual = target.tileset
        )
    }


    @Test
    fun When_resizing_with_filler_Then_it_should_be_properly_filled() {
        val defaultTile = FILLER.withCharacter('x')
        target.fill(defaultTile)
        val result = target.toResized(Size.create(4, 4), FILLER)

        assertEquals(
                expected = listOf('x', 'x', 'x', 'a',
                        'x', 'x', 'x', 'a',
                        'x', 'x', 'x', 'a',
                        'a', 'a', 'a', 'a'),
                actual = result.fetchCharacters()
        )
    }

    @Test
    fun When_resizing_without_filler_Then_it_should_be_properly_resized() {
        val defaultTile = FILLER.withCharacter('x')
        target.fill(defaultTile)
        val result = target.toResized(Size.create(4, 4))

        assertEquals(
                expected = listOf('x', 'x', 'x', ' ',
                        'x', 'x', 'x', ' ',
                        'x', 'x', 'x', ' ',
                        ' ', ' ', ' ', ' '),
                actual = result.fetchCharacters()
        )
    }

    @Test
    fun When_shrinking_Then_it_should_be_properly_shrunk() {
        val tile = FILLER.withCharacter('x')
        target.fill(tile)
        val result = target.toResized(Size.create(2, 2))

        assertEquals(
                expected = listOf(tile, tile, tile, tile),
                actual = result.tiles.values
        )
    }


    @Test
    fun When_applying_style_Then_it_should_be_properly_applied() {
        target.fill(FILLER)
        val old = FILLER.styleSet
        val new = StyleSet.defaultStyle()
                .withForegroundColor(ANSITileColor.GREEN)
                .withBackgroundColor(ANSITileColor.YELLOW)

        target.toSubTileGraphics(Rect.create(Position.offset1x1(), Size.one()))
                .applyStyle(new)

        assertEquals(
                expected = listOf(
                        old, old, old,
                        old, new, old,
                        old, old, old
                ),
                actual = target.size.fetchPositions().map {
                    target.getTileAt(it).get().styleSet
                }
        )
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
