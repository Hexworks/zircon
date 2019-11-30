package org.hexworks.zircon.api.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.Tile.Companion
import org.hexworks.zircon.fetchCharacters
import org.junit.Before
import org.junit.Test

class SubTileGraphicsTest {

    private val backend = TileGraphicsBuilder.newBuilder()
            .withSize(BACKEND_SIZE_5X5)
            .build()
    lateinit var target: TileGraphics

    @Before
    fun setUp() {
        target = backend.toSubTileGraphics(BOUNDS_1TO1_3X3)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun shouldRestrictResize() {
        target.toResized(Size.defaultGridSize())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun shouldRestrictResizeWithFiller() {
        target.toResized(Size.defaultGridSize(), Tile.empty())
    }

    @Test
    fun shouldProperlyFillBackendWhenFillIsCalled() {
        val subFiller = Tile.defaultTile().withCharacter('x')

        target.fill(subFiller)

        val chars = backend.fetchCharacters()

        assertThat(chars).containsExactly(
                ' ', ' ', ' ', ' ', ' ',
                ' ', 'x', 'x', 'x', ' ',
                ' ', 'x', 'x', 'x', ' ',
                ' ', 'x', 'x', 'x', ' ',
                ' ', ' ', ' ', ' ', ' ')
    }

    @Test
    fun shouldProperlyFetchCells() {
        assertThat(target.size.fetchPositions().toList())
                .containsExactlyElementsOf(BOUNDS_1TO1_3X3.size.fetchPositions().toList())
    }


    @Test
    fun shouldProperlyApplyStyle() {
        backend.fill(Tile.defaultTile())
        backend.applyStyle(BLUE_RED_STYLE)

        target.applyStyle(YELLOW_GREEN_STYLE)

        assertThat(backend.size.fetchPositions()
                .map { backend.getTileAt(it).get().styleSet }).containsExactly(
                BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE,
                BLUE_RED_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, BLUE_RED_STYLE,
                BLUE_RED_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, BLUE_RED_STYLE,
                BLUE_RED_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, BLUE_RED_STYLE,
                BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE)
    }

    @Test
    fun shouldProperlyCreateSubSubTileGraphics() {
        val subFiller = FILLER_UNDERSCORE.withCharacter('x')

        val result = target
                .toSubTileGraphics(Rect.create(Position.offset1x1(), Size.create(2, 1)))
        result.fill(subFiller)

        val chars = backend.size.fetchPositions().map {
            backend.getTileAt(it)
                    .map { tile -> tile.asCharacterTile().get().character }
                    .orElse(' ')
        }

        assertThat(chars).containsExactly(
                ' ', ' ', ' ', ' ', ' ',
                ' ', ' ', ' ', ' ', ' ',
                ' ', ' ', 'x', 'x', ' ',
                ' ', ' ', ' ', ' ', ' ',
                ' ', ' ', ' ', ' ', ' ')

    }

    companion object {

        private val BLUE_RED_STYLE = StyleSet.create(ANSITileColor.BLUE, ANSITileColor.RED)
        private val YELLOW_GREEN_STYLE = StyleSet.create(ANSITileColor.YELLOW, ANSITileColor.GREEN)
        private val BACKEND_SIZE_5X5 = Size.create(5, 5)
        private val FILLER_UNDERSCORE = Tile.defaultTile().withCharacter('_')

        val BOUNDS_1TO1_3X3 = Rect.create(Position.offset1x1(), Size.create(3, 3))
    }

}
