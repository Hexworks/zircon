package org.hexworks.zircon.internal.renderer.impl

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.ErrorRecorder
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import kotlin.reflect.KClass
import kotlin.test.AfterTest
import kotlin.test.Test

class BaseRendererTest {

    private val errors = ErrorRecorder()
    private val grid = tileGrid {
        size = Size(3, 1)
        tileset = CP437TilesetResources.rogueYun16x16()
    }

    @AfterTest
    fun tearDown() {
        errors.uninstall()
        grid.close()
    }

    @Test
    fun a_tile_that_fails_to_draw_is_reported_and_the_rest_of_the_frame_is_drawn() {
        "abc".forEachIndexed { x, c -> grid.draw(characterTile { character = c }, Position(x, 0)) }
        val target = TextRenderer(grid, failOn = 'b')
        val out = StringBuilder()

        target.render(out)

        out.toString() shouldBe "ac"
        errors.errors shouldHaveSize 1
        errors.sources.single() shouldStartWith "Tile at "
    }

    @Test
    fun a_component_renderer_that_throws_is_reported_and_the_frame_still_renders() {
        val screen = grid.toScreen()
        screen.display()
        screen.addComponent(label {
            +"x"
            componentRenderer = ComponentRenderer<Label> { _, _ -> throw IllegalStateException("boom") }
        })
        val target = TextRenderer(grid, failOn = null)

        target.render(StringBuilder())

        errors.errors shouldHaveSize 1
        errors.sources.single() shouldStartWith "Renderer of "
    }

    @Test
    fun the_cursor_swaps_the_colours_of_the_tile_under_it() {
        grid.draw(characterTile {
            character = 'a'
            withStyleSet {
                foregroundColor = Color.create(1, 2, 3)
                backgroundColor = Color.create(4, 5, 6)
            }
        }, Position(0, 0))
        grid.cursorPosition = Position(0, 0)
        grid.isCursorVisible = true
        val target = TextRenderer(grid, failOn = null)

        target.render(StringBuilder())

        val drawn = target.drawn.single() as CharacterTile
        drawn.foregroundColor shouldBe Color.create(4, 5, 6)
        drawn.backgroundColor shouldBe Color.create(1, 2, 3)
    }

    @Test
    fun the_cursor_on_an_empty_cell_is_a_block_in_the_default_foreground_colour() {
        grid.cursorPosition = Position(2, 0)
        grid.isCursorVisible = true
        val target = TextRenderer(grid, failOn = null)

        target.render(StringBuilder())

        val drawn = target.drawn.single() as CharacterTile
        drawn.character shouldBe ' '
        drawn.backgroundColor shouldBe Color.DEFAULT_FOREGROUND_COLOR
    }

    @Test
    fun a_hidden_cursor_is_not_drawn() {
        grid.cursorPosition = Position(2, 0)
        grid.isCursorVisible = false
        val target = TextRenderer(grid, failOn = null)

        target.render(StringBuilder())

        target.drawn shouldBe emptyList()
    }

    /** Appends the character of every tile it draws to the context; throws for [failOn]. */
    private class TextRenderer(grid: TileGrid, failOn: Char?, private val loader: TextLoader = TextLoader(failOn)) :
        BaseRenderer<StringBuilder, Unit>(grid, loader) {
        /** Every tile drawn so far, in drawing order. */
        val drawn: List<Tile> get() = loader.tileset.drawn
        override fun create() {}
        override fun processInputEvents() {}
    }

    private class TextLoader(failOn: Char?) : TilesetLoader<StringBuilder> {
        val tileset = TextTileset(failOn)
        override fun loadTilesetFrom(resource: TilesetResource): Tileset<StringBuilder> = tileset
        override fun canLoadTileset(resource: TilesetResource): Boolean = true
    }

    private class TextTileset(private val failOn: Char?) : Tileset<StringBuilder> {
        override val id: UUID = UUID.randomUUID()
        override val targetType: KClass<StringBuilder> = StringBuilder::class
        override val width: Int = 1
        override val height: Int = 1
        override val size: Size = Size.ONE
        val drawn = mutableListOf<Tile>()

        override fun drawTile(tile: Tile, context: StringBuilder, position: Position) {
            val char = (tile as CharacterTile).character
            if (char == failOn) throw IllegalStateException("can't draw $char")
            drawn += tile
            context.append(char)
        }
    }
}
