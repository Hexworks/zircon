package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.screen.Screen

object SelectionHandlingExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build())

        val screen = Screen.create(tileGrid)

        val cb0 = Components.checkBox()
                .withText("Foo")
                .build()

        val cb1 = Components.checkBox()
                .withText("Bar")
                .withPosition(0, 2)
                .build()

        screen.addComponent(cb0)
        screen.addComponent(cb1)

        screen.display()
        screen.theme = theme

        cb0.onSelectionChanged {
            println("Selection changed")
        }

        cb0.isSelected = true

        cb0.selectedProperty.bind(cb1.selectedProperty)

    }

}
