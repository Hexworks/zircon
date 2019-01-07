package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.kotlin.onSelectionChanged

object SelectionHandlingExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

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
        screen.applyColorTheme(theme)

        cb0.onSelectionChanged {
            println("Selection changed")
        }

        cb0.isSelected = true

        cb0.selectedProperty.bind(cb1.selectedProperty)

    }

}
