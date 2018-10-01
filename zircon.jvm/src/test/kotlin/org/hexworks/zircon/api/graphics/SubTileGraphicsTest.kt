package org.hexworks.zircon.api.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.*
import org.junit.Before
import org.junit.Test

class SubTileGraphicsTest {

    private val backend = TileGraphicsBuilder.newBuilder()
            .size(BACKEND_SIZE)
            .build()
    lateinit var target: TileGraphics

    @Before
    fun setUp() {
        target = backend.toSubTileGraphics(SUB_GRAPHICS_BOUNDS)
    }

    @Test
    fun shouldProperlyReportFilledPositions() {

        val positionsToFill = listOf(Position.create(1, 1), Position.create(2, 1))
        val wrongPosition = Position.create(6, 4)

        positionsToFill.plus(wrongPosition).forEach {
            target.setTileAt(it, Tile.defaultTile().withCharacter('y'))
        }

        assertThat(target.fetchFilledPositions()).containsExactlyElementsOf(positionsToFill)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun shouldRestrictResize() {
        target.resize(Size.defaultTerminalSize())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun shouldRestrictResizeWithFiller() {
        target.resize(Size.defaultTerminalSize(), Tile.empty())
    }

    @Test
    fun shouldProperlyFillBackendWhenFillIsCalled() {
        val subFiller = Tiles.defaultTile().withCharacter('x')

        target.fill(subFiller)

        val chars = backend.fetchCells().map { it.tile.asCharacterTile().get().character }

        assertThat(chars).containsExactly(
                ' ',' ',' ',' ',' ',
                ' ','x','x','x',' ',
                ' ','x','x','x',' ',
                ' ','x','x','x',' ',
                ' ',' ',' ',' ',' ')
    }

    @Test
    fun shouldProperlyFetchCells() {
        assertThat(target.fetchCells().map { it.position })
                .containsExactlyElementsOf(SUB_GRAPHICS_BOUNDS.size().fetchPositions().toList())
    }

    @Test
    fun shouldProperlyFetchCellsBy() {
        assertThat(target.fetchCellsBy(Position.offset1x1(), Size.create(2, 1)))
                .containsExactly(
                        Cell.create(Position.create(1, 1), Tile.empty()),
                        Cell.create(Position.create(2, 1), Tile.empty()))
    }

    @Test
    fun shouldProperlyPutText() {
        target.putText("foo")

        val chars = backend.fetchCells().map { it.tile.asCharacterTile().get().character }

        assertThat(chars).containsExactly(
                ' ',' ',' ',' ',' ',
                ' ','f','o','o',' ',
                ' ',' ',' ',' ',' ',
                ' ',' ',' ',' ',' ',
                ' ',' ',' ',' ',' ')
    }

    @Test
    fun shouldProperlyPutOverlappingText() {
        target.putText("foo", Position.offset1x1())

        val chars = backend.fetchCells().map { it.tile.asCharacterTile().get().character }

        assertThat(chars).containsExactly(
                ' ',' ',' ',' ',' ',
                ' ',' ',' ',' ',' ',
                ' ',' ','f','o',' ',
                ' ',' ',' ',' ',' ',
                ' ',' ',' ',' ',' ')
    }

    @Test
    fun shouldProperlyApplyStyle() {
        backend.applyStyle(BACKEND_STYLE)

        target.applyStyle(TARGET_STYLE)

        assertThat(backend.fetchCells().map { it.tile.styleSet() }).containsExactly(
                BACKEND_STYLE, BACKEND_STYLE, BACKEND_STYLE, BACKEND_STYLE, BACKEND_STYLE,
                BACKEND_STYLE, TARGET_STYLE, TARGET_STYLE, TARGET_STYLE, BACKEND_STYLE,
                BACKEND_STYLE, TARGET_STYLE, TARGET_STYLE, TARGET_STYLE, BACKEND_STYLE,
                BACKEND_STYLE, TARGET_STYLE, TARGET_STYLE, TARGET_STYLE, BACKEND_STYLE,
                BACKEND_STYLE, BACKEND_STYLE, BACKEND_STYLE, BACKEND_STYLE, BACKEND_STYLE)
    }

    @Test
    fun shouldProperlyCreateTileImage() {
        target.fill(FILLER).applyStyle(TARGET_STYLE)

        val result = target.toTileImage()

        target.fill(Tiles.empty()).applyStyle(BACKEND_STYLE)

        val expectedTile = FILLER.withStyle(TARGET_STYLE)

        assertThat(result.toTileMap().map { it.value }).containsExactly(
                expectedTile,expectedTile,expectedTile,
                expectedTile,expectedTile,expectedTile,
                expectedTile,expectedTile,expectedTile)
    }

    @Test
    fun shouldProperlyCreateSubSubTileGraphics() {
        val subFiller = FILLER.withCharacter('x')

        val result = target.toSubTileGraphics(Bounds.create(Position.offset1x1(), Size.create(2, 1)))
        result.fill(subFiller)

        val chars = backend.fetchCells().map { it.tile.asCharacterTile().get().character }

        assertThat(chars).containsExactly(
                ' ',' ',' ',' ',' ',
                ' ',' ',' ',' ',' ',
                ' ',' ','x','x',' ',
                ' ',' ',' ',' ',' ',
                ' ',' ',' ',' ',' ')

    }

    @Test
    fun shouldBeAbleToUseDifferentTileset() {
        val backendTileset = CP437TilesetResources.rexPaint16x16()
        val subTileset = CP437TilesetResources.wanderlust16x16()

        backend.useTileset(backendTileset)
        target.useTileset(subTileset)

        assertThat(backend.currentTileset()).isEqualTo(backendTileset)
        assertThat(target.currentTileset()).isEqualTo(subTileset)
    }

    @Test
    fun shouldBeAbleToUseDifferentStyles() {
        backend.setStyleFrom(BACKEND_STYLE)
        target.setStyleFrom(TARGET_STYLE)

        assertThat(backend.toStyleSet()).isEqualTo(BACKEND_STYLE)
        assertThat(target.toStyleSet()).isEqualTo(TARGET_STYLE)
    }

    companion object {

        private val BACKEND_STYLE = StyleSet.create(ANSITileColor.BLUE, ANSITileColor.RED)
        private val TARGET_STYLE = StyleSet.create(ANSITileColor.YELLOW, ANSITileColor.GREEN)
        private val BACKEND_SIZE = Size.create(5, 5)
        private val FILLER = Tiles.defaultTile().withCharacter('_')

        val SUB_GRAPHICS_BOUNDS = Bounds.create(Position.offset1x1(), Size.create(3, 3))
    }

}
