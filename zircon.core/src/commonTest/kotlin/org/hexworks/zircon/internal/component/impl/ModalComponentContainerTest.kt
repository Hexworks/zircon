package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.modal
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_PRESSED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_RELEASED
import org.hexworks.zircon.internal.component.modal.DefaultModal
import org.hexworks.zircon.internal.component.modal.EmptyModalResult
import org.hexworks.zircon.internal.screen.TileGridScreen
import kotlin.test.AfterTest
import kotlin.test.Test

class ModalComponentContainerTest {

    private val grid = tileGrid {
        size = Size(10, 5)
        tileset = CP437TilesetResources.rogueYun16x16()
    }
    private val screen = (grid.toScreen() as TileGridScreen).apply { display() }

    @AfterTest
    fun tearDown() {
        grid.close()
    }

    @Test
    fun clicking_the_close_button_of_the_top_modal_closes_only_that_modal() {
        val first = screen.openModalWithCloseButtonAt(BUTTON_POSITION)
        val second = screen.openModalWithCloseButtonAt(BUTTON_POSITION)

        screen.dispatch(MouseEvent(MOUSE_PRESSED, 1, BUTTON_POSITION))
        screen.dispatch(MouseEvent(MOUSE_RELEASED, 1, BUTTON_POSITION))

        second.closed shouldBe true
        first.closed shouldBe false
    }

    @Test
    fun a_click_that_closes_a_modal_does_not_reach_the_modal_below_even_when_it_was_opened_at_the_same_spot() {
        val first = screen.openModalWithCloseButtonAt(BUTTON_POSITION)
        val second = screen.openModalWithCloseButtonAt(BUTTON_POSITION)
        val third = screen.openModalWithCloseButtonAt(BUTTON_POSITION)

        screen.dispatch(MouseEvent(MOUSE_PRESSED, 1, BUTTON_POSITION))
        screen.dispatch(MouseEvent(MOUSE_RELEASED, 1, BUTTON_POSITION))
        screen.dispatch(MouseEvent(MOUSE_PRESSED, 1, BUTTON_POSITION))
        screen.dispatch(MouseEvent(MOUSE_RELEASED, 1, BUTTON_POSITION))

        third.closed shouldBe true
        second.closed shouldBe true
        first.closed shouldBe false
    }

    /** A screen sized modal whose only content is a one-character close button at [position]. */
    private fun TileGridScreen.openModalWithCloseButtonAt(position: Position): DefaultModal<EmptyModalResult> {
        val content = panel {
            preferredSize = Size(3, 1)
            this.position = position
        }
        val modal = modal {
            contentComponent = content
            preferredSize = this@openModalWithCloseButtonAt.size
            colorTheme = theme
            tileset = this@openModalWithCloseButtonAt.tileset
        }
        content.addComponent(button {
            +"x"
            this.position = Position.ZERO
        }.apply { onActivated { modal.close(EmptyModalResult) } })
        openModal(modal)
        return modal as DefaultModal<EmptyModalResult>
    }

    companion object {
        val BUTTON_POSITION = Position(3, 2)
    }
}
