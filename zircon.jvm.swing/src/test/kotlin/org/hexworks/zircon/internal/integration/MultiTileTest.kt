@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.*

object MultiTileTest {

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

        screen.addComponent(Components.label()
                .withText("Rexpaint 20x20")
                .withPosition(1, 1))

        screen.addComponent(Components.label()
                .withText("Yobbo 20x20")
                .withPosition(1, 3)
                .withTileset(CP437TilesetResources.yobbo20x20()))

        screen.addComponent(Components.panel()
                .withTitle("Anikki 20x20")
                .withPosition(1, 5)
                .withSize(17, 2)
                .wrapWithBox()
                .withTileset(CP437TilesetResources.anikki20x20()))

        val container = Components.panel()
                .withPosition(1, 8)
                .withSize(17, 3)
                .withTileset(CP437TilesetResources.bisasam20x20())
                .wrapWithBox()
                .withTitle("Bisasam 20x20")
                .build()
        container.addComponent(Components.label()
                .withText("Oreslam 20x20")
                .withTileset(CP437TilesetResources.oreslam20x20()))

        screen.addComponent(container)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
