@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.kotlin.onClosed
import org.hexworks.zircon.api.kotlin.onKeyStroke
import org.hexworks.zircon.api.kotlin.onMouseReleased
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

object DeletedComponentFocusTest {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val button = Components.button()
                .withText("Delete me")
                .build().apply {
                    onMouseReleased { screen.removeComponent(this) }
                }

        val other = Components.button()
                .withText("Other")
                .withPosition(0, 1)
                .build()

        screen.addComponent(button)
        screen.addComponent(other)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
