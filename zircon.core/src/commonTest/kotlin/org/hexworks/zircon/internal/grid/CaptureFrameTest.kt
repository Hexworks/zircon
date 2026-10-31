package org.hexworks.zircon.internal.grid

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.extensions.toScreen
import kotlin.test.AfterTest
import kotlin.test.Test

class CaptureFrameTest {

    private val grid = tileGrid {
        size = Size(3, 2)
        tileset = CP437TilesetResources.rogueYun16x16()
    }

    @AfterTest
    fun tearDown() {
        grid.close()
    }

    @Test
    fun the_capture_holds_the_tiles_of_the_grid() {
        grid.draw(opaque('a'), Position(0, 0))

        val frame = grid.captureFrame()

        frame.size shouldBe grid.size
        frame.tileset shouldBe grid.tileset
        frame.getTileAtOrNull(Position(0, 0)) shouldBe opaque('a')
        frame.getTileAtOrNull(Position(1, 0)) shouldBe null
    }

    @Test
    fun an_opaque_tile_on_a_layer_hides_what_is_below_it() {
        grid.draw(opaque('a'), Position(0, 0))
        grid.addLayer(layer { size = grid.size }.apply { draw(opaque('x'), Position(0, 0)) })

        grid.captureFrame().getTileAtOrNull(Position(0, 0)) shouldBe opaque('x')
    }

    @Test
    fun a_see_through_tile_over_an_opaque_one_is_captured_as_a_stack_bottom_first() {
        grid.draw(opaque('a'), Position(0, 0))
        grid.addLayer(layer { size = grid.size }.apply { draw(seeThrough('x'), Position(0, 0)) })

        val tile = grid.captureFrame().getTileAtOrNull(Position(0, 0)).shouldBeInstanceOf<StackedTile>()

        tile.tiles shouldBe listOf(opaque('a'), seeThrough('x'))
    }

    @Test
    fun a_tile_from_a_layer_with_another_tileset_keeps_that_tileset() {
        val other = CP437TilesetResources.anikki16x16()
        grid.addLayer(layer {
            size = grid.size
            tileset = other
        }.apply { draw(opaque('x'), Position(0, 0)) })

        val tile = grid.captureFrame().getTileAtOrNull(Position(0, 0)).shouldBeInstanceOf<CharacterTile>()

        tile.tileset shouldBe other
        tile.withTileset(grid.tileset) shouldBe opaque('x').withTileset(grid.tileset)
    }

    @Test
    fun a_tile_from_a_layer_with_the_grids_tileset_carries_no_tileset() {
        grid.addLayer(layer {
            size = grid.size
            tileset = grid.tileset
        }.apply { draw(opaque('x'), Position(0, 0)) })

        grid.captureFrame().getTileAtOrNull(Position(0, 0)).shouldBeInstanceOf<CharacterTile>().tileset.isUnknown shouldBe true
    }

    @Test
    fun a_hidden_layer_is_not_captured() {
        grid.draw(opaque('a'), Position(0, 0))
        grid.addLayer(layer { size = grid.size }.apply {
            draw(opaque('x'), Position(0, 0))
            isHidden = true
        })

        grid.captureFrame().getTileAtOrNull(Position(0, 0)) shouldBe opaque('a')
    }

    @Test
    fun the_components_of_a_displayed_screen_are_captured() {
        val screen = grid.toScreen()
        screen.display()
        screen.addComponent(label {
            +"y"
            position = Position(1, 1)
        })

        // The label's see-through text sits on the opaque root container.
        val tile = grid.captureFrame().getTileAtOrNull(Position(1, 1)).shouldBeInstanceOf<StackedTile>()

        tile.top.shouldBeInstanceOf<CharacterTile>().character shouldBe 'y'
    }

    @Test
    fun the_capture_does_not_follow_later_changes_to_the_grid() {
        grid.draw(opaque('a'), Position(0, 0))
        val frame = grid.captureFrame()

        grid.draw(opaque('b'), Position(0, 0))

        frame.getTileAtOrNull(Position(0, 0)) shouldBe opaque('a')
    }

    private fun opaque(c: Char) = characterTile {
        character = c
        withStyleSet {
            foregroundColor = Color.create(10, 20, 30)
            backgroundColor = Color.create(40, 50, 60)
        }
    }

    private fun seeThrough(c: Char) = characterTile {
        character = c
        withStyleSet {
            foregroundColor = Color.create(10, 20, 30)
            backgroundColor = Color.TRANSPARENT
        }
    }
}
