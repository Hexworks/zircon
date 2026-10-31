package org.hexworks.zircon.internal.component.modal

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.modal
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.extensions.darkenByPercent
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.screen.Screen
import kotlin.test.AfterTest
import kotlin.test.Test

class ModalBackdropTest {

    private val grid = tileGrid {
        size = Size(4, 3)
        tileset = CP437TilesetResources.rogueYun16x16()
    }
    private val screen = grid.toScreen()

    @AfterTest
    fun tearDown() {
        grid.close()
    }

    @Test
    fun an_opened_modal_gets_what_the_screen_showed_darkened_by_its_percent_as_backdrop() {
        screen.draw(tile('a', 100, 200), Position(0, 0))
        screen.display()

        val modal = screen.openTestModal(darkenPercent = 0.5)

        val backdrop = (modal as DefaultModal<*>).backdrop.shouldNotBeNull()
        backdrop.size shouldBe screen.size
        backdrop.getTileAtOrNull(Position(0, 0)) shouldBe tile('a', 50, 100)
    }

    @Test
    fun a_percent_of_zero_shows_the_screen_as_it_was() {
        screen.draw(tile('a', 100, 200), Position(0, 0))

        val modal = screen.openTestModal(darkenPercent = 0.0)

        (modal as DefaultModal<*>).backdrop.shouldNotBeNull().getTileAtOrNull(Position(0, 0)) shouldBe tile('a', 100, 200)
    }

    @Test
    fun the_backdrop_is_cropped_to_the_modals_bounds() {
        screen.draw(tile('a', 100, 200), Position(1, 1))

        val modal = screen.openTestModal(darkenPercent = 0.5, size = Size(2, 2), position = Position(1, 1))

        val backdrop = (modal as DefaultModal<*>).backdrop.shouldNotBeNull()
        backdrop.size shouldBe Size(2, 2)
        backdrop.getTileAtOrNull(Position(0, 0)) shouldBe tile('a', 50, 100)
    }

    @Test
    fun the_screen_renders_the_backdrop_behind_the_modal() {
        screen.addComponent(label {
            +"a"
            position = Position(0, 0)
        })
        screen.display()
        val before = screen.shownTopTileAt(Position(0, 0))

        screen.openTestModal(darkenPercent = 0.5)

        val shown = screen.shownTopTileAt(Position(0, 0))
        shown.character shouldBe 'a'
        shown.foregroundColor shouldBe before.foregroundColor.darkenByPercent(0.5)
    }

    @Test
    fun a_modal_over_a_modal_darkens_further_and_closing_it_brings_the_previous_view_back() {
        screen.addComponent(label {
            +"a"
            position = Position(0, 0)
        })
        screen.display()
        val before = screen.shownTopTileAt(Position(0, 0)).foregroundColor
        screen.openTestModal(darkenPercent = 0.5)

        val second = screen.openTestModal(darkenPercent = 0.5)

        screen.shownTopTileAt(Position(0, 0)).foregroundColor shouldBe before.darkenByPercent(0.5).darkenByPercent(0.5)
        second.close(EmptyModalResult)
        screen.shownTopTileAt(Position(0, 0)).foregroundColor shouldBe before.darkenByPercent(0.5)
    }

    /** The topmost tile the screen shows at [position]: the label text sits on the opaque root. */
    private fun Screen.shownTopTileAt(position: Position): CharacterTile {
        val tile = captureFrame().getTileAtOrNull(position).shouldNotBeNull()
        return (if (tile is StackedTile) tile.top else tile).shouldBeInstanceOf<CharacterTile>()
    }

    private fun Screen.openTestModal(
        darkenPercent: Double,
        size: Size = this.size,
        position: Position = Position.ZERO,
    ): Modal<EmptyModalResult> {
        val modal = modal {
            // The content sits in the bottom right corner, so the top left cell shows the backdrop.
            contentComponent = panel {
                preferredSize = Size(1, 1)
                this.position = Position(size.width - 1, size.height - 1)
            }
            preferredSize = size
            this.position = position
            colorTheme = theme
            tileset = this@openTestModal.tileset
            this.darkenPercent = darkenPercent
        }
        openModal(modal)
        return modal
    }

    private fun tile(c: Char, fg: Int, bg: Int) = characterTile {
        character = c
        withStyleSet {
            foregroundColor = Color.create(fg, fg, fg)
            backgroundColor = Color.create(bg, bg, bg)
        }
    }
}
