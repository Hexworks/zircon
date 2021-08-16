@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.examples.dialogs

import org.hexworks.cobalt.logging.api.LoggerFactory

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_RIGHT
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_PRESSED
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

object ModalExample {

    private val logger = LoggerFactory.getLogger(this::class)
    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build()
        )

        val screen = Screen.create(tileGrid)

        screen.addComponent(createOpenAnotherButton(screen))

        screen.display()
        screen.theme = theme

        openModal(screen)
    }

    private fun openModal(screen: Screen, level: Int = 1) {

        val modalPanel = Components.panel()
            .withPreferredSize(Size.create(30, 20))
            .withDecorations(box(title = "Modal level: $level"), shadow())
            .build()

        val modal = ModalBuilder.newBuilder<EmptyModalResult>()
            .withComponent(modalPanel)
            .withPreferredSize(screen.size)
            .withColorTheme(screen.theme)
            .withTileset(screen.tileset)
            .withDarkenPercent(.1)
            .withCenteredDialog(true)
            .build().apply {
                handleKeyboardEvents(KEY_PRESSED) { event, _ ->
                    if (event.code == KeyCode.KEY_C) {
                        logger.info("Closed by pressing C")
                        close(EmptyModalResult)
                        Processed
                    } else Pass
                }
            }

        val closeButton = Components.button()
            .withText("Close")
            .withAlignment(alignmentWithin(modalPanel, BOTTOM_RIGHT))
            .build().apply {
                onActivated {
                    logger.info("Closed by activating the button")
                    modal.close(EmptyModalResult)
                }
            }

        modalPanel.addComponent(
            createOpenAnotherButton(
                screen = screen,
                position = closeButton.position.withX(0),
                level = level + 1
            )
        )
        modalPanel.addComponent(closeButton)
        modal.theme = theme
        screen.openModal(modal)
    }

    private fun createOpenAnotherButton(
        screen: Screen,
        position: Position = Position.zero(),
        level: Int = 1
    ): Button {
        return Components.button()
            .withText("Open another")
            .withPosition(position)
            .build().apply {
                handleComponentEvents(ACTIVATED) {
                    logger.info("Opening another modal")
                    openModal(screen, level)
                    Processed
                }
            }
    }

}
