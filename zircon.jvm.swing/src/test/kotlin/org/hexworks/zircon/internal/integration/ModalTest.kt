@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.kotlin.onInput
import org.hexworks.zircon.api.kotlin.onKeyPressed
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
                    onKeyPressed('c') {
                        modal.close(EmptyModalResult)
                    }
                }

        modalPanel.addComponent(button)

        modal.onInput {
            println("Input is: $it")
        }

        screen.display()
        screen.applyColorTheme(theme)
        modal.applyColorTheme(theme)

        screen.openModal(modal)
    }

}
