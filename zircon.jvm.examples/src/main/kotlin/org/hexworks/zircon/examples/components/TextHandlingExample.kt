package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.extensions.onTextChanged

object TextHandlingExample {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val label = Components.label()
                .withText("Foobar")
                .wrapWithShadow(true)
                .build()

        val otherLabel = Components.label()
                .withText("Barbaz")
                .wrapWithShadow(true)
                .withPosition(0, 3)
                .build()

        screen.addComponent(label)
        screen.addComponent(otherLabel)

        screen.display()
        screen.applyColorTheme(theme)

        label.onTextChanged {
            println("Text changed")
        }

        label.text = "baz"

        label.textProperty.value = "xul"

        label.textProperty.bind(otherLabel.textProperty)

        // Text changed
        // Text changed
        // Text changed

    }

}
