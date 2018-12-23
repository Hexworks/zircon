@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.resource.BuiltInGraphicTilesetResource

object GraphicTilesTest {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rexPaint16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(Components.icon()
                .withPosition(1, 1)
                .withIcon(Tiles.newBuilder()
                        .withName("Plate mail")
                        .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                        .buildGraphicTile())
                .build())
        screen.addComponent(Components.label()
                .withText("Label with icon")
                .withPosition(2, 1))

        screen.display()

        screen.applyColorTheme(theme)
    }

}
