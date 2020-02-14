@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.MouseEventType

object DeletedComponentFocusTest {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val button = Components.button()
                .withText("Delete me")
                .build()

        val other = Components.button()
                .withText("Other")
                .withPosition(0, 1)
                .build()

        screen.addComponent(button).apply {
            processMouseEvents(MouseEventType.MOUSE_RELEASED) { _, _ ->
                detach()
            }
        }
        screen.addComponent(other)

        screen.display()
        screen.theme = theme
    }

}
