@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.cobalt.logging.api.LoggerFactory

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.GraphicalTilesetResources

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.screen.Screen

object GraphicTilesTest {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rexPaint16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        screen.addComponent(Components.icon()
                .withPosition(1, 1)
                .withIcon(Tile.newBuilder()
                        .withName("Plate mail")
                        .withTileset(GraphicalTilesetResources.nethack16x16())
                        .buildGraphicalTile())
                .build())
        screen.addComponent(Components.label()
                .withText("Label with icon")
                .withPosition(2, 1))

        screen.display()

        screen.theme = theme
    }

}
