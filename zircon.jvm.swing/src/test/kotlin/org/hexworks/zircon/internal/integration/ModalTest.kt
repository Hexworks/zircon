@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.extensions.onKeyboardEvent
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

object ModalTest {

    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val modalPanel = Components.panel()
                .withSize(Sizes.create(20, 20))
                .build()

        val modal = ModalBuilder.newBuilder<EmptyModalResult>()
                .withComponent(modalPanel)
                .withParentSize(tileGrid.size)
                .build()

        val button = Components.button()
                .withText("Close")
                .build().apply {
                    onKeyboardEvent(KeyboardEventType.KEY_PRESSED) { event, _ ->
                        if (event.code == KeyCode.KEY_C) {
                            modal.close(EmptyModalResult)
                            Processed
                        } else Pass
                    }
                }

        modalPanel.addComponent(button)

        screen.display()
        screen.applyColorTheme(theme)
        modal.applyColorTheme(theme)

        screen.openModal(modal)
    }

}
