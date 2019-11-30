package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.screen.Screen

object ComponentsDisableExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val checkBox = Components.checkBox()
                .withPosition(5, 5)
                .withText("Check me")
                .build()
        val disableButton = Components.toggleButton()
                .withSize(17, 1)
                .withAlignment(positionalAlignment(20, 5))
                .withText("Toggle")
                .build().apply {
                    checkBox.disabledProperty.updateFrom(selectedProperty)
                }

        screen.addComponent(checkBox)
        screen.addComponent(disableButton)

        screen.display()
        screen.theme = theme
    }

}
